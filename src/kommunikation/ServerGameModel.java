package kommunikation;

import java.util.Iterator;
import java.util.Stack;

import javafx.scene.paint.Color;
import model.Deck;
import kacheln.KachelContainer;
import stateManager.StateManager;
import staticClasses.auxx;
import view.DicePresenter;
import cluedoNetworkLayer.CluedoGameServer;
import cluedoNetworkLayer.CluedoPlayer;
import cluedoNetworkLayer.CluedoPosition;
import cluedoNetworkLayer.WinningStatement;
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
	private int gameID;
	private WinningStatement winningStatement;
	public CluedoGameServer  network;
	
	
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

		dicePresenter = new DicePresenter(pcManager, ausloeser, sucher);
		
		stateManager = new StateManager(pcManager,serverBeweger);
		
		
	}
	
	public void start(){
		dealCardsNetwork();
		orderPlayersList();
		stateManager.setNextTurnRec();
	}
	
	public WinningStatement getWinningStatement() {
		return winningStatement;
	}
	
	public void dealCardsNetwork() {
		Deck deck = new Deck(pcManager.getSize());
		Stack<CluedoPlayer> players = pcManager.getPlayers();
		deck.dealCluedoCards();
		String[] wh = deck.getWinningHand();
		winningStatement = new WinningStatement(
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
		while (it.hasNext()){
			CluedoPlayer pl = it.next();
			if (pl != first){
				players.remove(pl);
				players.add(pl);
			}
		}
		tmplist = null;
	}
	
	public int[] rollDice(){
		int ersterWuerfel = auxx.getRandInt(1, 6);
		int zweiterWuerfel = auxx.getRandInt(1, 6);
		dicePresenter.rollTheDiceForSomeone(ersterWuerfel, zweiterWuerfel, pcManager);
		
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
	}
	
}
