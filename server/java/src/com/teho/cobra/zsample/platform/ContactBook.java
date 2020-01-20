package com.teho.cobra.zsample.platform;

import java.util.ArrayList;
import java.util.List;

import com.teho.cobra.CobraObject;
import com.teho.cobra.codec.parameter.CobraCmdParameter;
import com.teho.cobra.codec.parameter.CobraEventParameter;
import com.teho.cobra.codec.parameter.CobraReturn;
import com.teho.cobra.codec.protocol.response.CobraEvent;
import com.teho.cobra.constant.COBRA_DATA_TYPE;
import com.teho.cobra.exceptions.CobraException;

public class ContactBook extends CobraObject {
	
	public ContactBook(String session_id) {
		super(session_id);
	}

	@Override
	protected void destroy() {
		
	}

	public boolean getContacts(List<CobraCmdParameter> params, CobraReturn cobra_return) {
		
		cobra_return.setValue(COBRA_DATA_TYPE.OBJECT, m_contact_collection);
		
		return true;
	}

	public boolean setContact(List<CobraCmdParameter> params, CobraReturn cobra_return) {
		CobraCmdParameter param = params.get(0);
		
		m_contact = (Contact)param.getValue();
		
		cobra_return.setValue(COBRA_DATA_TYPE.BOOLEAN, true);
		
		return true;
	}
	
	public boolean getContact(List<CobraCmdParameter> params, CobraReturn cobra_return) {
		
		cobra_return.setValue(COBRA_DATA_TYPE.OBJECT, m_contact);

		return true;
	}

	public boolean onAdded(CobraEvent event) {
		if ( null!=event ){
			// TODO: register 
			m_event = event;
			testDoEvent();
		}else{
			// TODO: unregister
			m_event = null;
		}
		return true;
	}
	
	private void testDoEvent() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(5000);
					if ( null!=m_event ) {
						List<CobraEventParameter> params = new ArrayList<CobraEventParameter>();
						
						CobraEventParameter param1 = new CobraEventParameter();
						CobraEventParameter param2 = new CobraEventParameter();
						CobraEventParameter param3 = new CobraEventParameter();
						CobraEventParameter param4 = new CobraEventParameter();
	
						param1.setValue(COBRA_DATA_TYPE.NUMBER, 1);
						param2.setValue(COBRA_DATA_TYPE.STRING, "string parameter");
						param3.setValue(COBRA_DATA_TYPE.BOOLEAN, true);
						param4.setValue(COBRA_DATA_TYPE.OBJECT, m_contact);
						
						params.add(param1);
						params.add(param2);
						params.add(param3);
						params.add(param4);
						
						m_event.setParameters(params);
						m_event.fireEvent();
					}
				} catch (CobraException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}				
			}
		}).start();		
	}
	
	private ContactCollection m_contact_collection = new ContactCollection(getSessionId());
	private CobraEvent m_event = null;
	public Contact m_contact = new Contact(getSessionId());
}
