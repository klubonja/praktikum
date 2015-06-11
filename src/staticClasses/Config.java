package staticClasses;




public abstract class Config {
	/**
	 * unsere config daten in einer static klasse
	 * */
	public final static String GROUP_NAME = "yinyanyolososes";

	
	
	public final static double PROTOKOLL_VERSION = 1.1;
	
	public final static long SECOND = 1000;//millis
	
	public final static int BROADCAST_INTERVAL = 3;//SECONDS
	
	
	public final static int BROADCAST_PORT = 30303;
	public final static int NETWORK_BUFFER_SIZE = 265;
	public final static String BROADCAST_WILDCARD_IP = "255.255.255.255";
	
	public final static int MESSAGE_BUFFER = 1024;
	
	
	public final static int TCP_PORT = 1200;	
	
	public final static String[] EXPANSIONS= {"Chat","Pumping Oil in the Antarctica, fuck yeah!"};
	
	public final static double IP_PROMPT_WINDOW_HEIGHT = 100;
	public final static double IP_PROMPT_WINDOW_WIDTH = 300;
	public final static double LOGIN_PROMPT_WINDOW_WIDTH = 300;
	public final static double LOGIN_PROMPT_WINDOW_HEIGHT = 200;
	
	public final static String BLACKLISTED_MSG = ""
			+ "you have the ip of some evil known protoksaveollviolator, lets try anyway";
}
