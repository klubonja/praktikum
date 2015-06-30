package finderOfPaths;

import view.BoardView;
import cluedoNetworkLayer.CluedoPlayer;

/**
 * @version 25.06.2015
 * @author Benedikt Mayer
 *
 *
 * Hier werden alle möglichen Sachen zum suchen an den Vorschlager weitergereicht.
 */
public class Sucher {

	private BoardView boardView;
	private BallEbene2 ballEbene;
	
	private DerBeweger beweger;
	private Vorschlaege vorschlager;
	private WahnsinnigTollerPathfinder pathfinder;
	
	private int wuerfelZahl;
	
	private CluedoPlayer currentPlayer;
	
	private char [][] anweisungen;
	 
	
	public Sucher(BoardView boardView, BallEbene2 ballEbene, DerBeweger beweger, Vorschlaege vorschlager, WahnsinnigTollerPathfinder pathfinder,  char [][]anweisungen){

		this.boardView = boardView;
		this.ballEbene = ballEbene;
		this.beweger = beweger;
		this.vorschlager = vorschlager;
		this.pathfinder = pathfinder;
		this.currentPlayer = (CluedoPlayer) 
		GanzTolleSpielerliste.playerManager.get(0);
		this.anweisungen = anweisungen;
		
	}
	
	
	public void suchen(int wuerfelZahlEingabe){
		this.wuerfelZahl = wuerfelZahlEingabe;
		
		setToNextPlayer();
		
		pathfinder.findThatPathBetter(wuerfelZahl);
		
		anweisungen = pathfinder.getMoeglichkeiten();		
		char [][][] mehrereAnweisungen = pathfinder.getMehrereMoeglichkeiten();
		int [] xPositionen = pathfinder.getxPositionen();
		int [] yPositionen = pathfinder.getyPositionen();
		int tuerCounter = pathfinder.getTuerCounter();
		
		vorschlager.vorschlaegeMachen(anweisungen, mehrereAnweisungen, xPositionen, yPositionen, tuerCounter);
		pathfinder.setTuerCounter(0);
	}
	
	public void setToNextPlayer(){
		pathfinder.setCurrentPlayer((CluedoPlayer) GanzTolleSpielerliste.playerManager.getCurrentObject());
		vorschlager.setCurrentPlayer((CluedoPlayer) GanzTolleSpielerliste.playerManager.getCurrentObject());
		this.currentPlayer = (CluedoPlayer) GanzTolleSpielerliste.playerManager.getCurrentObject();
	}
	
}
