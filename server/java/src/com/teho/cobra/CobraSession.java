package com.teho.cobra;

import org.webbitserver.WebSocketConnection;

import com.teho.cobra.codec.protocol.request.CobraCommand;
import com.teho.cobra.codec.protocol.request.CobraRequest;
import com.teho.cobra.codec.protocol.response.CobraEvent;
import com.teho.cobra.constant.COBRA_DATA_TYPE;
import com.teho.cobra.constant.COBRA_ERROR;
import com.teho.cobra.exceptions.CobraException;
import com.teho.cobra.interfaces.ICobraObjectBrokerEventListener;
import com.teho.cobra.interfaces.ICobraSessionListener;
import com.teho.cobra.interfaces.ICommandThreadListener;
import com.teho.cobra.interfaces.IEventThreadListener;
import com.teho.util.CobraLogger;

public class CobraSession implements ICommandThreadListener, IEventThreadListener, ICobraObjectBrokerEventListener {
	public CobraSession(ICobraSessionListener listener) {
		
		m_listener = listener;
		m_session_id = String.valueOf(hashCode());
		
		m_command_thread = new CommandThread(this);
		m_event_thread = new EventThread(this);
		
		m_object_broker = new CobraObjectBroker(m_session_id, this);
		
		CobraLogger.Info("Create CobraSession [session_id: " + m_session_id + "]");
	}

	/*
	 * member functions 
	 */
	public static CobraSession createSession(ICobraSessionListener listener) {
		return new CobraSession(listener);
	}
	
	public String getSessionId() {
		return this.m_session_id;
	}
	
	public WebSocketConnection getWebSocket() {
		return this.m_websocket;
	}
	
	public void setWebSocket(WebSocketConnection websocket) {
		this.m_websocket = websocket;
	}
	
	public void putCommand(CobraRequest request) throws CobraException {
		try {
			if ( true == request.isSyncCall() ) {  	/* Sync Operation */
				m_object_broker.doBroker((CobraCommand)request);
			}else{									/* Async Operation */
				m_command_thread.putCommand((CobraCommand)request);
				// Enable below line, if want to send return about async invoke
				// request.getCmdResult().getReturn().setValue(COBRA_DATA_TYPE.BOOLEAN, true);
			}
		} catch (InterruptedException e) {
			CobraException exception = new CobraException(COBRA_ERROR.ERROR_INTERNAL);
			throw exception;
		}
		/* write into cmd_result if success or not */
		
	}
	
	public void destroy() {	
		m_command_thread.stop();
		m_command_thread = null;
		
		m_event_thread.stop();
		m_event_thread = null;
		
		m_object_broker.destroy();
		m_object_broker = null;
		
		m_listener = null;
		m_websocket = null;
	}
	
	/**
	 * EventListener from ICobraObjectBrokerEventListener
	 * @throws CobraException 
	 */
	@Override
	public void onReceiveEventFromBroker(CobraEvent event) throws CobraException {
		try {
			if ( null!=m_event_thread ) m_event_thread.putEvent(event);
		} catch (InterruptedException e) {
			CobraException exception = new CobraException(COBRA_ERROR.ERROR_INTERNAL);
			throw exception;
		}		
	}	

	@Override
	public void onRecvCommandFromThread(CobraCommand cmd) throws CobraException {
		/* Go to ObjectBroker */	
		m_object_broker.doBroker(cmd);
	}	

	@Override
	public void onRecvEventFromThread(CobraEvent event) {
		/* Go to CobraServer */
		m_listener.onNotifyEvent(m_websocket, event);
	}
	
	/*
	 * member variables
	 */
	private CommandThread m_command_thread;
	private EventThread m_event_thread;
	
	private CobraObjectBroker m_object_broker;
	
	private ICobraSessionListener m_listener;
	
	private String m_session_id;
	
	private WebSocketConnection m_websocket;

}
