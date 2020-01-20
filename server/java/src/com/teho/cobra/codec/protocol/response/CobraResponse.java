package com.teho.cobra.codec.protocol.response;

import com.humaxdigital.util.json.JSONObject;
import com.teho.cobra.codec.CobraJSONEncoder;
import com.teho.cobra.constant.COBRA_ERROR;
import com.teho.cobra.constant.MSG_ACTION_TYPE;
import com.teho.cobra.constant.MSG_KEY;

public class CobraResponse extends CobraJSONEncoder {

	public CobraResponse() {
		m_header = new JSONObject();
		m_body = new JSONObject();
		m_status = new JSONObject();
		m_result = new JSONObject();
		m_error = COBRA_ERROR.ERROR_OK;
		
		JSONObject root = getRoot();
		
		addObject(root, MSG_KEY.MSG_HEADER.toString(), m_header);
		addObject(root, MSG_KEY.MSG_BODY.toString(), m_body);
		addObject(m_body, MSG_KEY.MSG_B_RESP_RESULT.toString(), m_result);
		addObject(m_body, MSG_KEY.MSG_B_RESP_STATUS.toString(), m_status);
	}
	
	@Override
	public void destroy() {
		m_header = null;
		m_body = null;
		m_status = null;
		m_result = null;
		m_error = null;
		m_session_id = null;
		m_action_type = null;
		
		super.__destroy();
	}
	
	protected void encodeHeader() {
		encode(m_header, MSG_KEY.MSG_H_SESSION_ID.toString(), m_session_id);
		encode(m_header, MSG_KEY.MSG_H_ACTION.toString(), m_action_type.toString());
	}
	
	protected void encodeResult() {
		encode(m_status, MSG_KEY.MSG_B_RESP_ERRORCODE.toString(), m_error.getErrorCode());
		encode(m_status, MSG_KEY.MSG_B_RESP_ERRORDESC.toString(), m_error.getErrorMessage());
	}
	
	@Override
	public String  toString() {
		encodeHeader();
		
		encodeResult();
		
		return super.toString();
	}
	
	public JSONObject getHeader() {
		return m_header;
	}
	
	public JSONObject getBody() {
		return m_body;
	}
	
	public JSONObject getStatus() {
		return m_status;
	}
	
	public JSONObject getResult() {
		return m_result;
	}
	
	public String getSessionId() {
		return m_session_id;
	}
	
	public MSG_ACTION_TYPE getActionType() {
		return m_action_type;
	}
	
	public void setError(COBRA_ERROR error) {
		m_error = error;
	}
	
	public COBRA_ERROR getError() {
		return m_error;
	}
	/*
	 * Member Variables
	 */
	private JSONObject m_header;
	private JSONObject m_body;
	private JSONObject m_status;
	private JSONObject m_result;
	
	protected String m_session_id;
	protected MSG_ACTION_TYPE m_action_type;
	protected COBRA_ERROR m_error;

}
