package com.teho.cobra.zsample.platform;

import java.util.List;

import com.teho.cobra.CobraObject;
import com.teho.cobra.codec.parameter.CobraCmdParameter;
import com.teho.cobra.codec.parameter.CobraReturn;
import com.teho.cobra.constant.COBRA_DATA_TYPE;

public class Contact extends CobraObject {
	
	public Contact(String session_id) {
		super(session_id);
	}

	@Override
	protected void destroy() {
		
	}
	
	public boolean getName(List<CobraCmdParameter> params, CobraReturn cobra_return) {
	
		cobra_return.setValue(COBRA_DATA_TYPE.STRING, m_name);
		
		return true;
	}

	public boolean setName(List<CobraCmdParameter> params, CobraReturn cobra_return) {
		CobraCmdParameter param = params.get(0);
		m_name = (String) param.getValue();
		
		cobra_return.setValue(COBRA_DATA_TYPE.BOOLEAN, true);
		
		return true;
	}

	public boolean getPhone(List<CobraCmdParameter> params, CobraReturn cobra_return) {
		cobra_return.setValue(COBRA_DATA_TYPE.STRING, m_phone);
		
		return true;
	}

	public boolean setPhone(List<CobraCmdParameter> params, CobraReturn cobra_return) {
		CobraCmdParameter param = params.get(0);
		m_phone = (String) param.getValue();
		
		cobra_return.setValue(COBRA_DATA_TYPE.BOOLEAN, true);
		
		return true;
	}

	public boolean getAge(List<CobraCmdParameter> params, CobraReturn cobra_return) {
		cobra_return.setValue(COBRA_DATA_TYPE.NUMBER, m_age);
		
		return true;
	}

	public boolean setAge(List<CobraCmdParameter> params, CobraReturn cobra_return) {
		CobraCmdParameter param = params.get(0);

		m_age = ((Double)param.getValue()).intValue();
		cobra_return.setValue(COBRA_DATA_TYPE.BOOLEAN, true);
		
		return true;
	}
	
	public boolean getSingle(List<CobraCmdParameter> params, CobraReturn cobra_return) {
		
		cobra_return.setValue(COBRA_DATA_TYPE.BOOLEAN, m_single);
		
		return true;
	}

	public boolean setSingle(List<CobraCmdParameter> params, CobraReturn cobra_return) {
		CobraCmdParameter param = params.get(0);
		
		m_single = (Boolean)param.getValue();
		
		cobra_return.setValue(COBRA_DATA_TYPE.BOOLEAN, true);
		
		return true;
	}

	public boolean getAddress(List<CobraCmdParameter> params, CobraReturn cobra_return) {
		
		cobra_return.setValue(COBRA_DATA_TYPE.OBJECT, m_address);
		
		return true;
	}

	public boolean setAddress(List<CobraCmdParameter> params, CobraReturn cobra_return) {
		CobraCmdParameter param = params.get(0);
		m_address = (Address)param.getValue();
		
		cobra_return.setValue(COBRA_DATA_TYPE.BOOLEAN, true);
		
		return true;
	}

	/**
	 * setter by platform
	 */
	public void setName(String name) {
		this.m_name = name;
	}
	public void setPhone(String phone) { 
		this.m_phone = phone;
	}
	public void setAge(int age) {
		this.m_age = age;
	}
	public void setSingle(boolean single) {
		this.m_single = single;
	}
	public void setAddress(Address address) {
		this.m_address = address;
	}
	private String m_name = "";
	private String m_phone = "";
	private int m_age = 0;
	private boolean m_single = false;
	private Address m_address = new Address(getSessionId());

	
	
}
