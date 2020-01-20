package com.teho.cobra.constant;

public enum COBRA_S_ENUM {
	PLATFORM_PKG_DEFAULT_NAME("com.humaxdigital.cobra.platform.");
	
	COBRA_S_ENUM(String name) {
		this.m_name = name;
	}
	
	public String get() { 
		return this.m_name;
	}
	
	private String m_name = null;
}
