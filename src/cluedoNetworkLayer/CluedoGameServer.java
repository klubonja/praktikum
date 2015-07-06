package cluedoNetworkLayer;

import java.util.ArrayList;

import model.Deck;
import staticClasses.NetworkMessages;
import staticClasses.auxx;
import cluedoServer.ClientItem;
import cluedoServer.DrawDFA;
import enums.GameStates;
import enums.JoinGameStatus;
import enums.Persons;
import enums.Rooms;
import enums.Weapons;

public class CluedoGameServer extends CluedoGame {
	private ArrayList<ClientItem> participants;
	ArrayList<ClientItem> watchers;
	WinningStatement winningStatement;
	DrawDFA drawAutomat;
	int currentPlayer = 0;

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
		players = dealCardsNetwork(getPlayersConnected());
		notifyInit();
		notifyNextRound();
		setGameState(GameStates.started);

		return true;
	}

	public ArrayList<CluedoPlayer> dealCardsNetwork(ArrayList<CluedoPlayer> players) {
		Deck deck = new Deck(getNumberConnected());
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
			CluedoPlayer p = getPlayerByClient(client);
			client.sendMsg(NetworkMessages.player_cardsMsg(getGameId(),p.getCards()));
		}
		

	}

	public void setNextRound() {
		setCurrentPlayerNext();
		drawAutomat = new DrawDFA(getParticipants().get(currentPlayer).getPlayer()
				.getState());
		notifyNextRound();
	}

	public void notifyNextRound() {
		auxx.loginfo(getNicksConnected());
		notifyAll(NetworkMessages.stateupdateMsg(getGameId(), NetworkMessages
				.player_info(getParticipants().get(currentPlayer).getNick(),
						getParticipants().get(currentPlayer).getPlayer()
								.getCluedoPerson().getColor(), getParticipants()
								.get(currentPlayer).getPlayer().getState()
								.getName())

		));
	}

	public void setCurrentPlayerNext() {
		currentPlayer = (currentPlayer + 1) % getParticipants().size();
	}
	

	public int getCurrentPlayer() {
		return currentPlayer;
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

	@Override
	boolean start(ArrayList<String> order) {
		return false;
	}


}
