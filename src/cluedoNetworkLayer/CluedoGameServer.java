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
	ArrayList<ClientItem> participants;
	ArrayList<ClientItem> watchers;
	WinningStatement winningStatement;
	DrawDFA drawAutomat;
	int currentPlayer = 0;

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
		dealCardsNetwork(getPlayersConnected());
		notifyInit();
		notifyNextRound();
		setGameState(GameStates.started);

		return true;
	}

	//DEALS CARDS BUT DOESNT SEND EM TO THE CLIENTS
	public void dealCardsNetwork(ArrayList<CluedoPlayer> players) {
		Deck deck = new Deck(getNumberConnected());
		deck.dealCluedoCards();
		String[] wh = deck.getWinningHand();
		winningStatement = new WinningStatement(
				Persons.getPersonByColor(wh[0]),
				Weapons.getWeaponByName(wh[1]), Rooms.getRoomByName(wh[2]));

		String[][] playerHands = deck.getPlayerHands();
		for (int i = 0; i < players.size(); i++) {
			players.get(i).setCards(playerHands[i]);
			System.out.println(players.get(i).getCards());
		}
	}

	public ArrayList<String> getWatchersNicks() {
		ArrayList<String> nicks = new ArrayList<String>();
		for (ClientItem c : watchers)
			nicks.add(c.getNick());

		return nicks;
	}

	public void notifyAll(String msg) {
		for (ClientItem c : participants) {
			auxx.sendTCPMsg(c.getSocket(), msg);
		}
	}

	public boolean hasPlayerNick(ClientItem client) {
		for (ClientItem c : participants)
			if (c == client)
				return true;

		return false;
	}

	public boolean findAndRemovePlayer(ClientItem client) {
		for (ClientItem c : participants)
			if (c == client) {
				if (removePlayer(client.getNick())) {
					return participants.remove(client);
				}
			}
		return false;
	}

	public JoinGameStatus joinGameServer(String color, ClientItem client) {
		if (participants.contains(client))
			return JoinGameStatus.already_joined;
		if (getGameState() != GameStates.not_started)
			return JoinGameStatus.not_joinable;

		for (CluedoPlayer p : players) {
			if (p.getCluedoPerson().getColor().equals(color)) {
				if (p.getNick().equals("")) {
					if (participants.add(client)) {
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
				if (participants.remove(client)) {
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
		for (ClientItem client : participants) {
			client.sendMsg(NetworkMessages.game_startedMsg(getGameId(),
					getConnectedPlayersString()));
			CluedoPlayer p = getPlayerByClient(client);
			client.sendMsg(NetworkMessages.player_cardsMsg(getGameId(),
					p.getCards()));
		}
	}

	public void setNextRound() {
		setCurrentPlayerNext();
		drawAutomat = new DrawDFA(participants.get(currentPlayer).getPlayer()
				.getState());
		notifyNextRound();
	}

	public void notifyNextRound() {
		auxx.loginfo(getNicksConnected());
		notifyAll(NetworkMessages.stateupdateMsg(getGameId(), NetworkMessages
				.player_info(participants.get(currentPlayer).getNick(),
						participants.get(currentPlayer).getPlayer()
								.getCluedoPerson().getColor(), participants
								.get(currentPlayer).getPlayer().getState()
								.getName())

		));
	}

	private void setCurrentPlayerNext() {
		currentPlayer = (currentPlayer + 1) % participants.size();
	}

}
