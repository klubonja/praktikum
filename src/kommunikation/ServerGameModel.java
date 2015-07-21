package kommunikation;

import java.util.Iterator;
import java.util.Stack;

import animation.RaumBeweger;
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
import finderOfPaths.Sucher;
import finderOfPaths.Vorschlaege;
import finderOfPaths.WahnsinnigTollerPathfinder;

/**
 * @since ca. 15.07.2015
 * @version 21.07.2015
 * @author Benedikt Mayer
 * Das interne Spiel-Model des Servers. Hier darf der Server auch mal spielen!
 */
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
	
	/**
	 * Hier wird unser Server-Spiel-Model erzeugt! Juchee!
	 * @param game unser Spiel, welches gespielt werden soll.
	 */
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
	
	/**
	 * auf geht's! Gogogo!
	 */
	public void start(){
		dealCardsNetwork();
		orderPlayersList();
		stateManager.setNextTurnRec();
	}
	
	/**
	 * @return das winning Statement!
	 */
	public CluedoStatement getWinningStatement() {
		return winningStatement;
	}
	
	/**
	 * Teilt die Karten fuer den Server aus und setzt dann den Spielern auch dementsprechend ihre Haende.
	 */
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
	
	/**
	 * 
	 * @param color gibt es einen Spieler mit dieser Farbe?
	 * @return true, falls es einen Spieler mit dieser Farbe gibt.
	 */
	public boolean checkForColor(Color color){
		Stack<CluedoPlayer> pl = network.getPlayersConnected();
		for (CluedoPlayer p : pl)
			if (p.getCluedoPerson().getFarbe() == color) 
				return true;
		return false;
	}
	
	/**
	 * Sortiert die Spielerliste..?
	 */
	public void orderPlayersList(){
		if (!checkForColor(Color.RED))
			setStart(pcManager.getPlayers());	
	}
	
	/**
	 * ??????????????????????
	 * @param players
	 */
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
	
	/**
	 * damit der Server auf wuerfeln darf. Hier wird gerandomt!
	 * @return das int[] welches erwuerfelt worde! Der Server hat gesprochen!
	 */
	public int[] rollDice(){
		int ersterWuerfel = auxx.getRandInt(1, 6);
		int zweiterWuerfel = auxx.getRandInt(1, 6);
		dicePresenter.rollTheDiceForSomeone(ersterWuerfel, zweiterWuerfel, pcManager);
		stateManager.transitionByAction(PlayerStates.roll_dice);
		return new int[]{ersterWuerfel,zweiterWuerfel};
	}
	
	/**
	 * bewegt eine Spieler an eine neue Position
	 * @param person der zu bewegende Spieler
	 * @param position die Zielposition
	 */
	public void bewegen(Persons person, CluedoPosition position){
		serverBeweger.setNewPosition(person, position);
	}
	
	/**
	 * Bewegt einen Spieler durch einen Geheimgang
	 * @param player wer bewegt wird.
	 * @return die CluedoPosition wo's mit dem Geheimgang hin gehen soll.
	 */
	public CluedoPosition useSecretPassage(CluedoPlayer player){
		return serverBeweger.useSecretPassage(player);
	}
	
	/**
	 * @param position wo sich derjenige hinbewegen will
	 * @return true, falls man sich dahin bewegen darf
	 */
	public boolean checkMove(CluedoPosition position){
		return serverBeweger.movePossible(position);
	}
	
	/**
	 * @param person derjenige, der einen Geheimgang nutzen will
	 * @return true, falls man von hier aus den Geheimgang nutzen darf.
	 */
	public boolean checkSecretPassage(Persons person){
		return serverBeweger.secretPassagePossible(person);
	}
	
	/**
	 * Beendet einen Zug und setzt die naechste Runde
	 */
	public void endTurn(){
		stateManager.transitionByAction(PlayerStates.end_turn);
		setNextRound();
	}
	
	/**
	 * wenn derjenige sich an gewaehlte Zielposition bewegen darf, wird das dann gemacht
	 * @param nick derjenige, der sich bewegen will
	 * @param newpos Die gewaehlte Zielposition
	 * @return true, falls er sich dort hin bewegen kann
	 */
	public boolean movePlayer(String nick,CluedoPosition newpos){
		if (checkMove(newpos)) {
			pcManager.getPlayerByNick(nick).setNewPosition(newpos);
			stateManager.transitionByAction(PlayerStates.move);
			return true;
		}
		return false;
	}

	/**
	 * Bewegt einen Spieler durch einen Geheimgang, falls er das denn darf.
	 * @param nick der Nick des Spielers welcher sich durch den Gang bewegen moechte.
	 * @return die Zielposition falls er sich bewegen darf. Sonst null
	 */
	public CluedoPosition useSecretPassage(String nick){
		CluedoPosition position = useSecretPassage(pcManager.getPlayerByNick(nick));
		if (position != null) {
			pcManager.getPlayerByNick(nick).setNewPosition(position);
			stateManager.transitionByAction(PlayerStates.use_secret_passage);
			return position;
		}
		return null;
	}
	
	/**
	 * Hier suspectet der Server
	 * @param statement das Statement das suspectet wird.
	 */
	public void suspect(CluedoStatement statement) {
		currentPlayerDisproveIndex = pcManager.getCurrentPlayerIndex();
		curSuspicion = statement;
		setNextDisproveRound();		
	}
	
	/**
	 * Hier wird die naechste Disprove-Runde eingeleitet
	 */
	public void setNextDisproveRound(){
		int lastIndex = currentPlayerDisproveIndex;
		currentPlayerDisproveIndex = rotate(currentPlayerDisproveIndex,pcManager.getSize(),false);
		if (currentPlayerDisproveIndex != pcManager.getCurrentPlayerIndex() && currentPlayerDisproveIndex != -1){
			stateManager.handleDisprove(currentPlayerDisproveIndex);
			network.sendStateUpdateMsg(pcManager.getPlayerByIndex(lastIndex));
			network.sendStateUpdateMsg(pcManager.getPlayerByIndex(currentPlayerDisproveIndex));
		}
		else{
			endDisproveRound();
		}
		System.out.println("currentdisproveindex: "+currentPlayerDisproveIndex+" currentplayerindex:"+pcManager.getCurrentPlayerIndex());
	}
	
	/**
	 * Jemand moechte disproven, also lassen wir ihn das auch machen.
	 * @param card die Karte, welche disprovt wird
	 * @param nick der Nick der Person, welche disprovt hat
	 * @return true, falls er disprovt hat. False falls nicht
	 */
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
	
	/**
	 * Hier wird eine disprove-Runde beendet.
	 */
	public void  endDisproveRound(){
		stateManager.transitionByAction(PlayerStates.suspect);
		network.sendStateUpdateMsg(pcManager.getCurrentPlayer());
		currentPlayerDisproveIndex = -1;
		curSuspicion = null;
	}
	
	/**
	 * 
	 * @param start
	 * @param size
	 * @param forward
	 * @return
	 */
	public static int rotate(int start,int size, boolean forward){
		int idx = start;
		if (forward) idx++; //rotate forward
		else idx--;//rotatebackwards
		
		return Math.abs(idx%size);
	}

	/**
	 * Anklagen!
	 * @param accusation die Anklage, welche gemacht wird!
	 * @param nick der Nick der Person, welche anklagt
	 */
	public void accuse(CluedoStatement accusation,String nick) {
		if (accusation.equals(getWinningStatement())){
			network.sendMsgsToAll(
					NetworkMessages.game_endedMsg(
							gameID, 
							nick , 
							accusation
					)
			);
		}
		else {
			pcManager.getPlayerByNick(nick).setAccused(true);
			network.sendMsgsToAll(NetworkMessages.wrongaccuationMsg(gameID, accusation));
			stateManager.transitionByAction(PlayerStates.accuse);
			setNextRound();
		}		
	}

	/**
	 * Next round, boys.
	 */
	public void setNextRound(){
		pcManager.next();
		stateManager.setNextTurnRec();
		network.notifyNextRound();
	}
	
}
