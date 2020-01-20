package com.teho.cobra.codec.parameter;

import com.teho.cobra.CobraObject;
import com.teho.cobra.constant.COBRA_DATA_TYPE;

public class CobraEventParameter extends CobraParameter {

	public CobraEventParameter() {
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
				return Boolean.valueOf((boolean)m_value);
			case NUMBER:
				String _tmp = String.valueOf(m_value);
				return Double.valueOf((String)_tmp);
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
