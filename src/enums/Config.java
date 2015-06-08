package enums;



public final class Config {
	/**
	 * unsere config daten in einer static klasse
	 */
	private Config(){} // keine Instanzen
	
	
	
	
	public final static String GroupName = "yinyanyolos";
	
	public final static double protokollVersion = 1.1;
	
	public final static long SECOND = 1000;//millis
	
	public final static int BroadcastInterval = 3;//SECONDS
	public final static boolean ServerBroadcastOnce = true;
	
	
	public final static int BroadcastPort = 30303;
	public final static int networkBufferSize = 265;
	public final static String BroadcastIp = "255.255.255.255";
	
	
	
	/**
	 * wildcard ip
	 * wird aber nicht gebraucht
	 */
	public final static String BroadcastListenerIp = "0.0.0.0";	
	//public final static String BroadcastListenerIp = "230.0.0.1";	
	
	public final static int TCPport = 9000;	
	
	
}
