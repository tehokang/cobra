package Sample;

import com.humaxdigital.cobra.CobraServer;
import com.humaxdigital.util.CobraLogger;

public class SampleMain {

	public SampleMain() {
		// TODO Auto-generated constructor stub
	}

	public static boolean startCobraServer() { 
		/**
		 * Sample Code for Starting CobraServer 
		 */
		CobraServer server1 = new CobraServer();
		server1.init(8080, "sync", 8081, "async", "com.humaxdigital.cobra.zsample.platform", true);
		
		CobraServer server2 = new CobraServer();
		server2.init(8090, "sync", 8091, "async", "com.humaxdigital.cobra.zsample.platform", true);
		
		server1.start();
		return server2.start();
	}
	
	public static void main(String args[]) {
		/**
		 * Sample to start CobraServer
		 */
		if ( true==startCobraServer() ) {
			CobraLogger.Debug("Success to run multi CobraServer ");
		}
	}
}
