package kommunikation;

import java.util.Iterator;
import java.util.Stack;

import javafx.scene.paint.Color;
import kacheln.KachelContainer;
import model.Deck;
import stateManager.StateManager;
import staticClasses.NetworkMessages;
import staticClasses.auxx;
import view.DicePresenter;
import cluedoNetworkLayer.CluedoGameServer;
import cluedoNetworkLayer.CluedoPlayer;
import cluedoNetworkLayer.CluedoPosition;
import cluedoNetworkLayer.CluedoStatement;
import enums.Persons;
import enums.PlayerStates;
import enums.Rooms;
import enums.Weapons;
import finderOfPaths.Ausloeser;
import finderOfPaths.PlayerCircleManager;
import finderOfPaths.RaumBeweger;
import finderOfPaths.Sucher;
import finderOfPaths.Vorschlaege;
import finderOfPaths.WahnsinnigTollerPathfinder;

public class ServerGameModel {
	
	private int gameID;
	private int currentPlayerDisproveIndex = -1;
	private CluedoStatement curSuspicion = null;
	
	private CluedoStatement winningStatement;
	
	public CluedoGameServer  network;
	
	
	private Sucher sucher;
	private Vorschlaege vorschlager;
	private WahnsinnigTollerPathfinder pathfinder;
	private Ausloeser ausloeser;
	private DicePresenter dicePresenter;
	private PlayerCircleManager pcManager;
	private KachelContainer kacheln;
	private ServerBoard serverBoard;
	private ServerBeweger serverBeweger;
	private RaumBeweger raumBeweger;
	private StateManager stateManager;
	
	
	
	
	public ServerGameModel(CluedoGameServer game){
		
		network = game;
		gameID = game.getGameId();
		pcManager = new PlayerCircleManager(game.getPlayers());		
		
		kacheln = new KachelContainer();
		serverBoard = new ServerBoard(kacheln); // Setzt Kacheln auf Spielfeldwerte
		raumBeweger = new RaumBeweger(pcManager, kacheln);
		serverBeweger = new ServerBeweger(pcManager, kacheln, raumBeweger);
		vorschlager = new Vorschlaege(pcManager, kacheln);
		pathfinder = new WahnsinnigTollerPathfinder(pcManager, kacheln);
		
		sucher = new Sucher(vorschlager, pathfinder, pcManager);
		ausloeser = new Ausloeser(kacheln, gameID, pathfinder, pcManager);

		dicePresenter = new DicePresenter(pcManager, ausloeser, sucher,kacheln);
		
		stateManager = new StateManager(pcManager,serverBeweger);
		
		
	}
	
	public void start(){
		dealCardsNetwork();
		orderPlayersList();
		stateManager.setNextTurnRec();
	}
	
	public CluedoStatement getWinningStatement() {
		return winningStatement;
	}
	
	public void dealCardsNetwork() {
		Deck deck = new Deck(pcManager.getSize());
		Stack<CluedoPlayer> players = pcManager.getPlayers();
		deck.dealCluedoCards();
		String[] wh = deck.getWinningHand();
		winningStatement = new CluedoStatement(
				Persons.getPersonByColor(wh[0]),
				Weapons.getWeaponByName(wh[1]), Rooms.getRoomByName(wh[2]));

		String[][] playerHands = deck.getPlayerHands();
		for (int i = 0; i < players.size(); i++) {
			players.get(i).setCards(playerHands[i]);
		}
	}
	
	public boolean checkForColor(Color color){
		Stack<CluedoPlayer> pl = network.getPlayersConnected();
		for (CluedoPlayer p : pl)
			if (p.getCluedoPerson().getFarbe() == color) 
				return true;
		return false;
	}
	
	public void orderPlayersList(){
		if (!checkForColor(Color.RED))
			setStart(pcManager.getPlayers());	
	}
	
	public static void setStart(Stack<CluedoPlayer> players){
		CluedoPlayer first = players.get(auxx.getRandInt(0, players.size()-1));
		Stack<CluedoPlayer>  tmplist = (Stack<CluedoPlayer>)players.clone(); 
		Iterator<CluedoPlayer> it = tmplist.iterator();
		CluedoPlayer pl = first;
		while (it.hasNext() && pl != first){
			pl = it.next();
			players.remove(pl);
			players.add(pl);
		}
		tmplist = null;
	}
	
	public int[] rollDice(){
		int ersterWuerfel = auxx.getRandInt(1, 6);
		int zweiterWuerfel = auxx.getRandInt(1, 6);
		dicePresenter.rollTheDiceForSomeone(ersterWuerfel, zweiterWuerfel, pcManager);
		stateManager.transitionByAction(PlayerStates.roll_dice);
		return new int[]{ersterWuerfel,zweiterWuerfel};
	}
	
	public void bewegen(Persons person, CluedoPosition position){
		serverBeweger.setNewPosition(person, position);
	}
	
	public void useSecretPassage(Persons person){
		serverBeweger.useSecretPassage(person);
	}
	
	/**
	 * 
	 * @param position
	 * @return true, falls man sich dahin bewegen darf
	 */
	public boolean checkMove(CluedoPosition position){
		return serverBeweger.movePossible(position);
	}
	
	/**
	 * 
	 * @param person
	 * @return true, falls man von hier aus den Geheimgang nutzen darf.
	 */
	public boolean checkSecretPassage(Persons person){
		return serverBeweger.secretPassagePossible(person);
	}
	
	public void endTurn(){
		stateManager.transitionByAction(PlayerStates.end_turn);
		setNextRound();
	}
	
	public boolean movePlayer(String nick,CluedoPosition newpos){
		if (checkMove(newpos)) {
			pcManager.getPlayerByNick(nick).setNewPosition(newpos);
			stateManager.transitionByAction(PlayerStates.move);
			return true;
		}
		return false;
	}
	
	public void setNextRound(){
		pcManager.next();
		stateManager.setNextTurnRec();
		network.notifyNextRound();
	}

	public void suspect(CluedoStatement statement) {
		currentPlayerDisproveIndex = pcManager.getCurrentPlayerIndex();
		curSuspicion = statement;
		setNextDisproveRound();		
	}
	
	public void setNextDisproveRound(){
		currentPlayerDisproveIndex = rotate(currentPlayerDisproveIndex,pcManager.getSize(),false);
		if (currentPlayerDisproveIndex != pcManager.getCurrentPlayerIndex() && currentPlayerDisproveIndex != -1){
			stateManager.handleDisprove(currentPlayerDisproveIndex);
			network.sendStateUpdateMsg(pcManager.getPlayerByIndex(currentPlayerDisproveIndex));
		}
		else{
			endDisproveRound();
		}
	}
	
	public boolean disprove(String card,String nick){
		if (curSuspicion.isDisprovenBy(card)){
			network.sendMsgsToAll(NetworkMessages.disprovedMsg(gameID, nick));
			network.sendMsgByPlayer(
					pcManager.getCurrentPlayer(), 
					NetworkMessages.disprovedMsg(
							gameID, 
							nick, 
							card
					)
			);
			endDisproveRound();
			return true;
		}
		network.sendMsgsToAll(NetworkMessages.no_disproveMsg(gameID));
		setNextDisproveRound();
		return false;
	}
	
	public void  endDisproveRound(){
		stateManager.transitionByAction(PlayerStates.suspect);
		network.sendStateUpdateMsg(pcManager.getCurrentPlayer());
		currentPlayerDisproveIndex = -1;
		curSuspicion = null;
	}
	
	public static int rotate(int start,int size, boolean forward){
		int idx = start;
		if (forward) idx++;
		else idx--;
		
		return idx%size;
	}

	
	
}
