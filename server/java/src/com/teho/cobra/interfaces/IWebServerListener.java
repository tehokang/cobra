package com.teho.cobra.interfaces;

import org.webbitserver.WebSocketConnection;

import com.teho.cobra.CobraServerReturn;

public interface IWebServerListener {
	public CobraServerReturn onMessage(String recvJson, WebSocketConnection websocket, boolean sync);
	
	public void onConnectedEventSocket(WebSocketConnection websocket);
	
	public void onDisconnectedEventSocket(WebSocketConnection websocket);
}
