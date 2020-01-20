package com.teho.cobra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CobraObjectCollector {

	private CobraObjectCollector() {
		
	}
	
	/*
	 * Multi Session 
	 */
	public static int add(String session_id, CobraObject obj) {
		ArrayList<CobraObject> obj_list = null;
		if ( m_objects_map.containsKey(session_id) ) {
			obj_list = (ArrayList<CobraObject>)m_objects_map.get(session_id);
			obj_list.add(obj);
		}else{
			obj_list = new ArrayList<CobraObject>();
			obj_list.add(obj);
			m_objects_map.put(session_id, obj_list);
		}
		return obj_list.size();
	}
	
	public static int remove(String session_id, CobraObject obj) {
		if ( m_objects_map.containsKey(session_id) ) {
			ArrayList<CobraObject> obj_list = (ArrayList<CobraObject>)m_objects_map.get(session_id);
			obj_list.remove(obj);
			return obj_list.size();
		}
		return 0;
	}
	
	public static int removeAll(String session_id) {
		if ( m_objects_map.containsKey(session_id) ) {
			ArrayList<CobraObject> obj_list = (ArrayList<CobraObject>)m_objects_map.get(session_id);
			for ( int i=0; i<obj_list.size(); i++ ) {
				CobraObject tmp_obj = obj_list.get(i);
				tmp_obj.destroy();
			}

			obj_list.clear();
			m_objects_map.remove(session_id);
			return obj_list.size();
		}
		
		return 0;
	}
	
	public static CobraObject find(String session_id, String object_id) {
		if ( m_objects_map.containsKey(session_id) ) {
			ArrayList<CobraObject> obj_list = (ArrayList<CobraObject>)m_objects_map.get(session_id);
			for ( int i=0; i<obj_list.size(); i++ ) {
				CobraObject tmp_obj = obj_list.get(i);
				if(0 == tmp_obj.getObjectId().compareToIgnoreCase(object_id)) {
					return tmp_obj;
				}
			}
		}
		return null;
	}
	
	/*
	 * Single Session 
	 */
	public static int add(CobraObject obj) {
		m_objects.add(obj);
		return m_objects.size();
	}
	
	public static int remove(CobraObject obj) {
		m_objects.remove(obj);
		if ( null!=obj) {
			obj.destroy();
			obj = null;
		}
		return m_objects.size();
	}
	
	public static int removeAll() {
		m_objects.clear();
		return m_objects.size();
	}
	
	public static CobraObject find(String object_id) {
		for ( int i=0; i<m_objects.size(); i++ ) {
			CobraObject tmp_obj = m_objects.get(i);
			if(0 == tmp_obj.getObjectId().compareToIgnoreCase(object_id)) {
				return tmp_obj;
			}
		}
		return null;
	}
	
	private static List<CobraObject> m_objects = new ArrayList<CobraObject>();
	private static Map<String, ArrayList<CobraObject>> m_objects_map = new HashMap<String, ArrayList<CobraObject>>();
	
}
