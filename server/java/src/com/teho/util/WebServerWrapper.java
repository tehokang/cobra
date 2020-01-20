package com.teho.util;

import java.util.concurrent.ExecutionException;

import org.webbitserver.HttpControl;
import org.webbitserver.HttpHandler;
import org.webbitserver.HttpRequest;
import org.webbitserver.HttpResponse;
import org.webbitserver.WebServer;
import org.webbitserver.WebServers;
import org.webbitserver.WebSocketConnection;
import org.webbitserver.WebSocketHandler;
import org.webbitserver.wrapper.HttpRequestWrapper;
import org.webbitserver.wrapper.HttpResponseWrapper;

import com.teho.cobra.CobraServerReturn;
import com.teho.cobra.interfaces.IWebServerListener;

public class WebServerWrapper implements HttpHandler, WebSocketHandler {	
	public WebServerWrapper() {
		CobraLogger.Debug("Create WebServerWrapper");
	}
	
	/*
	 * member functions
	 */
	public boolean init(int sync_port, String sync_path, int async_port, String async_path, IWebServerListener listener) {
		m_xhr_server = WebServers.createWebServer(sync_port);
		m_ws_server = WebServers.createWebServer(async_port);

		if ( null == m_xhr_server || null == m_ws_server ) return false;
		
		m_listener = listener;
		m_xhr_server.add("/"+ sync_path, (HttpHandler)this);
	
		m_ws_server.add("/"+ async_path, (WebSocketHandler)this);
		m_ws_server.staleConnectionTimeout(7000*1000);

		return true;
	}
	
	public boolean start() {
		try {
			if ( null == m_xhr_server.start().get() ||
					null == m_ws_server.start().get() ) return false;
		} catch (InterruptedException | ExecutionException e) {
			return false;
		}
		return true;
	}	
	
	public void stop() {
		if ( null != m_ws_server ) m_ws_server.stop();
		if ( null != m_xhr_server ) m_xhr_server.stop();
	}
	
	public void deinit() {
		if ( null != m_ws_server ) m_ws_server = null;
		if ( null != m_xhr_server ) m_xhr_server = null;
	}
	
	public boolean sendEvent(WebSocketConnection websocket, String event) {
		if ( null!=websocket && null != websocket.send(event) ) {
			CobraLogger.Debug("Sent Event : " + event);
			return true;
		}
		return false;
	}
	
	/**
	 * WebSocket Handler
	 */
	@Override
	public void onOpen(WebSocketConnection arg0) throws Throwable {
		
		m_listener.onConnectedEventSocket(arg0);
	}
	
	@Override
	public void onClose(WebSocketConnection arg0) throws Throwable {
		m_listener.onDisconnectedEventSocket(arg0);
	}

	@Override
	public void onMessage(WebSocketConnection websocket, String arg1)
			throws Throwable {
		CobraLogger.Debug("Received WebSocket Data : " + arg1);
        CobraServerReturn _cobra_return = m_listener.onMessage(arg1, websocket, false);
		try {
    		if ( null!=_cobra_return ) {
    			websocket.send(_cobra_return.getReturnJson());
    			_cobra_return.destory();
    			_cobra_return = null;
    		}
        }catch (RuntimeException e1) {
        }catch (Exception e2) {
        }
	}

	@Override
	public void onMessage(WebSocketConnection arg0, byte[] arg1)
			throws Throwable {
		/* nothing to do for transmission of binary data */
	}

	@Override
	public void onPing(WebSocketConnection arg0, byte[] arg1) throws Throwable {
		/* nothing to do */
	}

	@Override
	public void onPong(WebSocketConnection arg0, byte[] arg1) throws Throwable {
		/* nothing to do */
	}

	/**
	 * XmlHttpRequest Handler for Sync
	 */
	@Override
	public void handleHttpRequest(HttpRequest arg0, HttpResponse arg1,
			HttpControl arg2) throws Exception {
		HttpRequestWrapper request = new HttpRequestWrapper(arg0);
		HttpResponseWrapper response = new HttpResponseWrapper(arg1);
		
		
		String recv_json = request.body();
		CobraLogger.Debug("Received XHR Request : " + recv_json );
		
        CobraServerReturn _cobra_return = m_listener.onMessage(recv_json, null, true);
		try{
    		if ( null!=_cobra_return ) {
    			response.header("Access-Control-Allow-Origin", "*")
    			.content(_cobra_return.getReturnJson()).end();
    		
    			CobraLogger.Debug("Sent XHR Response : " + _cobra_return.getReturnJson());
    		
    			_cobra_return.destory();
    			_cobra_return = null;
    		}
        }catch (RuntimeException e1) {
        }catch (Exception e2) {
        }
		CobraLogger.Debug("");
		CobraLogger.Debug("");
	}
	
	/*
	 * member variables
	 */
	private WebServer m_xhr_server;
	private WebServer m_ws_server;
	private IWebServerListener m_listener;

}
