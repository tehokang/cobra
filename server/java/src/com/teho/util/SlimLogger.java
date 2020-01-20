package com.teho.util;

public class SlimLogger {
	/*
	 * member functions
	 */
	public static void Debug(String msg) {
		/* Pure Java */
		if ( DEBUG ) System.out.println(tag + "[D] " + msg);
		
		/* Android */
		//if(DEBUG) Log.d(tag, "[D]" + msg);
	}
	
	public static void Info(String msg) {
		/* Pure Java */
		if ( INFO ) System.out.println(tag + "[I] " +  msg);

		/* Android */
		//if(INFO) Log.i(tag, "[I]" + msg);
	}	
	
	public static void Warn(String msg) {
		/* Pure Java */
		if ( WARN ) System.out.println(tag + "[W] " +  msg);

		/* Android */
		//if(WARN) Log.w(tag, "[W]" + msg);
	}
	
	public static void Error(String msg) {
		/* Pure Java */
		if ( ERROR ) System.out.println(tag + "[E] " +  msg);

		/* Android */
		//if(ERROR) Log.e(tag, "[E]" + msg);
	}	

	/*
	 * member variables
	 */
	public static boolean DEBUG = true;
	public static boolean INFO = true;
	public static boolean WARN = true;
	public static boolean ERROR = true;
	public static String tag = "COBRA_SERVER";
}
