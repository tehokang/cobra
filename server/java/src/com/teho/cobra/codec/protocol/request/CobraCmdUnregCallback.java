package com.teho.cobra.codec.protocol.request;

import com.humaxdigital.util.json.JSONObject;
import com.teho.cobra.constant.MSG_KEY;

public class CobraCmdUnregCallback extends CobraCommand {

	public CobraCmdUnregCallback(String json) {
		super(json);
		
		JSONObject body = getBody();
		m_object_id = (String)decode(body, MSG_KEY.MSG_B_OBJECT_ID.toString());
		m_event_name = (String)decode(body, MSG_KEY.MSG_B_EVENT_NAME.toString());
	}

	@Override
	public void destroy() {
		m_object_id = null;
		m_event_name = null;
		
		super.destroy();
	}
	
	/*
	 * Member Functions
	 */
	public String getObjectId() {
		return m_object_id;
	}
	
	public String getEventName() {
		return m_event_name;
	}
	
	/*
	 * Member Variables
	 */
	private String m_object_id;
	private String m_event_name;
}
