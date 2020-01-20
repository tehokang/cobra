package com.teho.cobra.interfaces;

import com.teho.cobra.codec.protocol.request.CobraCommand;
import com.teho.cobra.exceptions.CobraException;

public interface ICommandThreadListener {
	public void onRecvCommandFromThread(CobraCommand cmd) throws CobraException;
}
