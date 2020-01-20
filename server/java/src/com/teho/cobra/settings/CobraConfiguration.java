package com.teho.cobra.settings;

import com.teho.cobra.constant.COBRA_S_ENUM;

public class CobraConfiguration {
	public static void setConfiguration(int sync_port, String sync_url, int async_port, String async_url, String pkg_path) {
		 m_async_url = async_url;
		 m_sync_url = sync_url;
		 m_sync_port = sync_port;
		 m_async_port = async_port;
		 m_platform_pkg_path = pkg_path;
	}
	
	/*
	 * member functions
	 */
	public static int getSyncPort() {
		return m_sync_port;
	}
	public static int getAsyncPort() { 
		return m_async_port;
	}
	
	public static String getSyncUrl() {
		return m_sync_url;
	}
	
	public static String getAsyncUrl() {
		return m_async_url;
	}
	public static String getPlatformPackagePath() {
		return m_platform_pkg_path;
	}
	
	/*
	 * member variables
	 */
	private static final String DEFAULT_ASYNC = "http://localhost:80/async";
	private static final String DEFAULT_SYNC = "http://localhost:80/sync";
	
	private static String m_platform_pkg_path = COBRA_S_ENUM.PLATFORM_PKG_DEFAULT_NAME.get();
	
	private static String m_async_url = DEFAULT_ASYNC;
	private static String m_sync_url = DEFAULT_SYNC;
	private static int m_sync_port = 9999;
	private static int m_async_port = m_sync_port+1;

}
