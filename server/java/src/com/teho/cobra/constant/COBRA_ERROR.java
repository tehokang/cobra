package com.teho.cobra.constant;

public enum COBRA_ERROR {
	ERROR_PARSE				("-32000", "Parse Error"),
	
	ERROR_INVALID_REQUEST	("-32100", "Invalid Request"),
	
	ERROR_NOTFOUND_METHOD	("-32201", "Method not found"),
	ERROR_NOTFOUND_CLASS	("-32202", "Class not found"),
	ERROR_INVALID_PARAMS	("-32203", "Invalid Parameters"),
	ERROR_NOTFOUND_OBJECT	("-32204", "Object not found"),
	
	ERROR_INTERNAL			("-32501", "Internal Error"),
	ERROR_SERVER			("-32600", "Server Error"),
	ERROR_OK				("200",    "Operation Success");
	
	
	COBRA_ERROR(String code, String message) {
		this.m_code = code;
		this.m_message = message;
	}
	
	public String getErrorCode() {
		return this.m_code;
	}
	
	public String getErrorMessage() {
		return this.m_message;
	}
	
	/*
	public String toString() {
		return m_msgtype;
	}
	*/
	
	private String m_code;
	private String m_message;
	
}
