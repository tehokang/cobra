package com.teho.cobra.codec.protocol.request;

import com.humaxdigital.util.json.JSONObject;
import com.teho.cobra.constant.MSG_KEY;

public class CobraCmdDelete extends CobraCommand {

	public CobraCmdDelete(String json) {
		super(json);
		
		JSONObject body = getBody();
		m_object_id = (String)decode(body, MSG_KEY.MSG_B_OBJECT_ID.toString());
	}
	
	@Override
	public void destroy() {
		m_object_id = null;
		
		super.destroy();
	}
	
	/*
	 * Member Functions
	 */
	public String getObjectId() {
		return m_object_id;
	}
	
	/*
	 * Member Variables
	 */
	private String m_object_id;
	
	
}
