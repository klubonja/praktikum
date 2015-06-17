package cluedoNetworkLayer;

import java.util.ArrayList;

import jdk.nashorn.internal.ir.ReturnNode;
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
	
	public boolean hasPlayerNick(ClientItem client){
		for (ClientItem c: participants)
			if (c == client) 
				return true;
		
		return false;
	}
	
	public boolean findAndRemovePlayer(ClientItem client){
		for (ClientItem c: participants)
			if (c == client) 
				return participants.remove(client);
		
		return false;
	}
	
	public void addWatcher(ClientItem c){
		watchers.add(c);
	}
	
	
}
