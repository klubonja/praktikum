package cluedoNetworkGUI;

import java.net.InetAddress;

import cluedoNetworkLayer.CluedoGame;

public abstract class DataManager {
	
	String groupName;
	public DataManager(String groupname) {
		groupName = groupname;
	}
	
	public String getGroupName() {
		return groupName;
	}
	
	public abstract boolean joinGame(int gameID, String color,String nick);
	//public abstract boolean addGame(CluedoGame game);
	public abstract boolean checkIpExists(InetAddress adress);
	public abstract void sendMsgToAllClients(String msg);
	public abstract int getGameCount();
	public abstract CluedoGame getGameByIndex(int index);
	//public abstract CluedoGame getGameByGameID(int gameID);
}
