package com.teho.cobra.codec.protocol.response;

import com.humaxdigital.util.json.JSONObject;
import com.teho.cobra.CobraObject;
import com.teho.cobra.codec.parameter.CobraReturn;
import com.teho.cobra.constant.COBRA_DATA_TYPE;
import com.teho.cobra.constant.COBRA_ERROR;
import com.teho.cobra.constant.MSG_ACTION_TYPE;
import com.teho.cobra.constant.MSG_KEY;

public class CobraCmdResult extends CobraResponse {

	public CobraCmdResult(String session_id, MSG_ACTION_TYPE action_type) {		
		m_session_id = session_id;
		m_action_type = action_type;
		
		m_type = new JSONObject();
		m_value = new JSONObject();
		
		m_return = new CobraReturn();
	}

	@Override
	public void destroy() {
		m_type = null;
		m_value = null;
		m_return = null;
		super.destroy();
	}
	
	/*
	 * Member Functions
	 */
	public void setSessionId(String session_id) {
		m_session_id = session_id;
	}
	
	public CobraReturn getReturn() {
		return m_return;
	}
	
	public COBRA_ERROR getError() {
		return m_error;
	}
	
	public void setError(COBRA_ERROR error) {
		m_error = error;
	}
	
	public String toString() {	
		COBRA_DATA_TYPE data_type = m_return.getType();
		Object return_object = m_return.getValue();
		
		addObject(getResult(), MSG_KEY.MSG_B_TYPE.toString(), m_type);
		encode(getResult(), MSG_KEY.MSG_B_TYPE.toString(), data_type.toString());

		switch (data_type) {
			case BOOLEAN:
			case NUMBER:
			case STRING:
				encode(getResult(), MSG_KEY.MSG_B_VALUE.toString(), String.valueOf(return_object));
				break;
			case OBJECT:
				addObject(getResult(), MSG_KEY.MSG_B_VALUE.toString(), m_value);
				encode(m_value, MSG_KEY.MSG_B_OBJECT_ID.toString(), String.valueOf(((CobraObject)return_object).getObjectId()));
				encode(m_value, MSG_KEY.MSG_B_CLASSNAME.toString(), String.valueOf(((CobraObject)return_object).getClassName()));				
				break;
			default:
				break;
		}
		return super.toString();
	}
	
	/*
	 * Member Variables
	 */
	private JSONObject m_type;
	private JSONObject m_value;
	
	private CobraReturn m_return;
}
