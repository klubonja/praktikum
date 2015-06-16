package cluedoServer;

import java.net.InetAddress;
import java.util.ArrayList;

import cluedoNetworkLayer.CluedoGameServer;

public class DataManagerServer {
	
	ClientPool clientPool;
	ArrayList<ClientItem> blackList;
	GameListServer gameList;
	public DataManagerServer() {
		clientPool = new ClientPool();
		blackList = new ArrayList<ClientItem>();
		gameList = new GameListServer();
	}
	
	public ArrayList<ClientItem> getBlackList() {
		return blackList;
	}
	public ClientPool getClientPool() {
		return clientPool;
	}
	public GameListServer getGameList() {
		return gameList;
	}
	
	public CluedoGameServer getGameByIndex(int index) throws ArrayIndexOutOfBoundsException{
		return gameList.get(index);
	}
	
	public String getNicksConnectedByIndex(int index){
		return getGameByIndex(index).getNicksConnected();
	}
	
	public boolean addGame(CluedoGameServer game){
		return gameList.add(game);
	}
	
	public boolean checkIpExists(InetAddress adress){
		return clientPool.checkForExistingIp(adress);
	}
	
	public boolean isBlacklisted(InetAddress adress){
		for (ClientItem c : blackList)
			if (adress.equals(c.getAdress())) return true;
		return false;
	}
}	

