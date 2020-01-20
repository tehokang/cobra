package com.teho.cobra;

import java.util.HashMap;

import org.webbitserver.WebSocketConnection;

import com.teho.cobra.codec.CobraCodec;
import com.teho.cobra.codec.protocol.request.CobraCmdStart;
import com.teho.cobra.codec.protocol.request.CobraCmdStop;
import com.teho.cobra.codec.protocol.request.CobraRequest;
import com.teho.cobra.codec.protocol.response.CobraEvent;
import com.teho.cobra.constant.COBRA_DATA_TYPE;
import com.teho.cobra.exceptions.CobraException;
import com.teho.cobra.interfaces.ICobraSessionListener;
import com.teho.cobra.interfaces.IWebServerListener;
import com.teho.cobra.settings.CobraConfiguration;
import com.teho.util.CobraLogger;
import com.teho.util.WebServerWrapper;

public class CobraServer implements IWebServerListener, ICobraSessionListener {
	public CobraServer() {
		CobraLogger.Debug("Create CobraServer ");
		m_webserver = new WebServerWrapper();
		m_session_map = new HashMap<String, CobraSession>();
	}

	/*
	 * member functions
	 */
	public boolean init(int sync_port, String sync_path, int async_port, String async_path, String platform_pkg_path, boolean logOn) {
		CobraConfiguration.setConfiguration(sync_port, sync_path, async_port, async_path, platform_pkg_path);
		
		if ( true==logOn ) CobraLogger.setLogLevel(true, true, true, true);
		else CobraLogger.setLogLevel(false, false, false, true);
		
		if( false == m_webserver.init(sync_port, sync_path, async_port, async_path, this) ) {
			CobraLogger.Error("WebServer couldn't be initialized");
			return false;
		}
		return true;
	}
	
	public void setLogLevel(boolean onDebug, boolean onInfo, boolean onWarning, boolean onError) {
		CobraLogger.setLogLevel(onDebug, onInfo, onWarning, onError);
	}
	
	public boolean start() {
		if( false == m_webserver.start() ) {
			CobraLogger.Error("WebServer couldn't start. Please Check if exist already opened port.");
			return false;
		}
		return true;
	}
	
	public void stop() {
		m_webserver.stop();
	}
	
	public void deinit() {
		m_webserver.deinit();

		for (int i=0; i<m_session_map.size(); i++) {
			CobraSession _session = m_session_map.get(i);
			m_session_map.remove(_session.getSessionId());
			_session.destroy();
			_session = null;
		}
	}

	/**
	 * IWebServerListener from WebServerWrapper
	 * Command Notification from WebServer
	 */

	@Override
	public void onConnectedEventSocket(WebSocketConnection websocket) {
		CobraLogger.Debug("Client Connected for Event WebSocket");
	}

	@Override
	public void onDisconnectedEventSocket(WebSocketConnection websocket) {
		CobraLogger.Debug("Client Event Websocket Disconnected");	
		
		/**
		 * TODO:: Will be recycle below routine for scheduling garbage collection
		 */
		/*
		Set<Entry<String,CobraSession>> entrySet = m_session_map.entrySet();
		Iterator<Entry<String, CobraSession>> iterator = entrySet.iterator();
		
		int session_count = 0;
		
	    while (iterator.hasNext()) {
	        Entry<String, CobraSession> entry = iterator.next();
	        
	        CobraLogger.Debug("[session "+(++session_count)+"]");
	        CobraSession _session = (CobraSession)entry.getValue();
	        WebSocketConnection _websocket = _session.getWebSocket();
	        
	        CobraLogger.Debug("websocket : " + websocket);
	        CobraLogger.Debug("_websocket : " + _websocket);
	        
	        if ( (null==_websocket && true==_session.isStarted()) || 
	        	 (null!=_websocket && _websocket.hashCode()==websocket.hashCode()) ) {
	        	CobraLogger.Debug("Found Session and Remove it !! ");
	        	_session.destroy();
	        	_session = null;
	        	iterator.remove();  //same as "m_session_map.remove(_session_id);"
	        }
	    }
	    */
	}

	/**
	 * Received Callback from WebSocket or XmlHttpRequest
	 */
	@Override
	public CobraServerReturn onMessage(String json, WebSocketConnection websocket, boolean sync) {
		CobraRequest request = null;
		try{
			request = CobraCodec.decodeCommand(json);
			
			doOperate(request, websocket, sync);
			
		}catch(CobraException e) {
			/* Here has process-routine for All of Error */
			CobraLogger.Warn("<Error Return to Client>");
			CobraLogger.Warn("Error Code : " + e.getError().getErrorCode());
			CobraLogger.Warn("Error Description : " + e.getError().getErrorMessage());
			request.getCmdResult().setError(e.getError());
			e.printStackTrace();
		}finally {
			
		}
		if ( true==sync )
			return new CobraServerReturn(CobraCodec.encodeCommandResult(request.getCmdResult()), request);
		
		return null;
	}

	/**
	 * Event Notification from CobraSessions 
	 */
	@Override
	public void onNotifyEvent(WebSocketConnection websocket, CobraEvent event) {
		String json_event = CobraCodec.encodeEventResult(event);
		m_webserver.sendEvent(websocket, json_event);
	}

	/**
	 * 
	 * @param request
	 * @return
	 * @throws CobraException About +Invalid Request | Method not found | Invalid Parameters+
	 */
	private void doOperate(CobraRequest request, WebSocketConnection websocket, boolean sync) throws CobraException {

		request.setSyncCall(sync);
		
		switch ( request.getActionType() ){
			case MSG_START:
				{
					CobraSession _session = null;
					CobraCmdStart cmd_start = (CobraCmdStart)request;
					
					cmd_start.setWebSocket(websocket);
					
					if ( null!=cmd_start.getSessionId() && 0!=cmd_start.getSessionId().compareToIgnoreCase("null") ) {
						_session = m_session_map.get(request.getSessionId());
						_session.setWebSocket(cmd_start.getWebSocket());
					}else{
						_session = CobraSession.createSession(this);
						m_session_map.put(_session.getSessionId(), _session);
						cmd_start.getCmdResult().setSessionId(_session.getSessionId());
						cmd_start.getCmdResult().getReturn().setValue(COBRA_DATA_TYPE.STRING, _session.getSessionId());
					}
				}
				break;
			case MSG_STOP:
				{
					CobraCmdStop cmd_stop = (CobraCmdStop)request;
					CobraSession session = m_session_map.get(cmd_stop.getSessionId());
					m_session_map.remove(cmd_stop.getSessionId());
					if ( null!=session) {
						session.destroy();
						session = null;
					}
					cmd_stop.getCmdResult().getReturn().setValue(COBRA_DATA_TYPE.BOOLEAN, true);
				}
				break;
			default:
				{
					CobraSession session = m_session_map.get(request.getSessionId());
                    if( null!=session)
					    session.putCommand(request);
				}
				break;
		}		
	}
	
	/*
	 * member variables
	 */
	private WebServerWrapper m_webserver;
	private HashMap<String, CobraSession> m_session_map; /* key : session_id, value : CobraSession */
	
}
