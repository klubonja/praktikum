package cluedoNetworkGUI;


import com.sun.org.apache.bcel.internal.generic.ReturnaddressType;

import cluedoNetworkLayer.CluedoGameServer;
import cluedoServer.ClientItem;
import cluedoServer.DataManagerServer;

public class DataGuiManagerServer extends DataGuiManager {
	
	
	DataManagerServer dataManager;
	
	
	public DataGuiManagerServer(CluedoServerGUI gui,DataManagerServer datam) {
		super(gui);
		dataManager = datam;		
	}	
	
	public void loginEvent(String ip,String nick,String msg){
		addMsgIn(nick+" says :"+msg);
		addNetworkActorToGui(nick, ip);
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
	
	public boolean joinGame(int gameID, String color,String nick){
		 if (dataManager.joinGame(gameID, color, nick)){
			updateGame(gameID, "(updated) Game", dataManager.getNicksConnectedByGameID(gameID));
			return true;
		 };
		 
		 return false;
	}
	
	public void setGuiStatus(String status) {
		setStatus(status);
	}
	
	public void setGuiWindowName(String newname) {
		setWindowName(newname);
	}
	
	public boolean addNetworkActor(ClientItem client){
		if (dataManager.addClient(client)){
			addNetworkActorToGui(client.getNick(), client.getIpString());
			addMsgIn(client.getNick()+" joined");
			return true;
		}
		return false;
	}
	
	public boolean removeClient(ClientItem client){
		if (dataManager.removeClientfromSystem(client)){
			removeNetworkActorFromGui(client.getNick(),client.getIpString());	
			return true;
		}
		return false;				
	}
	
	public int createGame(String color, String nick){		
		int gameId = dataManager.getGameCount();
		CluedoGameServer newgame = new CluedoGameServer(gameId);
		newgame.joinGame(color, nick);
		dataManager.addGame(newgame);
		addGame(gameId, "(created by )"+ nick, nick);
		
		return gameId;
	}
	
	public void closeOn(String msg){
		addMsgIn(msg);
	}
	
	
}
