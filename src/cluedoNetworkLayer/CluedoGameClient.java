package cluedoNetworkLayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Stack;

import javafx.application.Platform;
import kommunikation.Communicator;
import staticClasses.NetworkMessages;
import staticClasses.auxx;
import cluedoClient.ServerItem;
import enums.GameStates;

public class CluedoGameClient extends CluedoGame {

	ServerItem server;
	String myNick;
	
	ArrayList<String> watchers;

	public CluedoGameClient(int gameId, ServerItem server) {
		super(gameId);
		this.server = server;
		watchers = new ArrayList<String>();
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

	public boolean start() { //aussumes playerlsit beeing ordered
		auxx.loginfo("gameGUI of game " + getGameId() + " started");
		filterPlayers();
		startGameGui();		
		setGameState(GameStates.started);

		return true;
	}
	
	public boolean start(ArrayList <String> order) {
		searchAndDestroyOrder(order);
		start();

		return true;
	}
	
	private void startGameGui(){
		Platform.runLater(() -> {
			communicator = new Communicator(this);
			communicator.startGame();
			communicator.setTitle(myNick + " playing on server "
					+ server.getGroupName() + " Game : " + gameId);
		});
	}

	public void rollDice(int[] wuerfel) {
		Platform.runLater(() -> {
			communicator.rollDice(wuerfel);
		});
	}

	public void itsYourTurn(){
		Platform.runLater(() -> {
			communicator.itsYourTurn();
		});

	}
	
	public void itsSomeonesTurn(){
		Platform.runLater(() -> {
			communicator.itsSomeonesTurn();
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
		try {
			auxx.sendTCPMsg(server.getSocket(),
					NetworkMessages.leave_gameMsg(gameId));
			killCommunicator();
		}
		catch (Exception e){	
		}
	}

	public void killCommunicator() {
		Platform.runLater(() -> {
			if (communicator != null) communicator.kill();
		});
	}

	public void currentPlayerToNothing() {
		Platform.runLater(() -> {
			communicator.updateStatesToNothing();
		});

	}
	
	public void currentPlayerToRolls() {
		Platform.runLater(() -> {
			communicator.updateStatesToRolls();
		});
	}
	
	public void currentPlayerToAccuse(){
		Platform.runLater(() -> {
			communicator.updateStatesToAccuse();}
		);}
	public void currentPlayerToSuspect(){
		Platform.runLater(() -> {
			communicator.updateStatesToSuspect();}
		);}
	public void currentPlayerToDisprove(){
		Platform.runLater(() -> {
			communicator.updateStatesToDisprove();}
		);}
	
	public void searchAndDestroyOrder(ArrayList<String> order){
		Stack<CluedoPlayer>  tmplist = (Stack<CluedoPlayer>)players.clone();
		Iterator <CluedoPlayer> profDrOhlbach = players.iterator();
		while (profDrOhlbach.hasNext()){
			CluedoPlayer laith = profDrOhlbach.next();
			if (laith.getNick().equals("")){
				profDrOhlbach.remove();
			}
		}
		
		for (int i = 0;i < order.size(); i++){
			Collections.swap(players,i,getIndexByNick(order.get(i)));
		}
		
		for (int hans = 0; hans < order.size(); hans++){
			System.out.print("order an der stelle : " +hans +order.get(hans));
			System.out.println(" || players an der stelle : " +hans +players.get(hans).getNick());
		}
		
	}
	
	public void filterPlayers(){
		players = getPlayersConnected();
	}
	
	public String getWatchersConnected(){
		StringBuffer nb = new StringBuffer();
		for (String p : watchers)			
				nb.append(p+", ");		
		
		if (nb.length() > 2) nb.delete(nb.length()-2, nb.length()-1);
		return nb.toString();
	}


	public void disprove(String card) {
		communicator.disprove(card);
	}
	
	public ArrayList<String> getWatchers() {
		return watchers;
	}
	
	public boolean removeWatcher(String nick){
		for (String n: watchers)
			if (n.equals(nick))
				return watchers.remove(n);
		return false;		
	}
	
	public boolean addWatcher(String c) {
		if (watchers.contains(c)) return false;
		return watchers.add(c);
	}
	
	public boolean hasPlayerConnectedByNick(String nick){
		for (CluedoPlayer p: players)
			if (p.getNick().equals(nick)) return true;
		
		return false;	
	}
	
	@Override
	public boolean hasWatcherConnectedByNick(String nick) {
		for (String w: watchers)
			if (w.equals(nick)) return true;
		return false;
	}

	
}
