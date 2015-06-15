package cluedoNetworkGUI;

import java.util.ArrayList;

import cluedoClient.ServerPool;
import cluedoServer.ClientItem;
import cluedoServer.ClientPool;
import cluedoServer.DataManagerServer;
import cluedoServer.GameListServer;

public class CluedoServerDataGuiInterface extends CluedoNetworkDataGuiInterface {
	
	
	DataManagerServer dataManager;
	
	ServerPool serverList;//CLient
	
	public CluedoServerDataGuiInterface(CluedoServerGUI gui,DataManagerServer datam) {
		super(gui);
		dataManager = datam;		
	}	
	
	public void loginEvent(String ip,String nick,String msg){
		addMessageIn(nick+" says :"+msg);
		addIp(nick+" on "+ip);
	}
	
	
	
	
	
}
