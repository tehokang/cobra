package com.teho.cobra;

import com.teho.cobra.interfaces.ICobraObjectEventListener;

public abstract class CobraObject {

	/**
	 * 
	 * @param session_id
	 */
	public CobraObject(String session_id) {
		m_session_id = session_id;
		m_class_name = getClass().getSimpleName();
		m_object_id = String.valueOf(hashCode());
		CobraObjectCollector.add(session_id, this);
	}
	
	protected abstract void destroy();
	
	/**
	 * 
	 * @return
	 */
	protected String getSessionId() {
		return m_session_id;
	}
	
	
	/*
	 * Public Member Methods by using CobraCore
	 */
	
	/**
	 * 
	 * @param listener
	 */
	public void setEventListener(ICobraObjectEventListener listener) {
		m_listener = listener;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getObjectId() {
		return m_object_id;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getClassName() {
		return m_class_name;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getClassFullName() {
		return getClass().getName();
	}
	
	/*
	 * Private Member Variables
	 */
	private String m_session_id;
	private String m_object_id;
	private String m_class_name;
	private ICobraObjectEventListener m_listener;
	
}
