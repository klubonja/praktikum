package cluedoServer;

import java.net.InetAddress;
import java.util.ArrayList;

import cluedoNetworkGUI.DataManager;
import cluedoNetworkLayer.CluedoGameServer;
import enums.JoinGameStatus;

public class DataManagerServer extends DataManager {
	
	ClientPool clientPool;
	ArrayList<ClientItem> blackList;
	GameListServer gamesList;
	
	public DataManagerServer(String groupname) {
		super(groupname);
		clientPool = new ClientPool();
		blackList = new ArrayList<ClientItem>();
		gamesList = new GameListServer();
	}
	

	
	public ArrayList<ClientItem> getBlackList() {
		return blackList;
	}
	public ClientPool getClientPool() {
		return clientPool;
	}
	public GameListServer getGameList() {
		return gamesList;
	}
	
	public ArrayList<CluedoGameServer> getGameListArray() {
		return gamesList;
	}
	
	@Override
	public CluedoGameServer getGameByIndex(int index) throws ArrayIndexOutOfBoundsException{
		return gamesList.get(index);
	}
	
	public ArrayList<CluedoGameServer> getGamesByPlayer(String nick){
		ArrayList<CluedoGameServer> glist = new ArrayList<CluedoGameServer>();
		for (CluedoGameServer cg: gamesList){
			cg.getNicksConnected().contains(nick);
			glist.add(cg);
		}
		return glist;
	}
	
	public JoinGameStatus joinGame(int gameID, String color,ClientItem client){
		return gamesList.joinGame(gameID, color, client);
	}
	
	public boolean addNetworkActor(ClientItem client){
		return clientPool.add(client);
	}
	
	public String getNicksConnectedByIndex(int index){
		return getGameByIndex(index).getNicksConnected();
	}
	
	public String getNicksConnectedByGameID(int gameID){
		return gamesList.getGameByID(gameID).getNicksConnected();
	}
	
	public boolean addGame(CluedoGameServer game){
		return gamesList.add(game);
	}
	
	@Override
	public boolean checkIpExists(InetAddress adress){
		return clientPool.checkForExistingIp(adress);
	}
	
	public boolean isBlacklisted(InetAddress adress){
		for (ClientItem c : blackList)
			if (adress.equals(c.getAdress())) return true;
		return false;
	}
	
	@Override
	public void notifyAll(String msg){
		clientPool.notifyAll(msg);
	}
	
	public boolean blacklist(ClientItem client){
		if (clientPool.contains(client))
			clientPool.remove(client);
		return blackList.add(client);
	}
	
	@Override
	public int getGameCount(){
		return gamesList.size();
	}
	public boolean addClient(ClientItem client){
		if (!hasClient(client))
			return clientPool.add(client);
		return false;
	}
	public boolean removeClientfromSystem(ClientItem client){
		for (CluedoGameServer cgs: gamesList){
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
	
	@Override
	public boolean joinGame(int gameID, String color, String nick) {
		// TODO Auto-generated method stub
		return false;
	}
}
	

