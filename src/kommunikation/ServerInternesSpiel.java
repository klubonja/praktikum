package kommunikation;

import kacheln.KachelContainer;
import view.DicePresenter;
import cluedoNetworkLayer.CluedoGameServer;
import cluedoNetworkLayer.CluedoPosition;
import enums.Persons;
import finderOfPaths.Ausloeser;
import finderOfPaths.PlayerCircleManager;
import finderOfPaths.RaumBeweger;
import finderOfPaths.Sucher;
import finderOfPaths.Vorschlaege;
import finderOfPaths.WahnsinnigTollerPathfinder;

public class ServerInternesSpiel {

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
	private int gameID;
	
	
	public ServerInternesSpiel(CluedoGameServer game){
		
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
		
	}
	
	public void wuerfeln(int [] wuerfel){
		int ersterWuerfel = wuerfel[0];
		int zweiterWuerfel = wuerfel[1];
		dicePresenter.rollTheDiceForSomeone(ersterWuerfel, zweiterWuerfel, pcManager);
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
	
}
