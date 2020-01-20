package com.teho.cobra.codec.protocol.request;

import com.humaxdigital.util.json.JSONObject;
import com.teho.cobra.codec.CobraJSONDecoder;
import com.teho.cobra.codec.protocol.response.CobraCmdResult;
import com.teho.cobra.constant.MSG_ACTION_TYPE;
import com.teho.cobra.constant.MSG_KEY;

public class CobraRequest extends CobraJSONDecoder {

	public CobraRequest(String json) {
		super(json);
		
		m_header = (JSONObject)decode(MSG_KEY.MSG_HEADER.toString());
		m_body = (JSONObject)decode(MSG_KEY.MSG_BODY.toString());
		
		m_session_id = (String)decode(m_header, MSG_KEY.MSG_H_SESSION_ID.toString());
		m_action_type = getActionType((String)decode(m_header, MSG_KEY.MSG_H_ACTION.toString()));
		
		m_cmd_result = new CobraCmdResult(m_session_id, m_action_type);
	}

	public void destroy() {
		m_header = null;
		m_body = null;
		m_session_id = null;
		m_action_type = null;
		
		m_cmd_result.destroy();
		m_cmd_result = null;
		
		super.__destroy();
	}
	/*
	 * Member Functions
	 */
	public String getSessionId() {
		return m_session_id;
	}
	
	public MSG_ACTION_TYPE getActionType() {
		return m_action_type;
	}
	
	public CobraCmdResult getCmdResult() {
		return m_cmd_result;
	}
	
	public boolean isSyncCall() {
		return m_is_sync;
	}
	
	public void setSyncCall(boolean is_sync) {
		m_is_sync = is_sync;
	}
	
	protected JSONObject getBody() {
		return m_body;
	}
	
	private MSG_ACTION_TYPE getActionType(String action) {
		for ( MSG_ACTION_TYPE msg:MSG_ACTION_TYPE.values() ){
			if ( 0==action.compareTo(msg.toString()) )
				return msg;	
		}
		return MSG_ACTION_TYPE.MSG_UNKNOWN;
	}
	
	
	/*
	 * Member Variables
	 */
	private JSONObject m_header;
	private JSONObject m_body;
	private String m_session_id;
	private MSG_ACTION_TYPE m_action_type;
	private boolean m_is_sync;
	private CobraCmdResult m_cmd_result;
	
}
