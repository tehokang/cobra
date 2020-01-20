package com.teho.cobra.codec.protocol.request;

import com.humaxdigital.util.json.JSONObject;
import com.teho.cobra.constant.MSG_KEY;
import com.teho.cobra.settings.CobraConfiguration;

public class CobraCmdNew extends CobraCommand {

	public CobraCmdNew(String json) {
		super(json);
		
		JSONObject body = getBody();
		m_class_name = (String)decode(body, MSG_KEY.MSG_B_CLASSNAME.toString());
		m_full_class_name = CobraConfiguration.getPlatformPackagePath() + "." + m_class_name;
	}

	@Override
	public void destroy() {
		m_class_name = null;
		m_full_class_name = null;
		
		super.destroy();
	}

	/*
	 * Member Functions
	 */
	public String getSimpleClassName() {
		return m_class_name;
	}
	
	public String getFullClassName() {
		return m_full_class_name;
	}
	
	/*
	 * Member Variables
	 */
	private String m_class_name;
	private String m_full_class_name;
}
