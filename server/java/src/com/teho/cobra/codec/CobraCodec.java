package com.teho.cobra.codec;

import com.humaxdigital.util.json.JSONException;
import com.teho.cobra.codec.protocol.request.CobraCmdDelete;
import com.teho.cobra.codec.protocol.request.CobraCmdInvoke;
import com.teho.cobra.codec.protocol.request.CobraCmdNew;
import com.teho.cobra.codec.protocol.request.CobraCmdRegCallback;
import com.teho.cobra.codec.protocol.request.CobraCmdStart;
import com.teho.cobra.codec.protocol.request.CobraCmdStop;
import com.teho.cobra.codec.protocol.request.CobraCmdUnregCallback;
import com.teho.cobra.codec.protocol.request.CobraRequest;
import com.teho.cobra.codec.protocol.response.CobraCmdResult;
import com.teho.cobra.codec.protocol.response.CobraEvent;
import com.teho.cobra.constant.COBRA_ERROR;
import com.teho.cobra.exceptions.CobraException;

public class CobraCodec {
	/*
	 * member functions
	 */
	public static CobraRequest decodeCommand(String json) throws CobraException {
		CobraRequest request = new CobraRequest(json);
		try{
			switch ( request.getActionType() ) {
				case MSG_START:
					return new CobraCmdStart(request.getJson());
				case MSG_STOP:
					return new CobraCmdStop(request.getJson());
				case MSG_NEW:
					return new CobraCmdNew(request.getJson());
				case MSG_DELETE:
					return new CobraCmdDelete(request.getJson());
				case MSG_INVOKE:
					return new CobraCmdInvoke(request.getJson());
				case MSG_REG_CALLBACK:
					return new CobraCmdRegCallback(request.getJson());
				case MSG_UNREG_CALLBACK:
					return new CobraCmdUnregCallback(request.getJson());
				default:
					throw new CobraException(COBRA_ERROR.ERROR_INVALID_REQUEST);
			}
		}catch(JSONException e) {
			throw new CobraException(COBRA_ERROR.ERROR_INVALID_REQUEST);
		}
	}
	
	public static String encodeEventResult(CobraEvent event) {
		return event.toString();
	}

	public static String encodeCommandResult(CobraCmdResult cmd_result) {
		return cmd_result.toString();
	}
}
