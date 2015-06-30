package view;


import java.util.ArrayList;

import javafx.scene.shape.Circle;
import staticClasses.auxx;
import cluedoNetworkLayer.CluedoPlayer;
import finderOfPaths.Ausloeser;
import finderOfPaths.DerBeweger;
import finderOfPaths.GanzTolleSpielerliste;
import finderOfPaths.Sucher;
import finderOfPaths.WahnsinnigTollerPathfinder;

public class Communicater {

	private GameFrameView gameView;
	private BoardView boardView;
	private DiceView diceView;
	
	private GameFramePresenter gamePresenter;
	private DicePresenter dicePresenter;
	
	private Ausloeser ausloeser;
	private Sucher sucher;
	private WahnsinnigTollerPathfinder pathfinder;
	private DerBeweger beweger;
	private ArrayList <CluedoPlayer> players;
	
	//public static GanzTolleSpielerliste<CluedoPlayer> playerManager = new GanzTolleSpielerliste<CluedoPlayer>();
	//public static GanzTolleSpielerliste<Circle> circleManager = new GanzTolleSpielerliste<Circle>();
	
	public Communicater(ArrayList <CluedoPlayer> players){
		
		for (CluedoPlayer p : players){
			GanzTolleSpielerliste.playerManager.add(p);
			GanzTolleSpielerliste.circleManager.add(new Circle(0,0,14,p.getCluedoPerson().getFarbe()));
		}
		
	}
	
	public void startGame(){
		
		auxx.loginfo("Communicater");

		GameFrameView gameView = new GameFrameView();
		gameView.start();
		GameFramePresenter presenterContainer = new GameFramePresenter(gameView);
		dicePresenter = presenterContainer.getDicePresenter();
		
		diceView = gameView.getDice();
		boardView = gameView.getBoard();

		ausloeser = presenterContainer.getAusloeser();
		beweger = presenterContainer.getBeweger();
		//raumBeweger = presenterContainer.getr
		pathfinder = presenterContainer.getPathfinder();
		sucher = presenterContainer.getSucher();
		
	}
	
	public void rollDice(){
		///////////////////////////////
		/////////BRAUCHT INPUT/////////
		///////////////////////////////
		// dicePresenter.rollTheDiceForSomeone(ersterWuerfel, zweiterWuerfel);
	}
	
	public void useSecretPassage(){
		beweger.useSecretPassage();
	}
	
	public void move(){
		
	}
	
	public void suspect(){
		
	}
	
	public void accuse(){
		
	}
	
	public void disprove(){
		
	}
	
	public void endTurn(){
		GanzTolleSpielerliste.playerManager.next();
		GanzTolleSpielerliste.circleManager.next();
		/////////////////////////////////////
		///BENACHRICHTUGUNG, DASS NÄCHSTER///
		/////////ZUG ANGEFANGEN HAT//////////
		/////////////////////////////////////
	}
	
	public void kill(){
		gameView.close();
	}
	
	/*
	 * (check) start game 
	 * roll dice --> letztes Bild ihre Würfel-Kombination und dann pathfinder / sucher / vorschlager "losschicken"
	 * use secret passage --> beweger muss position neu setzen
	 * move --> ausloeser und beweger an diese position bewegen (neue methoden) / mit pathfinder ergebnissen vergleichen
	 * suspect --> nedko
	 * accuse --> nedko
	 * disprove --> nedko
	 * (check) end turn PlayerManager.playerManager.next() UND PlayerManager.circleManager.next() 
	 * 
	 */
	
	
	
}
