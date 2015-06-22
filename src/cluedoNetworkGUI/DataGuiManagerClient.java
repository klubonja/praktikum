package cluedoNetworkGUI;

import java.util.ArrayList;

import javafx.application.Platform;
import cluedoClient.ServerItem;
import cluedoNetworkLayer.CluedoGameClient;

public class DataGuiManagerClient extends DataGuiManager{
	
	ServerItem server;
	public DataGuiManagerClient(CluedoClientGUI gui,ServerItem s) {
		super(gui);
		server = s;
	}
	
	public ServerItem getServer(){
		return server;
	}
	
	@Override
	public void addGame(int gameID, String nick,String color){
		CluedoGameClient newgame = 
				new CluedoGameClient(gameID);
		newgame.joinGame(color, nick);
		addGameToGui(gameID, "(created by "+nick+") Game "+gameID, nick);
		
		server.addGame(newgame);
	}
	
	public boolean removeClientFromSystem(String nickID){
	//	if (server.removePlayerFromGames(nickID)){
			server.removePlayerFromGames(nickID);
			refreshGamesList();
			return true;
		//}
//		System.out.println("no refreshing");
//		return false;
	}
	
	public void removeServer(){		
		removeNetworkActorFromGui(server.getGroupName(), server.getIpString());
		emptyGamesList();
	}
	
	public boolean joinGame(int gameID,String color,String nick){
		if (server.addPlayerByGameID(gameID, color, nick)){
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
	}
	
	public void refreshGamesList(){
		emptyGamesList();
		addGamesGui(server.getGameList());		
	}
	
	
	
	
}
