package com.teho.cobra.codec;

import com.humaxdigital.util.json.JSONObject;

public abstract class CobraJSONDecoder {
	
	public CobraJSONDecoder(String json) {
		m_root = new JSONObject(json);
		m_json = json;
	}
	
	public abstract void destroy();
	
	/*
	 * Member Funtions
	 */
	protected void __destroy() {
		m_json = null;
		m_root = null;		
	}
	protected void setJSON(String json) {
		
	}
	
	public String getJson() {
		return m_json;
	}
	
	protected Object decode(String key) {
		if ( false == m_root.isNull(key) ) {
			return m_root.get(key);
		}
		return null;
	}
	
	protected Object decode(JSONObject obj, String key) {
		if ( null!=obj && false == obj.isNull(key) ) {
			return obj.get(key);
		}
		return null;
	}
	
	/*
	 * Member Variables
	 */
	private String m_json;
	private JSONObject m_root;
}
