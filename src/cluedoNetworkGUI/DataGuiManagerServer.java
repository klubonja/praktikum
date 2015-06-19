package cluedoNetworkGUI;


import java.util.ArrayList;

import javafx.application.Platform;
import cluedoNetworkLayer.CluedoGameServer;
import cluedoServer.ClientItem;
import cluedoServer.DataManagerServer;
import cluedoServer.GameListServer;
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
			gui.addGame(game.getGameId(),"Game",game.getNicksConnected());
			return true;
		};
		
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
			//removeNetworkActorFromGui(client.getNick(),client.getIpString());	
			refreshGamesList();
			return true;
		}
		return false;				
	}
	
	
	
	public ArrayList<CluedoGameServer> getGamesByPlayer(ClientItem client){
		return dataManager.getGamesByPlayer(client.getNick());		
	}
	
	public int createGame(String color, String nick){		
		int gameId = dataManager.getGameCount();
		CluedoGameServer newgame = new CluedoGameServer(gameId);
		newgame.joinGame(color, nick);
		dataManager.addGame(newgame);
		addGame(gameId, "(created by )"+ nick, nick);
		
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
			  for (CluedoGameServer c: glist)
					gui.addGame(c.getGameId(),"Game" ,c.getNicksConnected());
		 });
	  }
	
}
