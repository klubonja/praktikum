package finderOfPaths;

import staticClasses.auxx;
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

	private Vorschlaege vorschlager;
	private WahnsinnigTollerPathfinder pathfinder;
	
	private int wuerfelZahl;
	
	private char [][] anweisungen;
	public PlayerCircleManager pcManager;
	 
	
	public Sucher(Vorschlaege vorschlager, WahnsinnigTollerPathfinder pathfinder, PlayerCircleManager pcManager){
		this.pcManager = pcManager;
		this.vorschlager = vorschlager;
		this.pathfinder = pathfinder;
	}
	
	public void suchen(int wuerfelZahlEingabe, PlayerCircleManager pcManager){
		this.wuerfelZahl = wuerfelZahlEingabe;
		this.pcManager = pcManager;
		
		auxx.logsevere("Sucher.suchen:");
		auxx.logsevere("pcManager.getCurrentPlayer() Color : " +pcManager.getCurrentPlayer().getCluedoPerson().getColor());
		auxx.logsevere("pcManager.getCurrentPlayer() x : " +pcManager.getCurrentPlayer().getPosition().getX() + "  ||  y : " +pcManager.getCurrentPlayer().getPosition().getY());
		
		pathfinder.findThatPathBetter(wuerfelZahl, pcManager);
		
		anweisungen = pathfinder.getMoeglichkeiten();		
		char [][][] mehrereAnweisungen = pathfinder.getMehrereMoeglichkeiten();
		int [] xPositionen = pathfinder.getxPositionen();
		int [] yPositionen = pathfinder.getyPositionen();
		int tuerCounter = pathfinder.getTuerCounter();
		
		vorschlager.vorschlaegeMachen(anweisungen, mehrereAnweisungen, xPositionen, yPositionen, tuerCounter, this.pcManager);
		pathfinder.setTuerCounter(0);
	}
}
