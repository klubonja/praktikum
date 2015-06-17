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
 * Hier werden die MouseEvents der ballEbene ausgel�st und verwaltet. 
 *  ----------------------------------------------
 * || HIER WIRD AUCH DIE BEWEGUNG AUSGEL�ST!!!!  ||
 *  ----------------------------------------------
 * @since 13.06.2015
 * @version 13.06.2015 
 * @author Benedikt Mayer, Maximilian Lammel
 *
 */
public class Ausloeser {

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
	 * Konstruktor f�r den Ausloeser, welcher ballEbenen-clicks mit Bewegungen verlinkt.
	 * @param gui um aufs KachelArray zuzugreifen
	 * @param beweger um den Spieler/Ball zu bewegen
	 * @param ballEbene um auf die click-events zugreifen zu k�nnen
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
	 * @param moeglichkeitenEingabe das auszuwertende m�glichkeiten-Array
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
	 * Testmethode zum W�rfeln
	 */
	public void wuerfeln(){
		gui.resetBackground();
		gui.resetMoeglichkeiten();
		wuerfelZahl = 1 + (int)(Math.random()*6);
		System.out.println(wuerfelZahl);
		sucher.suchen(wuerfelZahl);
		zuweisung();
	}
	
	/**
	 * Wird ausgel�st, wenn irgendwo in der ballEbene geclickt wird
	 */
	public void click(MouseEvent event){
				
				System.out.println("ausl�ser click");
				
				for (int i = 0; i < gui.getKachelArray().length; i++){
					for (int j = 0; j < gui.getKachelArray()[i].length; j++){
						if ( (gui.getKachelArray()[i][j].getLayoutX() <= event.getX()) && (event.getX() < gui.getKachelArray()[i][j].getLayoutX()+29)
						&& ( (gui.getKachelArray()[i][j].getLayoutY() <= event.getY()) && (event.getY() < gui.getKachelArray()[i][j].getLayoutY()+29) ) ){
							Kachel momentaneKachel = gui.getKachelArray()[i][j];
							System.out.println("i : "+i +"  ||  j : " +j);
							char [] moeglichkeitenHierher = momentaneKachel.getMoeglichkeitenHierher();
							anweisungenOrientations = charToOrientation(moeglichkeitenHierher);
							schritte = wieVieleSchritte(moeglichkeitenHierher);
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////Bewegung wird ausgel�st/////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
							System.out.println("================");
							System.out.println("player x " +player.getxCoord());
							System.out.println("player y " +player.getyCoord());
							beweger.bewegen(anweisungenOrientations, schritte);
							System.out.println("???????????????????");
							System.out.println("player x " +player.getxCoord());
							System.out.println("player y " +player.getyCoord());
							
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

	
	
}
