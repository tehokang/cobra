package com.teho.cobra.zsample.platform;

import java.util.ArrayList;
import java.util.List;

import com.teho.cobra.CobraObject;
import com.teho.cobra.codec.parameter.CobraCmdParameter;
import com.teho.cobra.codec.parameter.CobraReturn;
import com.teho.cobra.constant.COBRA_DATA_TYPE;

public class ContactCollection extends CobraObject {

	public ContactCollection(String session_id) {
		super(session_id);
		
		/*
		Contact test1 = new Contact();
		Contact test2 = new Contact();
		Contact test3 = new Contact();

		test1.setName("Test1");
		test1.setAge(20);
		test1.setPhone("010-1111-1111");
		test1.setSingle(false);

		test2.setName("Test1");
		test2.setAge(20);
		test2.setPhone("010-1111-1111");
		test2.setSingle(false);

		test3.setName("Test1");
		test3.setAge(20);
		test3.setPhone("010-1111-1111");
		test3.setSingle(false);

		m_contacts.add(test1);
		m_contacts.add(test2);
		m_contacts.add(test3);
		*/
	}

	@Override
	protected void destroy() {


	}

	public boolean getLength(List<CobraCmdParameter> params, CobraReturn cobra_return) {
	
		cobra_return.setValue(COBRA_DATA_TYPE.NUMBER, m_contacts.size());
		
		return true;
	}

	public boolean getItem(List<CobraCmdParameter> params, CobraReturn cobra_return) {
		CobraCmdParameter param = params.get(0);
		int index = ((Double)param.getValue()).intValue();
		
		Contact _contact = m_contacts.get(index);
		
		cobra_return.setValue(COBRA_DATA_TYPE.OBJECT, _contact);
		
		return true;
	}

	public boolean addItem(List<CobraCmdParameter> params, CobraReturn cobra_return) {
		CobraCmdParameter param = params.get(0);
		Contact _contact = (Contact)param.getValue();
		
		m_contacts.add(_contact);
		
		cobra_return.setValue(COBRA_DATA_TYPE.BOOLEAN, true);
		
		return true;
	}
	private List<Contact> m_contacts = new ArrayList<Contact>();
}
