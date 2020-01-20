package com.teho.cobra.interfaces;

import com.teho.cobra.codec.protocol.response.CobraEvent;

public interface IEventThreadListener {
	public void onRecvEventFromThread(CobraEvent event);
}
