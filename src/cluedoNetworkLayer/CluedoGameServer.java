package cluedoNetworkLayer;

import java.util.ArrayList;

import model.Deck;
import staticClasses.NetworkMessages;
import staticClasses.auxx;
import cluedoServer.ClientItem;
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
	}
	
	public WinningStatement getWinningStatement() {
		return winningStatement;
	}
	
	@Override
	public boolean start() {
//		for (ClientItem client: participants)
//			//client.sendMsg(NetworkMessages.player_cardsMsg(gameId, cards));
		
		dealCardsNetwork();
		return super.start();
	}
	
	public void dealCardsNetwork(){
		Deck deck = new Deck(getNumberConnected());
		deck.dealCluedoCards();
		String [] wh = deck.getWinningHand();
		winningStatement = new WinningStatement(
				Persons.getPersonByColor(wh[0]), 
				Weapons.getWeaponByName(wh[1]),
				Rooms.getRoomByName(wh[2])
		);
		
		ArrayList<CluedoPlayer> playersCon  = getPlayersConnected();
		String[][] playerHands = deck.getPlayerHands();
		for (int i= 0;i < playersCon.size();i++)
			playersCon.get(i).setCards(playerHands[i]);
	}
	
	public ArrayList<String> getWatchersNicks(){
		ArrayList<String> nicks = new ArrayList<String>();
		for (ClientItem c : watchers)
			nicks.add(c.getNick());
		
		return nicks;
	}
	
	public void notifyAll(String msg){
		for (ClientItem c: participants){
			auxx.sendTCPMsg(c.getSocket(), msg);
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
					return participants.remove(client);	
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
		
		return JoinGameStatus.game_not_found;
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
	
	public CluedoPlayer getPlayerByClient(ClientItem client){
		for (CluedoPlayer p: players){
			if (client.getNick().equals(p.getNick()))
				return p; 
		}
		
		return null;
	}
	
	public void addWatcher(ClientItem c){
		watchers.add(c);
	}
	
	public void notifyInit(){
		for (ClientItem client: participants){
			CluedoPlayer p = getPlayerByClient(client);
			client.sendMsg(NetworkMessages.player_cardsMsg(getGameId(),p.getCards()));
		}
	}
	
	
}
