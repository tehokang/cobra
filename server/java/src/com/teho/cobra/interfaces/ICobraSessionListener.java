package com.teho.cobra.interfaces;

import org.webbitserver.WebSocketConnection;

import com.teho.cobra.codec.protocol.response.CobraEvent;

public interface ICobraSessionListener {
	public void onNotifyEvent(WebSocketConnection websocket, CobraEvent event);
}
