package com.teho.cobra;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import com.teho.cobra.codec.parameter.CobraReturn;
import com.teho.cobra.codec.protocol.request.CobraCmdDelete;
import com.teho.cobra.codec.protocol.request.CobraCmdInvoke;
import com.teho.cobra.codec.protocol.request.CobraCmdNew;
import com.teho.cobra.codec.protocol.request.CobraCmdRegCallback;
import com.teho.cobra.codec.protocol.request.CobraCmdUnregCallback;
import com.teho.cobra.codec.protocol.request.CobraCommand;
import com.teho.cobra.codec.protocol.response.CobraEvent;
import com.teho.cobra.constant.COBRA_DATA_TYPE;
import com.teho.cobra.constant.COBRA_ERROR;
import com.teho.cobra.constant.MSG_ACTION_TYPE;
import com.teho.cobra.exceptions.CobraException;
import com.teho.cobra.interfaces.ICobraObjectBrokerEventListener;
import com.teho.cobra.interfaces.ICobraObjectEventListener;
import com.teho.util.CobraLogger;

public class CobraObjectBroker implements ICobraObjectEventListener {

	public CobraObjectBroker(String session_id, ICobraObjectBrokerEventListener listener) {
		m_session_id = session_id;
		m_listener = listener;
	}
	
	/*
	 * Public Member Method
	 */
	public void destroy() {
		CobraObjectCollector.removeAll(m_session_id);
		m_listener = null;
	}
	
	@Override
	public void onRecvEventFromCobraObject(CobraEvent event) throws CobraException {
		if ( null!=m_listener)
			m_listener.onReceiveEventFromBroker(event);
	}
	
	public int doBroker(CobraCommand cmd) throws CobraException {
		// Do Broking !!! 
		switch (cmd.getActionType()) {
			case MSG_NEW:
				newInstance((CobraCmdNew)cmd);
				break;
			case MSG_DELETE:
				deleteInstance((CobraCmdDelete)cmd);
				break;
			case MSG_INVOKE:
				invoke((CobraCmdInvoke)cmd);
				break;
			case MSG_REG_CALLBACK:
				registerCallback((CobraCmdRegCallback)cmd);
				break;
			case MSG_UNREG_CALLBACK:				
				unregisterCallback((CobraCmdUnregCallback)cmd);
				break;
			default:
				break;
		}	
		return 0;
	}
	
