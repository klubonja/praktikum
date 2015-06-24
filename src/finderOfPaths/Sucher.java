package finderOfPaths;

import model.Player;
import view.BoardView;

public class Sucher {

	private BoardView boardView;
	private BallEbene2 ballEbene;
	private KrasserStack krasserStack;
	
	private DerBeweger beweger;
	private Vorschlaege vorschlager;
	private WahnsinnigTollerPathfinder pathfinder;
	
	private int wuerfelZahl;
	
	private Player player;
	
	private char [][] anweisungen;
	 
	
	public Sucher(BoardView boardView, BallEbene2 ballEbene, KrasserStack krasserStack, DerBeweger beweger, Vorschlaege vorschlager, WahnsinnigTollerPathfinder pathfinder,  Player player, char [][]anweisungen){
		this.boardView = boardView;
		this.ballEbene = ballEbene;
		this.krasserStack = krasserStack;
		this.beweger = beweger;
		this.vorschlager = vorschlager;
		this.pathfinder = pathfinder;
		this.player = player;
		this.anweisungen = anweisungen;
		
		
		
	}
	
	
	public void suchen(int wuerfelZahlEingabe){
		this.wuerfelZahl = wuerfelZahlEingabe;
		pathfinder.findThatPathBetter(wuerfelZahl);
		
		anweisungen = pathfinder.getMoeglichkeiten();		
		char [][][] mehrereAnweisungen = pathfinder.getMehrereMoeglichkeiten();
		int [] xPositionen = pathfinder.getxPositionen();
		int [] yPositionen = pathfinder.getyPositionen();
		int tuerCounter = pathfinder.getTuerCounter();
		
		vorschlager.vorschlaegeMachen(anweisungen, mehrereAnweisungen, xPositionen, yPositionen, tuerCounter);
		pathfinder.setTuerCounter(0);
	}
	
	
	
}
