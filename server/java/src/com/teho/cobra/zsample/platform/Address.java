package com.teho.cobra.zsample.platform;

import java.util.List;

import com.teho.cobra.CobraObject;
import com.teho.cobra.codec.parameter.CobraCmdParameter;
import com.teho.cobra.codec.parameter.CobraReturn;
import com.teho.cobra.constant.COBRA_DATA_TYPE;

public class Address extends CobraObject {

	
	public Address(String session_id) {
		super(session_id);		
	}

	@Override
	protected void destroy() {
				
	}

	public boolean getName(List<CobraCmdParameter> params, CobraReturn cobra_return) {
		cobra_return.setValue(COBRA_DATA_TYPE.STRING, m_name);
		return true;
	}
	
	public boolean getCity(List<CobraCmdParameter> params, CobraReturn cobra_return) {
		cobra_return.setValue(COBRA_DATA_TYPE.STRING, m_city);
		return true;
	}
	
	public boolean setName(List<CobraCmdParameter> params, CobraReturn cobra_return) {
		CobraCmdParameter param = params.get(0);
		this.m_name = (String)param.getValue();
		
		cobra_return.setValue(COBRA_DATA_TYPE.BOOLEAN, true);

		return true;
	}
	
	public boolean setCity(List<CobraCmdParameter> params, CobraReturn cobra_return) {
		CobraCmdParameter param = params.get(0);
		this.m_city = (String)param.getValue();
		
		cobra_return.setValue(COBRA_DATA_TYPE.BOOLEAN, true);
		
		return true;
	}

	private String m_name = "";
	private String m_city = "";
}
