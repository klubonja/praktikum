package cluedoNetworkLayer;

import java.util.ArrayList;

import staticClasses.aux;
import cluedoServer.ClientItem;
import enums.GameStates;
import enums.JoinGameStatus;
import enums.Persons;
import enums.Rooms;
import enums.Weapons;

public class CluedoGameServer extends CluedoGame{
	ArrayList<ClientItem> participants;
	ArrayList<ClientItem> watchers;
	WinningStatement winningStatement;
	
	public CluedoGameServer(int gameId) {
		super(gameId);	
		participants = new ArrayList<ClientItem>();
		watchers = new ArrayList<ClientItem>();
		winningStatement = new WinningStatement(Persons.white, Rooms.kitchen, Weapons.spanner);
	}
	
	public WinningStatement getWinningStatement() {
		return winningStatement;
	}
	
	
	
	public ArrayList<String> getWatchersNicks(){
		ArrayList<String> nicks = new ArrayList<String>();
		for (ClientItem c : watchers)
			nicks.add(c.getNick());
		
		return nicks;
	}
	
	public void notifyAll(String msg){
		for (ClientItem c: participants){
			aux.sendTCPMsg(c.getSocket(), msg);
		}
	}
	
	public boolean hasPlayerNick(ClientItem client){
		for (ClientItem c: participants)
			if (c == client) 
				return true;
		
		return false;
	}
	
	public boolean findAndRemovePlayer(ClientItem client){
		for (ClientItem c: participants)
			if (c == client) {
				if (removePlayer(client.getNick())){
					boolean removed = participants.remove(client);	
					if (getGameState() == GameStates.started) setGameState(GameStates.ended);
					if (participants.size() == 0) setGameState(GameStates.to_be_deleted);
					
					return removed;
				}				
			}		
		return false;
	}
	
	public JoinGameStatus joinGameServer(String color,ClientItem client){
		if (participants.contains(client)) return JoinGameStatus.already_joined;
		
		for (CluedoPlayer p: players){
			if (p.getCluedoPerson().getColor().equals(color)){
				if (p.getNick().equals("")){
					if (participants.add(client)){
						p.setNick(client.getNick());
						return JoinGameStatus.added;
					}
					return JoinGameStatus.error;
				}
				return JoinGameStatus.nick_already_taken;
			}
		}	
		
		return JoinGameStatus.error;
	}
	
	public boolean leaveGameServer(ClientItem client){
		for (CluedoPlayer p: players){
			if (p.getNick().equals(client.getNick())){
				if (participants.remove(client)){
					p.setNick("");
					return true;
				}
				else {
					return false;
				}				
			}
		}	
				
		return false;
	}
	
	public void addWatcher(ClientItem c){
		watchers.add(c);
	}
	
	
}
