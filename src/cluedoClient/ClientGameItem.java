package cluedoClient;

import java.util.ArrayList;

public class ClientGameItem {
	int gameID;
	String servername;
	ArrayList<String> playerNicks;
	
	public ClientGameItem(int gi,String sname) {
		gameID = gi;
		servername = sname;
		playerNicks = new ArrayList<String>();
	}
	
	public void addPlayerNick(String nick){
		playerNicks.add(nick);
	}
	
	public int getPlayerCount(){
		return playerNicks.size();
	}
	
	
}
