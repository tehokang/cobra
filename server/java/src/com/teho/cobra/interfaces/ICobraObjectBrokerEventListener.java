package com.teho.cobra.interfaces;

import com.teho.cobra.codec.protocol.response.CobraEvent;
import com.teho.cobra.exceptions.CobraException;

public interface ICobraObjectBrokerEventListener {
	public void onReceiveEventFromBroker(CobraEvent event) throws CobraException;
}