	/*
	 * Private Member Method
	 */
	private int newInstance(CobraCmdNew cmd_new) throws CobraException {
		try {
			CobraLogger.Info("[NEW] " + cmd_new.getSimpleClassName());
			Class<?> klass = Class.forName(cmd_new.getFullClassName());
			Constructor<?> constructor = klass.getConstructor(String.class);
			CobraObject obj = (CobraObject)constructor.newInstance(cmd_new.getSessionId());
			
			obj.setEventListener(this);
			
			cmd_new.getCmdResult().getReturn().setValue(COBRA_DATA_TYPE.OBJECT, obj);
			
		} catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException e) {
			CobraException exception = new CobraException(COBRA_ERROR.ERROR_NOTFOUND_CLASS);
			throw exception;
		} catch (IllegalAccessException | SecurityException |InstantiationException e) {
			CobraException exception = new CobraException(COBRA_ERROR.ERROR_INTERNAL);
			throw exception;
		} catch (IllegalArgumentException e) {
			CobraException exception = new CobraException(COBRA_ERROR.ERROR_INVALID_PARAMS);
			throw exception;
		} 
		return 0;
	}
	
	private int deleteInstance(CobraCmdDelete cmd_delete) throws CobraException {
		CobraObject instance = CobraObjectCollector.find(cmd_delete.getSessionId(), cmd_delete.getObjectId());
		if ( null==instance ) {
			cmd_delete.getCmdResult().getReturn().setValue(COBRA_DATA_TYPE.BOOLEAN, false);
			CobraException exception = new CobraException(COBRA_ERROR.ERROR_NOTFOUND_OBJECT);
			throw exception;
		}
		CobraLogger.Info("[DEL] " + instance.getClassName());
		CobraObjectCollector.remove(cmd_delete.getSessionId(), instance);
		cmd_delete.getCmdResult().getReturn().setValue(COBRA_DATA_TYPE.BOOLEAN, true);
		return 0;
	}
	
	private int registerCallback(CobraCmdRegCallback cmd_register_callback) throws CobraException {
		CobraObject instance = CobraObjectCollector.find(cmd_register_callback.getSessionId(), cmd_register_callback.getObjectId());
		if ( null==instance) {
			CobraException exception = new CobraException(COBRA_ERROR.ERROR_NOTFOUND_OBJECT);
			throw exception;
		}
		try {
			Class<?> klass = Class.forName(instance.getClassFullName());
			CobraEvent eventObject = new CobraEvent(cmd_register_callback.getSessionId(), MSG_ACTION_TYPE.MSG_EVENT);
			
			eventObject.setEventListener(this);
			eventObject.setObjectId(cmd_register_callback.getObjectId());
			eventObject.setEventName(cmd_register_callback.getEventName());
			
			CobraLogger.Info("[REG-CALLBACK] Class : " + instance.getClassName());
			CobraLogger.Info("[REG-CALLBACK] Event Name : " + cmd_register_callback.getEventName());

			Method method = klass.getMethod(cmd_register_callback.getEventName(),  CobraEvent.class);
			Boolean returnValue = (Boolean)method.invoke(instance, eventObject);
			cmd_register_callback.getCmdResult().getReturn().setValue(COBRA_DATA_TYPE.BOOLEAN, returnValue);			
		} catch (ClassNotFoundException e) {
			CobraException exception = new CobraException(COBRA_ERROR.ERROR_NOTFOUND_CLASS);
			throw exception;
		} catch (InvocationTargetException | NoSuchMethodException e) {
			CobraException exception = new CobraException(COBRA_ERROR.ERROR_NOTFOUND_METHOD);
			throw exception;
		} catch (IllegalAccessException | SecurityException e) {
			CobraException exception = new CobraException(COBRA_ERROR.ERROR_INTERNAL);
			throw exception;
		} catch (IllegalArgumentException e) {
			CobraException exception = new CobraException(COBRA_ERROR.ERROR_INVALID_PARAMS);
			throw exception;
		}

		return 0;
	}
	
	private int unregisterCallback(CobraCmdUnregCallback cmd_unregister_callback) throws CobraException {
		CobraObject instance = CobraObjectCollector.find(cmd_unregister_callback.getSessionId(), cmd_unregister_callback.getObjectId());
		if ( null==instance) {
			CobraException exception = new CobraException(COBRA_ERROR.ERROR_NOTFOUND_OBJECT);
			throw exception;
		}
		try {
			
			CobraLogger.Info("[Unreg-CALLBACK] Class : " + instance.getClassName());
			CobraLogger.Info("[Unreg-CALLBACK] Event Name : " + cmd_unregister_callback.getEventName());
			
			Class<?> klass = Class.forName(instance.getClassFullName());
			Method method = klass.getMethod(cmd_unregister_callback.getEventName(), CobraEvent.class);
			Boolean returnValue = (Boolean)method.invoke(instance, (CobraEvent)null);
			cmd_unregister_callback.getCmdResult().getReturn().setValue(COBRA_DATA_TYPE.BOOLEAN, returnValue);
		} catch (ClassNotFoundException e) {
			CobraException exception = new CobraException(COBRA_ERROR.ERROR_NOTFOUND_CLASS);
			throw exception;
		} catch (InvocationTargetException | NoSuchMethodException e) {
			CobraException exception = new CobraException(COBRA_ERROR.ERROR_NOTFOUND_METHOD);
			throw exception;
		} catch (IllegalAccessException | SecurityException e) {
			CobraException exception = new CobraException(COBRA_ERROR.ERROR_INTERNAL);
			throw exception;
		} catch (IllegalArgumentException e) {
			CobraException exception = new CobraException(COBRA_ERROR.ERROR_INVALID_PARAMS);
			throw exception;
		}
		return 0;		
	}
	
	private int invoke(CobraCmdInvoke cmd_invoke) throws CobraException {
		CobraObject instance = CobraObjectCollector.find(cmd_invoke.getSessionId(), cmd_invoke.getObjectId());
		if ( null==instance ) {
			CobraException exception = new CobraException(COBRA_ERROR.ERROR_NOTFOUND_OBJECT);
			throw exception;
		}
		
		try {
			CobraLogger.Debug("[INVOKE] Class name : " + instance.getClassFullName());
			CobraLogger.Debug("[INVOKE] Method name : " + cmd_invoke.getMethodName());
			
			Class<?> klass = Class.forName(instance.getClassFullName());
			Method method = klass.getMethod(cmd_invoke.getMethodName(), List.class, CobraReturn.class);
			Boolean returnValue = (Boolean)method.invoke(instance, cmd_invoke.getParameters(), cmd_invoke.getCmdResult().getReturn());		
			if ( null == returnValue || false == returnValue ) {
				
			}
		} catch (ClassNotFoundException e) {
			CobraException exception = new CobraException(COBRA_ERROR.ERROR_NOTFOUND_CLASS);
			throw exception;
		} catch (InvocationTargetException | NoSuchMethodException e) {
			CobraException exception = new CobraException(COBRA_ERROR.ERROR_NOTFOUND_METHOD);
			throw exception;
		} catch (IllegalAccessException | SecurityException e) {
			CobraException exception = new CobraException(COBRA_ERROR.ERROR_INTERNAL);
			throw exception;
		} catch (IllegalArgumentException e) {
			CobraException exception = new CobraException(COBRA_ERROR.ERROR_INVALID_PARAMS);
			throw exception;
		}
		return 0;
	}
	
	/*
	 * Private Member Variables
	 */
	private String m_session_id;
	private ICobraObjectBrokerEventListener m_listener;



	
}
