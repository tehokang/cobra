package com.teho.cobra.constant;

public enum MSG_ACTION_TYPE {
	MSG_START("start"),
	MSG_STOP("stop"),
	MSG_NEW("new"),
	MSG_DELETE("delete"),
	MSG_INVOKE("invoke"),
	MSG_REG_CALLBACK("registercallback"),
	MSG_UNREG_CALLBACK("unregistercallback"),
	MSG_EVENT("event"),
	MSG_UNKNOWN("unknown_action"),
	MSG_NONE("");
	
	MSG_ACTION_TYPE(String arg) {
		this.m_msgtype = arg;
	}
	
	public String get() {
		return m_msgtype;
	}	
	
	public String toString() {
		return m_msgtype;
	}
	
	private String m_msgtype;
	
}
