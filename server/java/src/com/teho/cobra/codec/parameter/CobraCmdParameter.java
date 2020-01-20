package com.teho.cobra.codec.parameter;

import com.teho.cobra.CobraObject;
import com.teho.cobra.CobraObjectCollector;
import com.teho.cobra.constant.COBRA_DATA_TYPE;

public class CobraCmdParameter extends CobraParameter {

	public CobraCmdParameter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setValue(COBRA_DATA_TYPE type, Object value) {
		m_value = value;
		m_type = type;
	}


	@Override
	public Object getValue() {
		switch ( m_type ) {
			case BOOLEAN:
				return Boolean.valueOf((String)m_value);
			case NUMBER:
				return Double.valueOf((String)m_value);
			case STRING:
				return String.valueOf(m_value);
			case OBJECT:
				return (CobraObject)m_value;
			default:
				break;
		}
		return COBRA_DATA_TYPE.NULL.toString();
	}
}
