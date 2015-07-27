package cluedoServer;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import staticClasses.NetworkMessages;
import cluedoNetworkGUI.DataManager;
import cluedoNetworkLayer.CluedoGameServer;
import cluedoNetworkLayer.CluedoPosition;
import cluedoNetworkLayer.CluedoStatement;
import enums.GameStates;
import enums.JoinGameStatus;

public class DataManagerServer extends DataManager {
	
	ClientPool clientPool;
	ArrayList<ClientItem> blackList;
	GameListServer gamesList;
	private String suspector;
	private String disprover;
	
	public DataManagerServer(String groupname) {
		super(groupname);
		clientPool = new ClientPool();
		blackList = new ArrayList<ClientItem>();
		gamesList = new GameListServer();
	}
	
	/////////////////////////////////GETTER//////////////////////////////////////////////////////
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
	
	public String getNicksConnectedByIndex(int index){
		return getGameByIndex(index).getNicksConnected();
	}
	
	public String getNicksConnectedByGameID(int gameID){
		return gamesList.getGameByID(gameID).getNicksConnected();
	}
	
	@Override
	public int getGameCount(){
		return gamesList.size();
	}
	////////////////////////////////SETTER/////////////////////////////////////////////////////
	
	///////////////////////////////////HASER//////////////////////////////////////////////////////
	public boolean hasClient(ClientItem client){
		for (ClientItem c: clientPool)
			if (c.getNick().equals(client.getNick()))
					return true;
		return false;
 	}
	//////////////////////////////////RUNNING GAME///////////////////////////////////////////////////
	public void suspectRequest(int gameID,CluedoStatement statement,ClientItem client){
		CluedoGameServer game = validatedClientGame(gameID, client);
		if (game != null){
//			if (statement.equals(game.getWinningStatement())){
//				sendMsgToAllClients(NetworkMessages.game_endedMsg(gameID, statement));
//			}
//			else {
				game.suspect(statement, client);
//			}			
		}
	}
	
	public void rollDiceRequest(int gameID,ClientItem client){
		CluedoGameServer game = validatedClientGame(gameID, client);
		if (game != null){
			game.rollDice(client);	
		}		
	}
	
	public void disproveRequest(int gameID, String card, ClientItem client) {
		CluedoGameServer game = validatedClientGame(gameID, client);
		if (game != null){
			game.disproveRequest(card,client);
		}
		
	}
	
	public void endTurnRequest(int gameID,ClientItem client){
		CluedoGameServer game = validatedClientGame(gameID, client);
		if (game != null){
			game.endTurnRequest(client);
		}
	}
	
	public void moveRequest(int gameID, ClientItem client,CluedoPosition newpos){
		CluedoGameServer game = validatedClientGame(gameID, client);
		if (game != null){
			if (game.movePlayerRequest(client ,newpos)) {
		   		//ach 
			}
		}
	}
	
	public void secretPassageRequest(int gameID, ClientItem client) {
		CluedoGameServer game = validatedClientGame(gameID, client);
		if (game != null){
			if (game.useSecretPassageRequest(client)) {
		   		//ach 
			}
		}
	}
	
	public void accuseRequest(int gameID, CluedoStatement accusation,
			ClientItem client) {
		CluedoGameServer game = validatedClientGame(gameID, client);
		if (game != null){
			if (game.accuseRequest(accusation,client)) {
				if (accusation.equals(game.getWinningStatement())){
					sendMsgToAllClients(NetworkMessages.game_endedMsg(gameID, client.nick, accusation));
				}
			}
		}
		
	}
	
	public String getDisprover() {
		return disprover;
	}



	public void setDisprover(String disprover) {
		this.disprover = disprover;
	}
	
	///////////////////////////////SENDMSGS/////////////////////////////////////////////////////////
	@Override
	public void sendMsgToAllClients(String msg){
		clientPool.notifyAll(msg);
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////
	
	/////////////////////////////////VALIDATION///////////////////////////////////////////////////
	
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

	
	////////////////////////////////////////////////////////////////////////////////
	public boolean startGameByID(int gameID,String nick){
		return gamesList.getGameByID(gameID).start();
	}
		
	
	public JoinGameStatus joinGame(int gameID, String color,ClientItem client){
		return gamesList.joinGameById(gameID, color, client);
	}
	
	public boolean addNetworkActor(ClientItem client){
		return clientPool.add(client);
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
	
	public boolean blacklist(ClientItem client){
		if (clientPool.contains(client))
			clientPool.remove(client);
		return blackList.add(client);
	}
	
	public boolean addClient(ClientItem client){
		if (!hasClient(client))
			return clientPool.add(client);
		return false;
	}
	
	public boolean removeClientfromSystem(ClientItem client){
		Iterator<CluedoGameServer> iter = gamesList.iterator();
		while(iter.hasNext()){
			CluedoGameServer cgs = iter.next();
			cgs.findAndRemovePlayer(client);
			cgs.findAndRemoveWatcher(client);
			if (cgs.getNumberConnected() == 0){
				cgs.sendMsgsToAll(NetworkMessages.game_endedMsg(cgs.getGameId(), cgs.getWinningStatement()));
				iter.remove();
				cgs.sendMsgsToAll(NetworkMessages.game_deletedMsg(cgs.getGameId()));								
			}
			else if (cgs.getGameState() == GameStates.ended){
				cgs.sendMsgsToAll(NetworkMessages.game_endedMsg(cgs.getGameId(), cgs.getWinningStatement()));
			}
			else if (cgs.getGameState() == GameStates.not_started){
				cgs.sendMsgsToAll(NetworkMessages.user_leftMsg(client.getNick()));
			}		
		}	
	
		return clientPool.remove(client);
	}
	
	@Override
	public boolean joinGame(int gameID, String color, String nick) {
		// TODO Auto-generated method stub
		return false;
	}

	

	
}
	

