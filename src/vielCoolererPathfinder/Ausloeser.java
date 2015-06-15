package vielCoolererPathfinder;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import kacheln.Kachel;
import enums.Orientation;

/**
 * Hier werden die MouseEvents der ballEbene ausgelöst und verwaltet. 
 *  ----------------------------------------------
 * || HIER WIRD AUCH DIE BEWEGUNG AUSGELÖST!!!!  ||
 *  ----------------------------------------------
 * @since 13.06.2015
 * @version 13.06.2015 
 * @author Benedikt Mayer, Maximilian Lammel
 *
 */
public class Ausloeser {

	private GUI gui;
	private BallEbene2 ballEbene;
	private DerBeweger beweger;
	private char [] moeglichkeiten;
	private Orientation [] anweisungenOrientations = new Orientation [12];
	private WahnsinnigTollerPathfinder pathfinder;
	
	private UnglaublicheAnwendung anwendung;
	
	/**
	 * Konstruktor für den Ausloeser, welcher ballEbenen-clicks mit Bewegungen verlinkt.
	 * @param gui um aufs KachelArray zuzugreifen
	 * @param beweger um den Spieler/Ball zu bewegen
	 * @param ballEbene um auf die click-events zugreifen zu können
	 * @param pathfinder um den Weg berechnen zu lassen
	 */
	public Ausloeser(GUI gui, DerBeweger beweger, BallEbene2 ballEbene, WahnsinnigTollerPathfinder pathfinder){
		this.gui = gui;
		this.ballEbene = ballEbene;
		this.beweger = beweger;
		this.pathfinder = pathfinder;
		
	}
	
	/**
	 * nimmt clicks aus der ballEbene und weist ihnen hier eine Methode zu.
	 */
	public void zuweisung(){
		ballEbene.setOnMouseClicked(e -> click());
	}
	
	/**
	 * berechnetdie Anzahl der zu gehenden Schritte in jegliche Richtung
	 * @param moeglichkeitenEingabe das auszuwertende möglichkeiten-Array
	 * @return wie viele Schritte zu gehen sind
	 */
	public int wieVieleSchritte(char [] moeglichkeitenEingabe){
		this.moeglichkeiten = moeglichkeitenEingabe;
		int counter = 0;
		for (int i = 0; i < moeglichkeiten.length; i++){
			if (moeglichkeiten[i] != ' '){
				counter++;
			}
		}
		return counter;
	}
	
	/**
	 * Wird ausgelöst, wenn irgendwo in der ballEbene geclickt wird
	 */
	public void click(){
		
			ballEbene.setOnMouseClicked(new EventHandler<MouseEvent>(){

			
			@Override
			public void handle(MouseEvent event) {
				
				anwendung = new UnglaublicheAnwendung();
				
				anwendung.suchen();
				anwendung.zuweisen();
				
				for (int i = 0; i < gui.getKachelArray().length; i++){
					for (int j = 0; j < gui.getKachelArray()[i].length; j++){
						if ( (gui.getKachelArray()[i][j].getLayoutX() <= event.getX()) && (event.getX() < gui.getKachelArray()[i][j].getLayoutX()+80)
						&& ( (gui.getKachelArray()[i][j].getLayoutY() <= event.getY()) && (event.getY() < gui.getKachelArray()[i][j].getLayoutY()+80) ) ){
							Kachel momentaneKachel = gui.getKachelArray()[i][j];
							System.out.println("i : "+i +"  ||  j : " +j);
							char [] moeglichkeitenHierher = momentaneKachel.getMoeglichkeitenHierher();
							anweisungenOrientations = charToOrientation(moeglichkeitenHierher);
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////Bewegung wird ausgelöst/////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////							
							beweger.bewegen(anweisungenOrientations,wieVieleSchritte(moeglichkeitenHierher));
							
						}
					}
				}
				
				
			}
			});
		
		
	}
	
	/**
	 * Wandelt char [] mit anweisungen in Orientation [] mit anweisungen um
	 * @param anweisungen umzuwandeldes char []
	 * @return umgewandeltes Orientation []
	 */
	public Orientation [] charToOrientation(char [] anweisungen){
		
			for (int counterInnen = 0; counterInnen < anweisungen.length; counterInnen++){
				if (anweisungen[counterInnen] == 'S'){
					anweisungenOrientations[counterInnen] = Orientation.S;
				}
				
				if (anweisungen[counterInnen] == 'E'){
					anweisungenOrientations[counterInnen] = Orientation.O;
				}
				
				if (anweisungen[counterInnen] == 'N'){
					anweisungenOrientations[counterInnen] = Orientation.N;
				}
				
				if (anweisungen[counterInnen] == 'W'){
					anweisungenOrientations[counterInnen] = Orientation.W;
				}
				 
			}

		return anweisungenOrientations;
	}
	
}
