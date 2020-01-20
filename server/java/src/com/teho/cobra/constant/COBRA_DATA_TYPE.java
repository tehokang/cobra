package com.teho.cobra.constant;

public enum COBRA_DATA_TYPE {
	BOOLEAN("boolean"),
	NUMBER("number"),
	STRING("string"),
	OBJECT("object"),
	
	NULL(""),
	NONE("");
	
	COBRA_DATA_TYPE(String arg) {
		this.m_type = arg;
	}
	
	public String get() {
		return m_type;
	}
	
	public String toString() {
		return m_type;
	}

	private String m_type;
	
}
