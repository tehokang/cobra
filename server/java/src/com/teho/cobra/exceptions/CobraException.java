package com.teho.cobra.exceptions;

import com.teho.cobra.constant.COBRA_ERROR;

public class CobraException extends Exception {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CobraException() {
		// TODO Auto-generated constructor stub
	}

	public CobraException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	
	public CobraException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public CobraException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public CobraException(COBRA_ERROR error) {
		this.m_error = error;
	}

	public COBRA_ERROR getError() {
		return this.m_error;
	}
	
	private COBRA_ERROR m_error;
}
