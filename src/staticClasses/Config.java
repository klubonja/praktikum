package staticClasses;




public abstract class Config {
	/**
	 * unsere config daten in einer static klasse
	 * */

	//public final static String GROUP_NAME = "yinyanyolosMAC";


	
	
	
	//public final static String GROUP_NAME = "yinyanyolosCIP";
	public final static String GROUP_NAME = auxx.getRandomString(5);

	
	public final static String PROTOKOLL_VERSION = "1.2.1";
	
	public final static long SECOND = 1000;//millis
	
	public final static int BROADCAST_INTERVAL = 10;//SECONDS
	
	
	public final static int BROADCAST_PORT = 30303;
	public final static int NETWORK_BUFFER_SIZE = 265;
	public final static String BROADCAST_WILDCARD_IP = "255.255.255.255";
	
	public final static int MESSAGE_BUFFER = 4096*2;
	
	
	//public final static int TCP_PORT = 1200;	
	public final static int TCP_PORT = auxx.getRandInt(1025,49151);	
	
	public final static String[] EXPANSIONS= {"Chat","Pumping Oil in the Antarctica, fuck yeah!"};
	
	public final static double IP_PROMPT_WINDOW_HEIGHT = 200;
	public final static double IP_PROMPT_WINDOW_WIDTH = 300;
	
	public final static double LOGIN_PROMPT_WINDOW_WIDTH = 300;
	public final static double LOGIN_PROMPT_WINDOW_HEIGHT = 200;
	
	public final static double CLIENT_WINDOW_WIDTH = 700;
	public final static double CLIENT_WINDOW_HEIGHT = 350;
	
	public final static double SERVER_WINDOW_WIDTH = 700;
	public final static double SERVER_WINDOW_HEIGHT = 350;
	public final static double GAME_LIST_ITEM_HEIGHT = 50;
	public final static double NETWORK_ACTOR_LIST_ITEM_HEIGHT = 50;
	
	public final static String BLACKLISTED_MSG = ""
			+ "you have the ip of some evil known protoksaveollviolator, lets try anyway";



	public static final double COLOR_SELECT_WINDOW_WIDTH = 500;



	public static final double COLOR_SELECT_WINDOW_HEIGHT = 200;
	
	public static final int MIN_CLIENTS_FOR_GAMESTART = 3;
	
	
	
	
	
	
	
	
	
}