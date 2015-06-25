package cluedoClient;

import java.net.InetAddress;

import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;

import cluedoNetworkGUI.DataManager;
import cluedoNetworkLayer.CluedoGame;
import cluedoNetworkLayer.CluedoGameServer;


public class DataManagerClient {
	
	ServerPool serverList;
	ServerItem server;
	
	public DataManagerClient(ServerItem s) {
		server =  s;
	}
	
	public boolean addGame(ServerItem server,CluedoGameServer game){
		return false;
	}

	
	public boolean joinGame(int gameID, String color, String nick) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public boolean checkIpExists(InetAddress adress) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public void notifyAll(String msg) {
		// TODO Auto-generated method stub
		
	}

	
	public int getGameCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public CluedoGame getGameByIndex(int index) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean addServer(ServerItem server){
		return serverList.add(server);
	}
	
	
}
