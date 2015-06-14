package cluedoClient;

import java.net.InetAddress;
import java.util.ArrayList;

import cluedoNetworkLayer.CluedoGameClient;


public class ServerItem  {
	String groupName;
	InetAddress ip;
	int port;
	ArrayList<CluedoGameClient> gameList;
	
	public ServerItem(String groupName,InetAddress ip, int port) {
		this.groupName = groupName;
		this.ip = ip;
		this.port = port;
		gameList = new ArrayList<CluedoGameClient>();
		
	}
	
	public InetAddress getIp() {
		return ip;
	}
	
	public int getPort() {
		return port;
	}
	
	public String getGroupName() {
		return groupName;
	}
	
	public ArrayList<CluedoGameClient> getGameList() {
		return gameList;
	}
	
	public void addGame(CluedoGameClient cg){
		gameList.add(cg);
	}
	
	public void removeGame(ClientGameItem cg){
		gameList.remove(cg);
	}
	
	public void setIp(InetAddress ip) {
		this.ip = ip;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
}
