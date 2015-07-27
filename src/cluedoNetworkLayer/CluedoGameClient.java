package cluedoNetworkLayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Stack;

import javafx.application.Platform;
import kommunikation.Communicator;
import staticClasses.Images;
import staticClasses.NetworkMessages;
import staticClasses.auxx;
import cluedoClient.ServerItem;
import enums.GameStates;

public class CluedoGameClient extends CluedoGame {

	ServerItem server;
	String myNick;
	
	ArrayList<String> watchers;
	
	Communicator communicator;

	public CluedoGameClient(int gameId, ServerItem server) {
		super(gameId);
		Images.initImages();
		this.server = server;		
		watchers = new ArrayList<String>();
		myNick = server.getMyNick();
	}

	
	//////////////////////////////RUNNIGAME////////////////////////////////////////////////////////
	
	private void startGameGui(){
		Platform.runLater(() -> {
			communicator = new Communicator(this);
			communicator.startGame();
			String role;
			if (this.hasWatcherConnectedByNick(myNick)) role = "watching";
			else role = "playing";
			communicator.setTitle(String.format("%s %s on server %s Game %d", getMyNick(),role,server.getGroupName(),gameId));

		});
	}

	
	@Override
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
	
//	public void compareCards(String person, String weapon, String room) {
//		Platform.runLater(() -> {
//			communicator.compareCards(person, weapon, room);
//		});
//	}
	
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
	
	public void changeZugFensterButtons(ArrayList<String> states){
		communicator.setZugFensterButtons(states);
	}
	
//	public void showDisprovals(String person, String weapon, String room) {
//		Platform.runLater(() -> {
//			communicator.showPossibleDisprovals(person, weapon, room);
//		});
//	}
	
	public void itsSomeonesTurn(String nick){
		Platform.runLater(() -> {
			communicator.itsSomeonesTurn(nick);
		});
		
	}
	
	public void move(CluedoPosition position, String person) {
		
		Platform.runLater(() -> {
			communicator.move(position, person);
		});
	}
	
	public void disprove() {
		Platform.runLater(() -> {
			communicator.handleDisprove();
		});
	}
	
	public void moved() {
		Platform.runLater(() -> {
			communicator.moved(); //for KI
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
	
	public void setCurSuspicion(CluedoStatement sus){
		communicator.setCurSuspicion(sus);
	}
	
	///////////////////////////////GETTER////////////////////////////////////////////////////////
	
	public ServerItem getServer() {
		return server;
	}

	public Communicator getCommunicator() {
		return communicator;
	}

	public String getMyNick() {
		return myNick;
	}
	
	public String getWatchersConnected(){
		return auxx.formatStringList(getWatchers(), "and");
	}
	
	public ArrayList<String> getWatchers() {
		return watchers;
	}
	
	////////////////////////////////SETTER/////////////////////////////////////////////////////////
	
	public void setMyNick(String myNick) {
		this.myNick = myNick;
	}

	///////////////////////////////GAMEGUIACTION//////////////////////////////////////////////////////
	
	public void somebodyIsAccusing(String nick) {
		Platform.runLater(() -> {
		communicator.checkIfWon(nick);
		});
	}
	
	public void somebodyFailedToAccuse(String person, String weapon, String room) {
		String str = "Accusation failed for: " + person + " " + weapon + " "
				+ room;
		showyourstates(str);
	}

	public void showyourstates(String str) {
		Platform.runLater(() -> {
			communicator.showyourstates(str);
		});
	}
	
	public void showothersstates(String str) {
		// TODO Auto-generated method stub
		Platform.runLater(() -> {
			communicator.showsothersstates(str);
		});
	}	
	
	public void addChatMsg(String msg) {
		Platform.runLater(() -> {
			communicator.addChatMsg(msg);
		});
	}

	public void killCommunicator() {
		Platform.runLater(() -> {
			if (communicator != null) communicator.kill();
		});
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	

	

	

	
	


	public void sendMsgToServer(String msg) {
		auxx.sendTCPMsg(server.getSocket(), msg);
	}

	
	public void kill() {
		try {
			server.sendMsg(NetworkMessages.leave_gameMsg(gameId));
		}
		catch (Exception e){	
			auxx.logsevere("sending leave msg failed :", e);
		}
		finally {
			killCommunicator();
		}
	}
	
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
	
	@Override
	public boolean hasPlayerConnectedByNick(String nick){
		for (CluedoPlayer p: players)
			if (p.getNick().equals(nick)) 
				return true;		
		return false;	
	}
	
	@Override
	public boolean hasWatcherConnectedByNick(String nick) {
		for (String w: watchers)
			if (w.equals(nick)) return true;
		return false;
	}

	public void setCurrentSuspicion(CluedoStatement suspicion) {
		Platform.runLater(() -> {
			communicator.setCurSuspicion(suspicion);
		});
		
	}

	public void moveForSuspicion(int gameID, CluedoStatement suspicion) {
		Platform.runLater(() -> {
			communicator.moveForSuspiciton(gameID,suspicion);
		});
	}


	public void setCardsForPlayer(ArrayList<String> cards) {
		getConnectedPlayerByName(server.getMyNick()).setCards(cards);
//		getCommunicator().
//		setCards(
//				server.getMyNick(),
//				cards);
		
	}





	public void showPoolcards(ArrayList<String> karten) {
		Platform.runLater(() -> {
			communicator.showPoolCards(karten);
		});
		
	}


	public void showDisprove(String karte) {
		Platform.runLater(() -> {
			communicator.showDisprovedCard(karte);
		});
		
	}

	public void showSuspicionCards(int gameID, CluedoStatement suspicion) {
		Platform.runLater(() -> {
			communicator.showCardsforSuspiction(suspicion);
		});
		
	}	
}
