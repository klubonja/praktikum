package cluedoServer;

import java.net.InetAddress;
import java.util.ArrayList;

import cluedoNetworkGUI.DataManager;
import cluedoNetworkLayer.CluedoGameServer;

public class DataManagerServer extends DataManager {
	
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
	
	public boolean joinGame(int gameID, String color,String nick){
		return gameList.joinGame(gameID, color, nick);
	}
	
	public boolean addNetworkActor(ClientItem client){
		return clientPool.add(client);
	}
	
	public String getNicksConnectedByIndex(int index){
		return getGameByIndex(index).getNicksConnected();
	}
	
	public String getNicksConnectedByGameID(int gameID){
		return gameList.getGameByGameID(gameID).getNicksConnected();
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
	
	public void notifyAll(String msg){
		clientPool.notifyAll(msg);
	}
	
	public boolean blacklist(ClientItem client){
		if (clientPool.contains(client))
			clientPool.remove(client);
		return blackList.add(client);
	}
	
	public int getGameCount(){
		return gameList.size();
	}
	public boolean addClient(ClientItem client){
		if (!hasClient(client))
			return clientPool.add(client);
		return false;
	}
	public boolean removeClientfromSystem(ClientItem client){
		for (CluedoGameServer cgs: gameList){
			cgs.findAndRemovePlayer(client);
		}
		
		return clientPool.remove(client);
	}
	
	public boolean hasClient(ClientItem client){
		for (ClientItem c: clientPool)
			if (c.getNick().equals(client.getNick()))
					return true;
		return false;
 	}
}
	

