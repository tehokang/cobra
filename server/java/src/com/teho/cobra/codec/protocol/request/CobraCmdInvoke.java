package com.teho.cobra.codec.protocol.request;

import java.util.ArrayList;
import java.util.List;

import com.humaxdigital.util.json.JSONArray;
import com.humaxdigital.util.json.JSONObject;
import com.teho.cobra.CobraObject;
import com.teho.cobra.CobraObjectCollector;
import com.teho.cobra.codec.parameter.CobraCmdParameter;
import com.teho.cobra.constant.COBRA_DATA_TYPE;
import com.teho.cobra.constant.MSG_KEY;

public class CobraCmdInvoke extends CobraCommand {

	public CobraCmdInvoke(String json) {
		super(json);
		
		JSONObject body = getBody();
		
		m_object_id = (String)decode(body, MSG_KEY.MSG_B_OBJECT_ID.toString());
		m_method_name = (String)decode(body, MSG_KEY.MSG_B_METHOD_NAME.toString());
		
		JSONArray array = (JSONArray)decode(body, MSG_KEY.MSG_B_METHOD_PARAMS.toString());
		if ( null!=array ) {
			for(int i=0; i<array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				COBRA_DATA_TYPE data_type = getDataType((String)decode(obj, MSG_KEY.MSG_B_METHOD_PARAM_TYPE.toString()));
				/* new */
				CobraCmdParameter param = obtainParam(obj, data_type);
				m_params.add(param);
			}
		}
	}

	@Override
	public void destroy() {
		m_object_id = null;
		m_method_name = null;
		
		super.destroy();
	}
	
	private CobraCmdParameter obtainParam(JSONObject obj, COBRA_DATA_TYPE data_type) {
		CobraCmdParameter param = new CobraCmdParameter();
		
		switch ( data_type ) {
			case NUMBER:
			case STRING:
			case BOOLEAN:
				String value = (String)decode(obj, MSG_KEY.MSG_B_METHOD_PARAM_VALUE.toString());
				param.setValue(data_type, value);
				break;
			case OBJECT:
				JSONObject value_object = (JSONObject)decode(obj, MSG_KEY.MSG_B_METHOD_PARAM_VALUE.toString());
				String object_id = (String)decode(value_object, MSG_KEY.MSG_B_OBJECT_ID.toString());
				CobraObject cobra_obj = CobraObjectCollector.find(getSessionId(), object_id);
				param.setValue(data_type, cobra_obj);
				break;
			default:
				break;
		}
		return param;
	}
	
	private COBRA_DATA_TYPE getDataType(String type) {
		for ( COBRA_DATA_TYPE cobra_type:COBRA_DATA_TYPE.values() ){
			if( 0==type.compareToIgnoreCase(cobra_type.toString()) ) {
				return cobra_type;
			}
		}
		return COBRA_DATA_TYPE.NONE;
	}
	/*
	 * Member Functions
	 */
	public String getObjectId() {
		return m_object_id;
	}
	
	public String getMethodName() {
		return m_method_name;
	}
	
	public List<CobraCmdParameter> getParameters() {
		return m_params;
	}
	/*
	 * Member Variables
	 */
	private String m_object_id;
	private String m_method_name;
	private List<CobraCmdParameter> m_params = new ArrayList<CobraCmdParameter>();
}
