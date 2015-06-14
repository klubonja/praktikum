package cluedoNetworkLayer;

import java.util.ArrayList;

import cluedoServer.ClientItem;

public class CluedoGameServer extends CluedoGame{
	ArrayList<ClientItem> participants;
	ArrayList<ClientItem> watchers;
	
	public CluedoGameServer(int gameId) {
		super(gameId);	
		participants = new ArrayList<ClientItem>();
		watchers = new ArrayList<ClientItem>();
	}
	
	public ArrayList<String> getWatchersNicks(){
		ArrayList<String> nicks = new ArrayList<String>();
		for (ClientItem c : watchers)
			nicks.add(c.getNick());
		
		return nicks;
	}
	
	public void addWatcher(ClientItem c){
		watchers.add(c);
	}
	
	
}
