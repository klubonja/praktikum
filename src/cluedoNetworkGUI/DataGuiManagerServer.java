package cluedoNetworkGUI;


import java.util.ArrayList;
import java.util.Arrays;

import javafx.application.Platform;
import staticClasses.Config;
import staticClasses.NetworkMessages;
import staticClasses.auxx;
import cluedoNetworkLayer.CluedoGameServer;
import cluedoServer.ClientItem;
import cluedoServer.DataManagerServer;
import cluedoServer.GameListServer;
import enums.GameStates;
import enums.JoinGameStatus;
import enums.PlayerStates;

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
			gui.addGame(game.getGameId(),game.getNicksConnected(),game.getWatchersConnected(),game.getGameState(),"","");
			return true;
		}
		return false;		
	}
	
	public boolean startGameByID(int gameID, ClientItem client){
		  CluedoGameServer game = dataManager.getGameByID(gameID);
		  if (game != null){
			  if (game.hasPlayerConnectedByClient(client)){	        		    			
		  			addMsgIn("game "+gameID+ " started");
		  			dataManager.sendMsgToAllClients(
		  					NetworkMessages.game_startedMsg(
		  							gameID, 
		  							 game.getConnectedPlayersString()
		  							 )
		  					);
		  			dataManager.startGameByID(gameID,client.getNick());
		  			setRunningGame(gameID);
		  	   }     		  
			   else {
				   client.sendMsg(NetworkMessages.error_Msg("you have to join game "+gameID+" first to start"));
			   }
		  }
	  	  
		return false;
	}
	
	public void joinGame(int gameID, String color,ClientItem client){
		JoinGameStatus status =  dataManager.joinGame(gameID, color, client);
		   if (status == JoinGameStatus.added){
			   dataManager.sendMsgToAllClients(
     				   NetworkMessages.player_addedMsg(
     						   NetworkMessages.player_info(
     								   client.getNick(), 
     								   color , 
     								   new ArrayList<String>(
     										    Arrays.asList(PlayerStates.do_nothing.getName()
     										    	)
     									)
     								   ),
     						   gameID
     						   )
     				   );
			   auxx.logfine(status.name());
			   updateGame(gameID, dataManager.getNicksConnectedByGameID(gameID),dataManager.getGameByID(gameID).getWatchersConnected());
		   }
		   if (status == JoinGameStatus.already_watching){
			   client.sendMsg(NetworkMessages.error_Msg("damn cheater, you are already watching"));
		   }
		   else if (status == JoinGameStatus.already_joined)
			   client.sendMsg(NetworkMessages.error_Msg("you have already joined this game"));
		   else if (status == JoinGameStatus.color_already_taken)
			   client.sendMsg(NetworkMessages.error_Msg("color is already chosen by someone else"));
		   else if (status == JoinGameStatus.not_joinable)
			   client.sendMsg(NetworkMessages.error_Msg("you can't join this game, idiot"));
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
	
	public boolean removeClientfromGame(ClientItem client,int gameID){
		CluedoGameServer game = dataManager.getGameByID(gameID);
		if (game != null) {
			GameStates stateBefore = game.getGameState();
			if (!game.removeWatcher(client)){
				game.removePlayer(client.getNick());
				 if (game.getNumberConnected() == 0){
						game.sendMsgsToAll(NetworkMessages.game_deletedMsg(gameID));
						removeGameGui(gameID);
						dataManager.removeGame(game);
				}
				else if (game.getGameState() == GameStates.not_started && game.getNumberConnected() < Config.MIN_CLIENTS_FOR_GAMESTART){
					//game.notifyAll(NetworkMessages.game_endedMsg(game.getGameId(), game.getWinningStatement()));
					dataManager.sendMsgToAllClients(NetworkMessages.left_gameMsg(game.getGameId(), client.getNick()));
					setGameWaitingGui(gameID);
				}	
				else if (game.getGameState() == GameStates.ended && GameStates.ended == stateBefore){
					dataManager.sendMsgToAllClients(NetworkMessages.left_gameMsg(game.getGameId(), client.getNick()));
					setGameEndedGui(gameID);
				}
				else if (game.getGameState() == GameStates.ended){
					dataManager.sendMsgToAllClients(NetworkMessages.game_endedMsg(game.getGameId(), game.getWinningStatement()));
					setGameEndedGui(gameID);
				}
				 return true;
			}
			dataManager.sendMsgToAllClients(NetworkMessages.left_gameMsg(game.getGameId(), client.getNick()));
			updateGame(gameID, game.getNicksConnected(), game.getWatchersConnected());
		}
		
		client.sendMsg(NetworkMessages.error_Msg("game "+gameID+" doesn't exist"));
		
		return false;
		
		
	}
	
	public ArrayList<CluedoGameServer> getGamesByPlayer(ClientItem client){
		return dataManager.getGamesByPlayer(client.getNick());		
	}
	
	public int createGame(String color, ClientItem client){		
		int gameId = dataManager.getGameCount();
		CluedoGameServer newgame = new CluedoGameServer(gameId);
		newgame.joinGameServer(color, client);
		dataManager.addGame(newgame);
		addGameToGui(gameId,  client.getNick(),newgame.getWatchersConnected(),newgame.getGameState(),"","");
		
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
				  gui.addGame(c.getGameId(),c.getNicksConnected(),c.getWatchersConnected(),c.getGameState(),"","");
			  }					
		 });
	  }
	
	@Override
	public CluedoServerGUI getGui(){
		return (CluedoServerGUI) super.getGui();
	}
	
	public void addWatcherToGame(int gameID,ClientItem client){
		CluedoGameServer game = getGameByIndex(gameID);
		if (game != null){
			 if (game.addWatcher(client)){
				 if (game.getGameState() == GameStates.started){
					 client.sendMsg(NetworkMessages.gameInfoMsg(game));
				 }
				 dataManager.sendMsgToAllClients(NetworkMessages.watcher_addedMsg(gameID, client.getNick()));	
				 refreshGamesList();
			 }
		}
	}
	
	
	
}
