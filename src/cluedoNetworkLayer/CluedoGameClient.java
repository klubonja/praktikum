package cluedoNetworkLayer;

import java.util.ArrayList;

import javafx.application.Platform;
import staticClasses.NetworkMessages;
import staticClasses.auxx;
import view.Communicator;
import cluedoClient.ServerItem;
import enums.GameStates;

public class CluedoGameClient extends CluedoGame {

	ServerItem server;
	String myNick;

	public CluedoGameClient(int gameId, ServerItem server) {
		super(gameId);
		this.server = server;
		myNick = server.getMyNick();
	}

	public void somebodyIsAccusing(String nick, String person, String weapon,
			String room) {
		String str = "The player " + nick + " is trying to solve the mystery!"
				+ "\n" + "Accused: " + person + " " + weapon + " " + room;
		changeLabel(str);
	}

	public void somebodyFailedToAccuse(String person, String weapon, String room) {
		String str = "Accusation failed for: " + person + " " + weapon + " "
				+ room;
		changeLabel(str);
	}

	public void changeLabel(String str) {
		Platform.runLater(new Runnable() {
			public void run() {
				communicator.changeLabel(str);
			}
		});
	}

	public void compareCards(String person, String weapon, String room) {
		Platform.runLater(new Runnable() {
			public void run() {
				communicator.compareCards(person, weapon, room);
			}
		});
	}

	public ServerItem getServer() {
		return server;
	}

	public Communicator getCommunicator() {
		return communicator;
	}

	public String getMyNick() {
		return myNick;
	}

	@Override
	public boolean start(){
		return false;
	}
	
	@Override
	public boolean start(ArrayList <String> order) {
		auxx.loginfo("game " + getGameId() + " started");

		Platform.runLater(() -> {
			communicator = new Communicator(this, order);
			communicator.startGame();
			communicator.setTitle(myNick + " playing on server "
					+ server.getGroupName() + " Game : " + gameId);
		});
		setGameState(GameStates.started);

		return true;
	}

	public void rollDice(int[] wuerfel) {
		Platform.runLater(() -> {
			communicator.rollDice(wuerfel);
		});
	}

	public void nextTurn() {
		Platform.runLater(() -> {
			communicator.itsYourTurn();
		});

	}

	public void move(CluedoPosition position, String person) {
		communicator.move(position, person);
	}

	public void setMyNick(String myNick) {
		this.myNick = myNick;
	}

	public void sendMsgToServer(String msg) {
		auxx.sendTCPMsg(server.getSocket(), msg);
	}

	public void addChatMsg(String msg) {
		communicator.addChatMsg(msg);
	}

	public void kill() {
		auxx.sendTCPMsg(server.getSocket(),
				NetworkMessages.leave_gameMsg(gameId));
		killCommunicator();
	}

	public void killCommunicator() {
		Platform.runLater(() -> {
			communicator.kill();
		});
	}

	public void currentPlayerToNothing() {
		Platform.runLater(() -> {
			communicator.updateStatesToNothing();
		});

	}
	
	public void changePlayer(){
		Platform.runLater(() -> {
			communicator.changePlayer();
		});
	}
	
	public void currentPlayerToRolls() {
		Platform.runLater(() -> {
			communicator.updateStatesToRolls();
		});

	}
}
