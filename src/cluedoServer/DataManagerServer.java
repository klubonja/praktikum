package cluedoServer;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import org.json.JSONObject;

import staticClasses.NetworkMessages;
import staticClasses.auxx;
import cluedoNetworkGUI.DataManager;
import cluedoNetworkLayer.CluedoGameServer;
import cluedoNetworkLayer.CluedoPosition;
import enums.GameStates;
import enums.JoinGameStatus;

public class DataManagerServer extends DataManager {
	
	ClientPool clientPool;
	ArrayList<ClientItem> blackList;
	GameListServer gamesList;
	private String suspector;
	
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
	
	public Vector<CluedoGameServer> getGameListArray() {
		return gamesList;
	}
	
	@Override
	public CluedoGameServer getGameByIndex(int index) throws ArrayIndexOutOfBoundsException{
		return gamesList.get(index);
	}
	
	public CluedoGameServer getGameByID(int gameID){
		return gamesList.getGameByID(gameID);
	}
	
	public boolean startGameByID(int gameID,String nick){
		return gamesList.getGameByID(gameID).start();
	}
	
	public ClientItem getClientByNick(String nick){
		for (ClientItem client: clientPool)
			if (client.getNick().equals(nick))
				return client;
		return null;
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
		return gamesList.joinGameById(gameID, color, client);
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
	
	public boolean removeGame(CluedoGameServer game){
		return gamesList.remove(game);
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
		Iterator<CluedoGameServer> iter = gamesList.iterator();
		//for (CluedoGameServer cgs: gamesList){
		while(iter.hasNext()){
			CluedoGameServer cgs = iter.next();
			cgs.findAndRemovePlayer(client);
			cgs.findAndRemoveWatcher(client);
			if (cgs.getNumberConnected() == 0){
				cgs.notifyAll(NetworkMessages.game_endedMsg(cgs.getGameId(), cgs.getWinningStatement()));
				if (removeGame(cgs)) cgs.notifyAll(NetworkMessages.game_deletedMsg(cgs.getGameId()));								
			}
			else if (cgs.getGameState() == GameStates.ended){
				cgs.notifyAll(NetworkMessages.game_endedMsg(cgs.getGameId(), cgs.getWinningStatement()));
			}
			else if (cgs.getGameState() == GameStates.not_started){
				cgs.notifyAll(NetworkMessages.user_leftMsg(client.getNick()));
			}		
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



	public String getSuspector() {
		return suspector;
	}



	public void setSuspector(String suspector) {
		this.suspector = suspector;
	}
	
	public void rollDiceRequest(int gameID,ClientItem client){
		CluedoGameServer game = validatedClientGame(gameID, client);
		if (game != null){
			game.rollDice(client);	
		}		
	}
	
	public void endTurnRequest(int gameID,ClientItem client){
		CluedoGameServer game = validatedClientGame(gameID, client);
		if (game != null){
			game.endTurnRequest(client);
//			else {
//				game.sendMsgToParticipants(
//						NetworkMessages.chatMsg(
//							"@"+client.getNick()+": cant end turn at this point idiot, possible moves are :  "+game.getPlayerByClient(client).getStatesAsString(),
//							game.getGameId(), 
//							auxx.now()
//							)
//						);
//			}
		}
	}
	
	public void moveRequest(int gameID, ClientItem client,CluedoPosition newpos){
		CluedoGameServer game = validatedClientGame(gameID, client);
		if (game != null){
			if (game.movePlayer(client ,newpos)) {
		   		//ach 
			}
		}
	}
	
	private boolean validateClient(ClientItem client){
		boolean valid = true;
		if (blackList.contains(client)){
			valid = false;
			client.sendMsg(NetworkMessages.error_Msg("you are blacklisted"));
		}
		if (!clientPool.contains(client)){
			valid = false;		
			client.sendMsg(NetworkMessages.error_Msg("you are not supposed to be connected"));
		}
		
		return valid;
	}
	
	private CluedoGameServer validatedClientGame(int gameID, ClientItem client){
		boolean valid = validateClient(client);
		CluedoGameServer game = getGameByID(gameID);
		if (valid){
			if (game.hasClient(client)){
				return game;
			}
			client.sendMsg(NetworkMessages.error_Msg("you are not participating in this game"));
		}

		return  null;
	}
}
	

