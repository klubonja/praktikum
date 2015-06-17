package cluedoNetworkGUI;

import java.net.Socket;
import java.util.ArrayList;

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
	
	public void addGame(int gameID, String nick,String color){
		CluedoGameClient newgame = 
				new CluedoGameClient(gameID);
		newgame.joinGame(color, nick);
		addGameToGui(gameID, "(created by "+nick+") Game "+gameID, nick);
		
		server.addGame(newgame);
	}
	
	public void kill(){
		removeNetworkActorFromGui(server.getGroupName(), server.getIpString());
	}
	
	public boolean joinGame(int gameID,String color,String nick){
		if (server.addPlayerByGameID(gameID, color, nick)){
			updateGame(gameID, "(updated by "+nick+")",server.getGameByGameID(gameID).getNicksConnected());
			return true;
		}
		return false;
	}
	
	public void addGameToGui(int gameID,String specialInfo, String info){
		addGame(gameID, specialInfo, info);
	}
	
	public void setGames(ArrayList<CluedoGameClient> glist){
		server.addGames(glist);
		addGamesGui(glist);
	}
	
	@Override
	public CluedoClientGUI getGui() {
		return (CluedoClientGUI) super.getGui();
	}
	
}
