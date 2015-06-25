package cluedoNetworkGUI;


import java.util.ArrayList;

import javafx.application.Platform;
import staticClasses.Config;
import staticClasses.NetworkMessages;
import cluedoClient.ServerItem;
import cluedoNetworkLayer.CluedoGameClient;
import cluedoNetworkLayer.CluedoGameServer;
import cluedoServer.ClientItem;
import cluedoServer.DataManagerServer;
import cluedoServer.GameListServer;
import enums.GameStates;
import enums.JoinGameStatus;

public class DataGuiManagerServer extends DataGuiManager {	
	
	DataManagerServer dataManager;	
	
	public DataGuiManagerServer(CluedoServerGUI gui,DataManagerServer datam) {
		super(gui);
		dataManager = datam;		
	}
	
	public void loginEvent(String ip,String nick,String msg,String status){
		addMsgIn(nick+" says :"+msg);
		addNetworkActorToGui(nick, ip,status);
	}
	
	public CluedoGameServer getGameByIndex(int index){
		return dataManager.getGameByIndex(index);
	}
	
	public boolean addGame(CluedoGameServer game){
		if (dataManager.addGame(game)){
			gui.addGame(game.getGameId(),"Game",game.getNicksConnected(),game.getGameState());
			return true;
		};
		
		return false;		
	}
	
	public boolean startGameByID(int gameID, String nick){
		if (dataManager.startGameByID(gameID,nick)){
			setRunningGame(gameID);
			return true;
		}
		return false;
	}
	
	
	public JoinGameStatus joinGame(int gameID, String color,ClientItem client){
		JoinGameStatus status =  dataManager.joinGame(gameID, color, client);
		if (status == JoinGameStatus.added)
			updateGame(gameID, "Game", dataManager.getNicksConnectedByGameID(gameID));
		 
		return status;
	}
	
	public void setGuiStatus(String status) {
		setStatus(status);
	}
	
	public void setGuiWindowName(String newname) {
		setWindowName(newname);
	}
	
	public boolean addNetworkActor(ClientItem client,String status){
		if (dataManager.addClient(client)){
			addNetworkActorToGui(client.getNick(), client.getIpString(),status);
			addMsgIn(client.getNick()+" joined");
			return true;
		}
		return false;
	}
	
	public boolean removeClient(ClientItem client){
		if (dataManager.removeClientfromSystem(client)){
			removeNetworkActorFromGui(client.getNick(),client.getIpString());
			refreshGamesList();
			return true;
		}
		return false;				
	}
	
	public boolean removePlayerfromGame(ClientItem client,int gameID){
			CluedoGameServer game = dataManager.getGameByID(gameID);
			 if (game.getNumberConnected() == 0){
					game.notifyAll(NetworkMessages.game_deletedMsg(gameID));
					removeGameGui(gameID);
					dataManager.removeGame(game);
			}
			else if (game.getGameState() == GameStates.not_started && game.getNumberConnected() < Config.MIN_CLIENTS_FOR_GAMESTART){
				game.notifyAll(NetworkMessages.game_endedMsg(game.getGameId(), game.getWinningStatement()));
				setGameWaitingGui(gameID);
			}
			else if (game.getGameState() == GameStates.ended){
				game.notifyAll(NetworkMessages.game_endedMsg(game.getGameId(), game.getWinningStatement()));
				setGameEndedGui(gameID);
			}
			
			
			
		
		
		return true;
	}
	
	public ArrayList<CluedoGameServer> getGamesByPlayer(ClientItem client){
		return dataManager.getGamesByPlayer(client.getNick());		
	}
	
	public int createGame(String color, ClientItem client){		
		int gameId = dataManager.getGameCount();
		CluedoGameServer newgame = new CluedoGameServer(gameId);
		newgame.joinGameServer(color, client);
		dataManager.addGame(newgame);
		addGameToGui(gameId, "", client.getNick(),newgame.getGameState());
		
		return gameId;
	}
	
	public void closeOn(ClientItem client,String msg){
		addMessageOut(msg);
		removeClient(client);
	}
	
	public void refreshGamesList(){
		emptyGamesList();
		addGamesGui( dataManager.getGameList());
	}
	
	public void addGamesGui(GameListServer glist){
		  Platform.runLater(() -> {
			  for (CluedoGameServer c: glist){
				  gui.addGame(c.getGameId(),"Game" ,c.getNicksConnected(),c.getGameState());
			  }					
		 });
	  }
	
}
