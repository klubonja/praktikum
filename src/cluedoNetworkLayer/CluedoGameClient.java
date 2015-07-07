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

	public void compareCards(String person, String weapon, String room) {
		for (CluedoPlayer p : players) {
			if (p.getNick().equals(myNick)) {
				for (String card : p.getCards()) {
					if (card.equals(person) || card.equals(weapon)
							|| card.equals(room)) {
						this.sendMsgToServer(NetworkMessages.disproveMsg(
								this.gameId, card));
					} else {
						this.sendMsgToServer(NetworkMessages
								.no_disproveMsg(this.gameId));
					}
				}
			}
		}
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

	public void nextTurn(){
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
		Platform.runLater(() -> {
			communicator.addChatMsg(msg);
		});
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
