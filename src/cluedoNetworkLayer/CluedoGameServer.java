package cluedoNetworkLayer;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Stack;

import javafx.scene.paint.Color;
import model.Deck;
import staticClasses.NetworkMessages;
import staticClasses.auxx;
import cluedoServer.ClientItem;
import enums.GameStates;
import enums.JoinGameStatus;
import enums.Persons;
import enums.PlayerStates;
import enums.Rooms;
import enums.Weapons;

public class CluedoGameServer extends CluedoGame {
	private ArrayList<ClientItem> participants;
	ArrayList<ClientItem> watchers;
	WinningStatement winningStatement;
	int currentPlayerIndex = 0;

	public CluedoGameServer(int gameId) {
		super(gameId);
		setParticipants(new ArrayList<ClientItem>());
		watchers = new ArrayList<ClientItem>();
	}

	public WinningStatement getWinningStatement() {
		return winningStatement;
	}

	@Override
	public boolean start() {
		players = dealCardsNetwork(getPlayersConnected()); //this renders players as only connected
		orderPlayersList();
		notifyInit();
		notifyNextRound();
		setGameState(GameStates.started);

		return true;
	}

	public Stack<CluedoPlayer> dealCardsNetwork(Stack<CluedoPlayer> players) {
		Deck deck = new Deck(players.size());
		deck.dealCluedoCards();
		String[] wh = deck.getWinningHand();
		winningStatement = new WinningStatement(
				Persons.getPersonByColor(wh[0]),
				Weapons.getWeaponByName(wh[1]), Rooms.getRoomByName(wh[2]));

		String[][] playerHands = deck.getPlayerHands();
		for (int i = 0; i < players.size(); i++) {
			players.get(i).setCards(playerHands[i]);
		}
		return players;
	}

	public ArrayList<String> getWatchersNicks() {
		ArrayList<String> nicks = new ArrayList<String>();
		for (ClientItem c : watchers)
			nicks.add(c.getNick());

		return nicks;
	}

	public void notifyAll(String msg) {
		for (ClientItem c : getParticipants()) {
			auxx.sendTCPMsg(c.getSocket(), msg);
		}
	}

	public boolean hasPlayerNick(ClientItem client) {
		for (ClientItem c : getParticipants())
			if (c == client)
				return true;

		return false;
	}

	public boolean findAndRemovePlayer(ClientItem client) {
		for (ClientItem c : getParticipants())
			if (c == client) {
				if (removePlayer(client.getNick())) {
					return getParticipants().remove(client);
				}
			}
		return false;
	}

	public JoinGameStatus joinGameServer(String color, ClientItem client) {
		if (getParticipants().contains(client))
			return JoinGameStatus.already_joined;
		if (getGameState() != GameStates.not_started)
			return JoinGameStatus.not_joinable;

		for (CluedoPlayer p : players) {
			if (p.getCluedoPerson().getColor().equals(color)) {
				if (p.getNick().equals("")) {
					if (getParticipants().add(client)) {
						p.setNick(client.getNick());
						client.setPlayer(p);
						return JoinGameStatus.added;
					}
					return JoinGameStatus.error;
				}
				return JoinGameStatus.nick_already_taken;
			}
		}

		return JoinGameStatus.game_not_found;
	}

	public boolean leaveGameServer(ClientItem client) {
		for (CluedoPlayer p : players) {
			if (p.getNick().equals(client.getNick())) {
				if (getParticipants().remove(client)) {
					p.setNick("");
					return true;
				} else {
					return false;
				}
			}
		}

		return false;
	}

	public CluedoPlayer getPlayerByClient(ClientItem client) {
		for (CluedoPlayer p : players) {
			if (client.getNick().equals(p.getNick()))
				return p;
		}

		return null;
	}

	public void addWatcher(ClientItem c) {
		watchers.add(c);
	}

	public void notifyInit() {
		for (ClientItem client: getParticipants()){
			//client.sendMsg(NetworkMessages.game_startedMsg(getGameId(), getConnectedPlayersString()));
			CluedoPlayer p = getPlayerByClient(client);
			client.sendMsg(
					NetworkMessages.player_cardsMsg(
							getGameId(),
							p.getCards()
						)
				);
		}
	}

	public void setNextRound() {
		setCurrentPlayerNext();
		updatePlayerStates();		
		notifyNextRound();
	}
	
	public void updatePlayerStates(){		
		for (int i = 0;i < players.size(); i++){
			if (i == currentPlayerIndex)	{
				players.get(i).setCurrentState(PlayerStates.do_nothing); // hier werden possible moves von do nothing aus gesetzt
			}
			else{
				players.get(i).setDoNothing(); // hier werden possible moves geleert und do nothing hinzugefügt
			}			
		}
			
	}

	public void notifyNextRound() {
		auxx.loginfo(getNicksConnected());
		notifyAll(NetworkMessages.stateupdateMsg(getGameId(), 
				NetworkMessages.player_info(
						players.get(currentPlayerIndex).getNick(),
						players.get(currentPlayerIndex).getCluedoPerson().getColor(), 
						players.get(currentPlayerIndex).getStatesStringList()
				)
		));
	}

	public void setCurrentPlayerNext() {
		currentPlayerIndex = (currentPlayerIndex + 1) % getParticipants().size();
	}
	
	public int getCurrentPlayerIndex() {
		return currentPlayerIndex;
	}

	public int [] rollTheDice() {
		int [] wuerfel = new int [2];
		wuerfel[0] = auxx.getRandInt(1, 6);
		wuerfel[1] = auxx.getRandInt(1, 6);
		return wuerfel;
	}

	public ArrayList<ClientItem> getParticipants() {
		return participants;
	}

	public void setParticipants(ArrayList<ClientItem> participants) {
		this.participants = participants;
	}
	
	public boolean checkForColor(Color color){
		Stack<CluedoPlayer> pl = getPlayersConnected();
		for (CluedoPlayer p : pl)
			if (p.getCluedoPerson().getFarbe() == color) 
				return true;
		return false;
	}
	
	public void orderPlayersList(){
		if (!checkForColor(Color.RED))
			setStart(players);	
	}
	
	public static void setStart(Stack<CluedoPlayer> players){
		CluedoPlayer first = players.get(auxx.getRandInt(0, players.size()-1));
		Stack<CluedoPlayer>  tmplist = (Stack<CluedoPlayer>)players.clone(); 
		Iterator<CluedoPlayer> it = tmplist.iterator();
		while (it.hasNext()){
			try {
				CluedoPlayer pl = it.next();
				if (pl != first){
					players.remove(pl);
					players.add(pl);
				}
				
			} catch (ConcurrentModificationException e) {
				
					auxx.logsevere("modification playerslist failed", e);
			}
			
		
			
		}
		tmplist = null;
	}
	
	static void setStart(Stack<CluedoPlayer> p,Color c){
		Iterator<CluedoPlayer> it = p.iterator();
		while (it.hasNext() && it.next().getCluedoPerson().getFarbe() != c){
			CluedoPlayer pl = it.next();
			p.remove(pl);
			p.add(pl);
		}
	}

	@Override
	boolean start(ArrayList<String> order) {
		return false;
	}


}
