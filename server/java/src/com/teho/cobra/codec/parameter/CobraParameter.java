package com.teho.cobra.codec.parameter;

import com.teho.cobra.constant.COBRA_DATA_TYPE;

public abstract class CobraParameter {

	public CobraParameter() {

	}

	public void destroy() {
		m_type = null;
		m_value = null;
	}
	
	/*
	 * Member Functions
	 */
	public COBRA_DATA_TYPE getType() {
		return m_type;
	}
	
	public abstract Object getValue();
	
	public abstract void setValue(COBRA_DATA_TYPE type, Object value);
	
	/*
	 * Member Variables
	 */
	protected COBRA_DATA_TYPE m_type = COBRA_DATA_TYPE.NONE;
	protected Object m_value = "";
}
