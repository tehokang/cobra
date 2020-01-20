package com.teho.cobra;

import com.teho.cobra.codec.protocol.request.CobraRequest;

public class CobraServerReturn {

	public CobraServerReturn(String json, CobraRequest request ) {
		m_return_json = json;
		m_request = request;
	}

	public String getReturnJson() {
		return m_return_json;
	}
	
	public void destory() {
		m_return_json = null;
		m_request.destroy();
		m_request = null;
	}
	
	private String m_return_json;
	private CobraRequest m_request; 
}
