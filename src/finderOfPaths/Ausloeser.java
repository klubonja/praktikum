package finderOfPaths;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import kacheln.Kachel;
import model.Player;
import view.BoardView;
import view.DicePresenter;
import view.DiceView;
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

	private boolean gewuerfelt;
	
	private BoardView gui;
	private BallEbene2 ballEbene;
	private DerBeweger beweger;
	private char [] moeglichkeiten;
	private Orientation [] anweisungenOrientations = new Orientation [12];
	private WahnsinnigTollerPathfinder pathfinder;
	
	private int schritte;
	
	private Player player;
	
	private Sucher sucher;
	
	private int wuerfelZahl;
	
	private UnglaublicheAnwendung anwendung;
	
	DicePresenter dice;
	
	/**
	 * Konstruktor für den Ausloeser, welcher ballEbenen-clicks mit Bewegungen verlinkt.
	 * @param gui um aufs KachelArray zuzugreifen
	 * @param beweger um den Spieler/Ball zu bewegen
	 * @param ballEbene um auf die click-events zugreifen zu können
	 * @param pathfinder um den Weg berechnen zu lassen
	 */
	public Ausloeser(BoardView gui, DerBeweger beweger, BallEbene2 ballEbene, WahnsinnigTollerPathfinder pathfinder, Sucher sucher, Player player){
		this.gui = gui;
		this.ballEbene = ballEbene;
		this.beweger = beweger;
		this.pathfinder = pathfinder;
		this.sucher = sucher;
		this.player = player;
		
	}
	
	/**
	 * nimmt clicks aus der ballEbene und weist ihnen hier eine Methode zu.
	 */
	public void zuweisung(){
		System.out.println("zuweisung");
		ballEbene.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				click(event);
			}});
		ballEbene.getWuerfeln().setOnMouseClicked(e -> wuerfeln());
//		DiceView.roll.setOnMouseClicked(e -> wuerfeln());
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
	 * Testmethode zum Würfeln
	 */
	public void wuerfeln(){
		gui.resetBackground();
		gui.resetMoeglichkeiten();
		wuerfelZahl = (1 + (int)(Math.random()*6)) + (1 + (int)(Math.random()*6)) ;
		System.out.println("==================================");
		System.out.println("==================================");
		System.out.println("Würfelzahl : " +wuerfelZahl);
		System.out.println("==================================");
		System.out.println("==================================");
		sucher.suchen(wuerfelZahl);
		zuweisung();
		gewuerfelt = true;
	}
	
	/**
	 * Wird ausgelöst, wenn irgendwo in der ballEbene geclickt wird
	 */
	public void click(MouseEvent event){
				
		if (gewuerfelt){
			gewuerfelt = false;
				System.out.println("auslöser click");
				
				
				
				for (int iReihen = 0; iReihen < gui.getKachelArray().length-1; iReihen++){
					for (int jSpalten = 0; jSpalten < gui.getKachelArray()[iReihen].length-1; jSpalten++){
						if ( (gui.getKachelArray()[iReihen][jSpalten].getLayoutX() <= event.getX()) && (event.getX() < gui.getKachelArray()[iReihen][jSpalten].getLayoutX()+29)
						&& ( (gui.getKachelArray()[iReihen][jSpalten].getLayoutY() <= event.getY()) && (event.getY() < gui.getKachelArray()[iReihen][jSpalten].getLayoutY()+29) ) ){
							
							try {
								
							
							Kachel momentaneKachel = gui.getKachelArray()[iReihen][jSpalten];
							System.out.println("Reihe : "+iReihen +"  ||  Spalte : " +jSpalten);
							char [] moeglichkeitenHierher = momentaneKachel.getMoeglichkeitenHierher();
							resetAnweisungen();
							anweisungenOrientations = charToOrientation(moeglichkeitenHierher);
							schritte = wieVieleSchritte(moeglichkeitenHierher);
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////Bewegung wird ausgelöst/////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
//							System.out.println("================");
//							System.out.println("player x " +player.getxCoord());
//							System.out.println("player y " +player.getyCoord());
							// Hier: Schon falsch
							for (int h = 0; h < moeglichkeitenHierher.length; h++){
								System.out.println(moeglichkeitenHierher[h]);
							}
							
							beweger.bewegen(anweisungenOrientations, schritte);
							
							
							}
							catch (NullPointerException e ){
								
							}
//							System.out.println("???????????????????");
//							System.out.println("player x " +player.getxCoord());
//							System.out.println("player y " +player.getyCoord());
							
						}
					}
				}
		}
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

	public int getWuerfelZahl() {
		return wuerfelZahl;
	}

	public void setWuerfelZahl(int wuerfelZahl) {
		this.wuerfelZahl = wuerfelZahl;
	}

	public void resetAnweisungen(){
		
		for (int i = 0; i < anweisungenOrientations.length; i++){
			anweisungenOrientations[i] = null;
		}
		
	}
	
}
