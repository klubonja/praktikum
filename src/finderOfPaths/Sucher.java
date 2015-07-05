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
	public PlayerCircleManager pcManager;
	 
	
	public Sucher(BoardView boardView, BallEbene2 ballEbene, DerBeweger beweger, Vorschlaege vorschlager, WahnsinnigTollerPathfinder pathfinder,  char [][]anweisungen,PlayerCircleManager pcm){
		pcManager = pcm;
		this.boardView = boardView;
		this.ballEbene = ballEbene;
		this.beweger = beweger;
		this.vorschlager = vorschlager;
		this.pathfinder = pathfinder;
		this.currentPlayer = pcManager.getCurrentPlayer();
		this.anweisungen = anweisungen;
		
	}
	
	
	public void suchen(int wuerfelZahlEingabe, PlayerCircleManager pcManager){
		this.wuerfelZahl = wuerfelZahlEingabe;
		this.pcManager = pcManager;
		
		//setToNextPlayer();
		
		pathfinder.findThatPathBetter(wuerfelZahl, pcManager);
		
		anweisungen = pathfinder.getMoeglichkeiten();		
		char [][][] mehrereAnweisungen = pathfinder.getMehrereMoeglichkeiten();
		int [] xPositionen = pathfinder.getxPositionen();
		int [] yPositionen = pathfinder.getyPositionen();
		int tuerCounter = pathfinder.getTuerCounter();
		
		vorschlager.vorschlaegeMachen(anweisungen, mehrereAnweisungen, xPositionen, yPositionen, tuerCounter, pcManager);
		pathfinder.setTuerCounter(0);
	}
	
	public void setToNextPlayer(){
		pathfinder.setCurrentPlayer(pcManager.getCurrentPlayer());
		vorschlager.setCurrentPlayer(pcManager.getCurrentPlayer());
		this.currentPlayer = pcManager.getCurrentPlayer();
	}
	
}
