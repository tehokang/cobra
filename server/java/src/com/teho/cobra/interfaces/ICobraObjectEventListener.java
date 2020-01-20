package com.teho.cobra.interfaces;

import com.teho.cobra.codec.protocol.response.CobraEvent;
import com.teho.cobra.exceptions.CobraException;

public interface ICobraObjectEventListener {
	public void onRecvEventFromCobraObject(CobraEvent event) throws CobraException;
}
