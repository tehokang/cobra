package com.teho.cobra.codec;

import com.humaxdigital.util.json.JSONArray;
import com.humaxdigital.util.json.JSONObject;

public abstract class CobraJSONEncoder {

	public CobraJSONEncoder() {
		m_root = new JSONObject();
	}

	public abstract void destroy();
	
	public void __destroy() {
		m_root = null;
	}
	
	public JSONObject getRoot() {
		return m_root;
	}
	
	public void addObject(JSONObject parent, String key, JSONObject obj) {
		parent.put(key, obj);
	}
	
	public void addObject(JSONObject parent, String key, JSONArray obj) {
		parent.put(key, obj);
	}
	
	public void encode(JSONObject obj, String key, String value) {
		obj.put(key, value);
	}
	
	public String toString() {
		return m_root.toString();
	}
	/*
	 * Member Variables
	 */
	private JSONObject m_root;
}
