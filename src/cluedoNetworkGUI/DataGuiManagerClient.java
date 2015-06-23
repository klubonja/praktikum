package cluedoNetworkGUI;

import java.util.ArrayList;

import staticClasses.aux;
import javafx.application.Platform;
import cluedoClient.ServerItem;
import cluedoNetworkLayer.CluedoGameClient;
import enums.ServerStatus;

public class DataGuiManagerClient extends DataGuiManager{
	
	ServerItem server;
	public DataGuiManagerClient(CluedoClientGUI gui,ServerItem s) {
		super(gui);
		server = s;
	}
	
	public ServerItem getServer(){
		return server;
	}
	
	public void addGame(int gameID, String nick,String color){
		CluedoGameClient newgame = 
				new CluedoGameClient(gameID);
		newgame.joinGame(color, nick);
		addGameToGui(gameID, "(created by "+nick+") Game "+gameID, nick);
		
		server.addGame(newgame);
	}
	
	public boolean removeClientFromSystem(String nickID){
	    if (server.removePlayerFromGames(nickID)){
			refreshGamesList();
			return true;
		}
		return false;
	}
	
	public void removeServer(){		
		removeNetworkActorFromGui(server.getGroupName(), server.getIpString());
		emptyGamesList();
	}
	
	public CluedoGameClient getGameByID(int gameID){
		return server.getGameByGameID(gameID);
	}
	
	public boolean joinGame(int gameID,String color,String nick){
		if (server.addPlayerByGameID(gameID, color, nick)){
			if (server.getGameByGameID(gameID).getNumberConnected() >= 3) {
				setReadyGame(gameID, true);
				
			}
			aux.loginfo("connected to game "+gameID+" : "+server.getGameByGameID(gameID).getNumberConnected());
				
			updateGame(gameID, "(updated by "+nick+")",server.getGameByGameID(gameID).getNicksConnected());
			return true;
		}
		return false;
	}
	
	public void setGames(ArrayList<CluedoGameClient> glist){
		server.addGames(glist);
		addGamesGui(glist);
	}
	
	@Override
	public CluedoClientGUI getGui() {
		return (CluedoClientGUI) super.getGui();
	}
	
	
	
	public void addGamesGui(ArrayList<CluedoGameClient> glist){
		  Platform.runLater(() -> {
			  for (CluedoGameClient c: glist)
					gui.addGame(c.getGameId(),"Game" ,c.getNicksConnected());
		 });
	  }
	
	public void setServerLoggedIn(ArrayList<CluedoGameClient> gameslist,String servername,String serverip,String status){
		setGames(gameslist);		
		updateNetworkActorGui(servername,serverip,status);
		server.setStatus(ServerStatus.connected);
	}
	
	public void refreshGamesList(){
		emptyGamesList();
		addGamesGui(server.getGameList());		
	}
	
	
	
	
}
