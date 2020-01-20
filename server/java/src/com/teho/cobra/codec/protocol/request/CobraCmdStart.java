package com.teho.cobra.codec.protocol.request;

import org.webbitserver.WebSocketConnection;

public class CobraCmdStart extends CobraCommand {

	public CobraCmdStart(String json) {
		super(json);
	}

	@Override
	public void destroy() {
		m_websocket = null;
		super.destroy();
	}
	/*
	 * Member Functions
	 */
	public void setWebSocket(WebSocketConnection websocket) {
		m_websocket = websocket;
	}
	
	public WebSocketConnection getWebSocket() {
		return m_websocket;
	}
	
	/*
	 * Member Variables
	 */
	private WebSocketConnection m_websocket;
}
