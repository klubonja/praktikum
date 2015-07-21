package finderOfPaths;

import kommunikation.PlayerCircleManager;
import staticClasses.auxx;

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
	
	private char [][] anweisungen;
	public PlayerCircleManager pcManager;
	
	public Sucher(Vorschlaege vorschlager, WahnsinnigTollerPathfinder pathfinder, PlayerCircleManager pcManager){
		this.pcManager = pcManager;
		this.vorschlager = vorschlager;
		this.pathfinder = pathfinder;
	}
	
	/**
	 * Hier wird das wuerfelErgebnis und so an den vorschlager --> pathfinder weitergeleitet
	 * @param wuerfelZahlEingabe was gewuerfelt wurde (Ueberrraschung)
	 * @param pcManager jeder darf mal.
	 */
	public void suchen(int wuerfelZahlEingabe, PlayerCircleManager pcManager){
		this.pcManager = pcManager;
		
		auxx.logsevere("Sucher.suchen:");
		auxx.logsevere("pcManager.getCurrentPlayer() Color : " +pcManager.getCurrentPlayer().getCluedoPerson().getColor());
		auxx.logsevere("pcManager.getCurrentPlayer() x : " +pcManager.getCurrentPlayer().getPosition().getX() + "  ||  y : " +pcManager.getCurrentPlayer().getPosition().getY());
		
		pathfinder.findThatPathBetter(wuerfelZahlEingabe, pcManager);
		
		anweisungen = pathfinder.getMoeglichkeiten();		
		char [][][] mehrereAnweisungen = pathfinder.getMehrereMoeglichkeiten();
		int [] xPositionen = pathfinder.getxPositionen();
		int [] yPositionen = pathfinder.getyPositionen();
		int tuerCounter = pathfinder.getTuerCounter();
		
		vorschlager.vorschlaegeMachen(anweisungen, mehrereAnweisungen, xPositionen, yPositionen, tuerCounter, this.pcManager);
		pathfinder.setTuerCounter(0);
	}
}
