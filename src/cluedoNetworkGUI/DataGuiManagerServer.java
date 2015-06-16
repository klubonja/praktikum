package cluedoNetworkGUI;

import java.util.ArrayList;

import cluedoClient.ServerPool;
import cluedoNetworkLayer.CluedoGameServer;
import cluedoServer.ClientItem;
import cluedoServer.ClientPool;
import cluedoServer.DataManagerServer;
import cluedoServer.GameListServer;

public class DataGuiManagerServer extends DataGuiManager {
	
	
	DataManagerServer dataManager;
	
	
	public DataGuiManagerServer(CluedoServerGUI gui,DataManagerServer datam) {
		super(gui);
		dataManager = datam;		
	}	
	
	public void loginEvent(String ip,String nick,String msg){
		addMessageIn(nick+" says :"+msg);
		addIp(nick+" on "+ip);
	}
	
	public CluedoGameServer getGameByIndex(int index){
		return dataManager.getGameByIndex(index);
	}
	
	public boolean addGame(CluedoGameServer game){
		if (dataManager.addGame(game)){
			gui.addGame(game.getGameId(),"(test) Game",game.getNicksConnected());
			return true;
		};
		
		return false;		
	}
	
	public void setGuiStatus(String status) {
		setStatus(status);
	}
	
	public void setWindowName(String newname) {
		setWindowName(newname);
	}
	
	
}
