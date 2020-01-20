package com.teho.util;


/**
 * CobraLogger will be replace by logj4 or slf4j third party logger
 * @author thkang2
 *
 */
public class CobraLogger {		
	/*
	 * member functions
	 */
	static class LogString {
		public String full_class_name;
		public String class_name;
		public String method_name;
		public String line_number;
		
		public LogString() { }
		void set(String full_class_name, String class_name, String method_name, String line_number) {
			this.full_class_name = full_class_name; 
			this.class_name = class_name; 
			this.method_name = method_name; 
			this.line_number=line_number; 
		}
	};
	
	public static void setLogLevel(boolean onDebug, boolean onInfo, boolean onWarning, boolean onError) {
		DEBUG = onDebug;
		INFO = onInfo;
		WARN = onWarning;
		ERROR = onError;
	}
	
	public static void Debug(String msg) {
		/* if it need more meta information, enable below lines */
		//LogString log_meta = new LogString();
		//getMeta(log_meta);
		//if ( DEBUG  ) System.out.println("["+log_meta.class_name+"] ["+
		//								log_meta.method_name+":"+log_meta.line_number+"] "+msg);
		//log_meta = null;
		
		if ( DEBUG ) System.out.println(tag + "[D] " + msg);
		
	}
	
	public static void Info(String msg) {
		if ( INFO ) System.out.println(tag + "[I] " + msg);		
	}
	
	public static void Warn(String msg) {
		if ( WARN ) System.out.println(tag + "[W] " + msg);
	}
	
	public static void Error(String msg) {	
		if ( ERROR ) System.out.println(tag + "[E] " + msg);
	}
	
	private static void getMeta(LogString log_me) {
	     String fullClassName = Thread.currentThread().getStackTrace()[3].getClassName();            
	     String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
	     String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
	     String lineNumber = String.valueOf(Thread.currentThread().getStackTrace()[3].getLineNumber());
	     
	     log_me.set(fullClassName, className, methodName, lineNumber);
	}
	
	/*
	 * member variables
	 */
	private static String tag = "COBRA_SERVER";
	private static boolean DEBUG = true;
	private static boolean INFO	= true;
	private static boolean WARN = true;
	private static boolean ERROR = true;
}
