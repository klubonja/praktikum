package staticClasses;




public abstract class Config {
	/**
	 * unsere config daten in einer static klasse
	 * */


	
	
	
	//public final static String GROUP_NAME = "yinyanyolos";
	public final static String GROUP_NAME = auxx.getRandomString(5);

	
	public final static String PROTOKOLL_VERSION = "1.2.1";
	
	public final static long SECOND = 1000;//millis
	
	public final static int BROADCAST_INTERVAL = 10;//SECONDS
	
	
	public final static int BROADCAST_PORT = 30303;
	public final static int NETWORK_BUFFER_SIZE = 256;
	public final static String BROADCAST_WILDCARD_IP = "255.255.255.255";
	
	public final static int MESSAGE_BUFFER = 4096*3;
	
	public final static int HOWMANYOFDEMCARDS = 19;
	public final static int ALLOFDEMCARDS = 21;
	
	public final static int COLUMNS = 25;
	public final static int ROWS = 26;
	
	//public final static int TCP_PORT = 1200;	
	public final static int TCP_PORT = auxx.getRandInt(1025,49151);	
	
	public final static String[] EXPANSIONS= {"Chat"};
	
	public final static double IP_PROMPT_WINDOW_HEIGHT = 200;
	public final static double IP_PROMPT_WINDOW_WIDTH = 300;
	
	
	
	public final static double LOGIN_PROMPT_WINDOW_WIDTH = 300;
	public final static double LOGIN_PROMPT_WINDOW_HEIGHT = 200;
	
	public final static double CLIENT_WINDOW_WIDTH = 800;
	public final static double CLIENT_WINDOW_HEIGHT = 600;
	
	public final static double SERVER_WINDOW_WIDTH = 700;
	public final static double SERVER_WINDOW_HEIGHT = 350;
	public final static double GAME_LIST_ITEM_HEIGHT = 60;
	public final static double NETWORK_ACTOR_LIST_ITEM_HEIGHT = 50;
	
	public final static double MAIN_MENU_WINDOW_WIDTH = 1000;
	public final static double MAIN_MENU_WINDOW_HEIGHT = 700;
	
	
	public final static String BLACKLISTED_MSG = ""
			+ "you have the ip of some evil known protoksaveollviolator, lets try anyway";

	public final static double SHOWKARTEN_HEIGHT = 300;
	public static double SHOWKARTEN_WIDTH = 400;
	
	public static double COLOR_SELECT_WINDOW_WIDTH = 300;
	
	public final static double SUSPICION_HEIGHT = 300;
	public final static double SUSPICION_WIDTH = 400;
	




	

	public static final double COLOR_SELECT_WINDOW_HEIGHT = 300;
	
	public static final int MIN_CLIENTS_FOR_GAMESTART = 1;


	public static final double GAMEWINDOW_HEIGHT = 750;


	public static final double GAMEWINDOW_WIDTH = 1450;
	
	public static final String TCP_MESSAGE_DELIMITER = "\n";
	public static final String TCP_MESSAGE_DELIMITER_WINDOWS = "\r";
	public static final String TCP_MESSAGE_DELIMITER_REGEX = "\\r?\\n";
	
	
	
	
	
	
	
	
	

}

