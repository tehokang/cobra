package com.teho.cobra.codec.protocol.response;

import java.util.ArrayList;
import java.util.List;

import com.humaxdigital.util.json.JSONArray;
import com.humaxdigital.util.json.JSONObject;
import com.teho.cobra.CobraObject;
import com.teho.cobra.codec.parameter.CobraEventParameter;
import com.teho.cobra.codec.parameter.CobraParameter;
import com.teho.cobra.constant.COBRA_DATA_TYPE;
import com.teho.cobra.constant.MSG_ACTION_TYPE;
import com.teho.cobra.constant.MSG_KEY;
import com.teho.cobra.exceptions.CobraException;
import com.teho.cobra.interfaces.ICobraObjectEventListener;

public class CobraEvent extends CobraResponse {
	public CobraEvent(String session_id, MSG_ACTION_TYPE action_type) {
		m_session_id = session_id;
		m_action_type = action_type;
	}
	
	@Override
	public void destroy() {
		m_session_id = null;
		m_object_id = null;
		m_action_type = null;
		m_params = null;
		m_value = null;
		
		m_event_name = null;
		
		for ( int i=0; i<m_event_params.size(); i++ ) {
			CobraParameter param = (CobraParameter)m_event_params.get(i);
			param.destroy();
		}
		m_event_params.clear();
		
		m_event_params = null;
		m_event_listener = null;
		
		super.destroy();
	}
	/*
	 * To make event, It has to call setObjectId, setEventName, setParameters
	 */
	public void setObjectId(String object_id) {
		m_object_id = object_id;
	}
	
	public void setEventName(String event_name) {
		m_event_name = event_name;
	}
	
	public void setParameters(List<CobraEventParameter> event_params) {       
		m_event_params = event_params;
        m_value = null;
        m_params = null;
        m_value = new JSONObject();
        m_params = new JSONArray();
	}
	
	public void setEventListener(ICobraObjectEventListener listener) {
		m_event_listener = listener;
	}
	
	public void fireEvent() throws CobraException {
		if ( null!=m_event_listener )
			m_event_listener.onRecvEventFromCobraObject(this);
	}
	
	public String toString() {
		
		encode(getResult(), MSG_KEY.MSG_B_OBJECT_ID.toString(), m_object_id);
		encode(getResult(), MSG_KEY.MSG_B_EVENT_NAME.toString(), m_event_name);

		addObject(getResult(), MSG_KEY.MSG_B_METHOD_PARAMS.toString(), m_params);

		for ( int i=0; i<m_event_params.size(); i++ ) {
			JSONObject param = new JSONObject();
			COBRA_DATA_TYPE data_type = m_event_params.get(i).getType();
			Object obj = m_event_params.get(i).getValue();
			
			encode(param, MSG_KEY.MSG_B_TYPE.toString(), data_type.toString());
					
			switch ( data_type ) {
				case BOOLEAN:
				case NUMBER:
				case STRING:
					encode(param, MSG_KEY.MSG_B_VALUE.toString(), String.valueOf(obj));
					break;
				case OBJECT:
					addObject(param, MSG_KEY.MSG_B_VALUE.toString(), m_value);
					encode(m_value, MSG_KEY.MSG_B_OBJECT_ID.toString(), String.valueOf(((CobraObject)obj).getObjectId()));
					encode(m_value, MSG_KEY.MSG_B_CLASSNAME.toString(), String.valueOf(((CobraObject)obj).getClassName()));				
					break;
				default:
					break;
			}
			m_params.put(param);
		}
		
		return super.toString();
	}
	
	/*
	 * Member Variables
	 */
	private JSONObject m_value;
	private JSONArray m_params;

	private String m_object_id;
	private String m_event_name;
	private ICobraObjectEventListener m_event_listener;
	private List<CobraEventParameter> m_event_params = new ArrayList<CobraEventParameter>();
	
}
