package com.teho.cobra.constant;

public enum MSG_KEY {
	MSG_HEADER("header"),
	MSG_BODY("body"),
	
	/*
	 * Header Key
	 */
	MSG_H_SESSION_ID("session_id"),
	MSG_H_ACTION("action"),
	
	/*
	 * Body Key
	 */
	MSG_B_TYPE("type"),
	MSG_B_VALUE("value"),
	MSG_B_CLASSNAME("name"),
	MSG_B_OBJECT_ID("object_id"),
	MSG_B_EVENT_NAME("name"),
	MSG_B_METHOD_NAME("name"),
	MSG_B_METHOD_PARAMS("param"),
	MSG_B_METHOD_PARAM_TYPE("type"),
	MSG_B_METHOD_PARAM_VALUE("value"),
	
	MSG_B_RESP_STATUS("status"),
	MSG_B_RESP_ERRORCODE("code"),
	MSG_B_RESP_ERRORDESC("desc"),
	MSG_B_RESP_RESULT("result"),
	MSG_B_RESP_EVT_PARAM("param");
	
	private String tag_name;
	
	MSG_KEY(String tag) {
		this.tag_name = tag;
	}
	
	public String get() {
		return this.tag_name;
	}
	
	public String toString() {
		return get();
	}
}
