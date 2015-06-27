package finderOfPaths;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import kacheln.Kachel;
import staticClasses.auxx;
import view.BoardView;
import cluedoNetworkLayer.CluedoPlayer;
import enums.Orientation;

/**
 * Hier werden die MouseEvents der ballEbene ausgel�st und verwaltet. 
 *  ----------------------------------------------
 * || HIER WIRD AUCH DIE BEWEGUNG AUSGEL�ST!!!!  ||
 *  ----------------------------------------------
 * @since 13.06.2015
 * @version 25.06.2015 
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
	private Orientation [] anweisungenOrientationsVonHier = new Orientation [12];
	private WahnsinnigTollerPathfinder pathfinder;
	
	private int nullSchritte;
	
	private int schritte;
	
	private CluedoPlayer player;
	
	private int xInsgesamt;
	private int yInsgesamt;
	
	private Sucher sucher;
	
	private int wuerfelZahl;
	
	/**
	 * Konstruktor f�r den Ausloeser, welcher ballEbenen-clicks mit Bewegungen verlinkt.
	 * @param gui um aufs KachelArray zuzugreifen
	 * @param beweger um den Spieler/Ball zu bewegen
	 * @param ballEbene um auf die click-events zugreifen zu k�nnen
	 * @param pathfinder um den Weg berechnen zu lassen
	 */
	public Ausloeser(BoardView gui, DerBeweger beweger, BallEbene2 ballEbene, WahnsinnigTollerPathfinder pathfinder, Sucher sucher, CluedoPlayer player){
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
	 * Wird ausgel�st, wenn irgendwo in der ballEbene geclickt wird
	 */
	public void click(MouseEvent event){
				
		if (gewuerfelt){
			gewuerfelt = false;
				
				for (int iReihen = 0; iReihen < gui.getKachelArray().length-1; iReihen++){
					for (int jSpalten = 0; jSpalten < gui.getKachelArray()[iReihen].length-1; jSpalten++){
						if ( (gui.getKachelArray()[iReihen][jSpalten].getLayoutX() <= event.getX()) && (event.getX() < gui.getKachelArray()[iReihen][jSpalten].getLayoutX()+29)
						&& ( (gui.getKachelArray()[iReihen][jSpalten].getLayoutY() <= event.getY()) && (event.getY() < gui.getKachelArray()[iReihen][jSpalten].getLayoutY()+29) ) ){
							
							try {
							
								Kachel momentaneKachel = gui.getKachelArray()[iReihen][jSpalten];
								
								System.out.println("Reihe : "+iReihen +"  ||  Spalte : " +jSpalten);
								
								char [] moeglichkeitenHierher = momentaneKachel.getMoeglichkeitenHierher();
								
								Kachel startKachel = momentaneKachel.getVonHier();
								startKachel.setVonHier(null);
								
								auxx.logsevere("anfangs Kachel laut Auslöser x : " +gui.getColumnIndex(startKachel) +"  ||  y : " +gui.getRowIndex(startKachel));
								
								resetAnweisungen();
								anweisungenOrientations = charToOrientation(moeglichkeitenHierher);
								schritte = wieVieleSchritte(moeglichkeitenHierher);
								
								insgesamteDistanz();
								
								for (int i = 0; i<anweisungenOrientations.length && anweisungenOrientations[i] != null;i++){
									System.out.println("anweisungen : " +anweisungenOrientations[i]);
								}
								
								beweger.anfangsKachelSetzen(startKachel);
								
								beweger.bewegen(anweisungenOrientations, schritte, nullSchritte);
								nullSchritte = 0;
								pathfinder.setWelcheKachel(0);
								
							}catch (NullPointerException e ){}
							
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
		
		Orientation [] anweisungenOrientationsVerarbeitet = new Orientation [12];
		
			for (int counterInnen = 0; counterInnen < anweisungen.length; counterInnen++){
				if (anweisungen[counterInnen] == 'S'){
					anweisungenOrientationsVerarbeitet[counterInnen] = Orientation.S;
				}
				
				if (anweisungen[counterInnen] == 'E'){
					anweisungenOrientationsVerarbeitet[counterInnen] = Orientation.O;
				}
				
				if (anweisungen[counterInnen] == 'N'){
					anweisungenOrientationsVerarbeitet[counterInnen] = Orientation.N;
				}
				
				if (anweisungen[counterInnen] == 'W'){
					anweisungenOrientationsVerarbeitet[counterInnen] = Orientation.W;
				}
				
				if (anweisungen[counterInnen] == 'T'){
					nullSchritte++;
					anweisungenOrientationsVerarbeitet[counterInnen] = null;
				}
				  
			}
		
		return anweisungenOrientationsVerarbeitet;
	}


	
	public void insgesamteDistanz(){
		
		xInsgesamt = 0;
		yInsgesamt = 0;
			
		for (int i = 0; i < anweisungenOrientations.length; i++){
			if (anweisungenOrientations[i] == Orientation.W){
				xInsgesamt--;
			}
			
			else if (anweisungenOrientations[i] == Orientation.O){
				xInsgesamt++;
			}
			
			else if (anweisungenOrientations[i] == Orientation.N){
				yInsgesamt--;
			}
			
			else if (anweisungenOrientations[i] == Orientation.S){
				yInsgesamt++;
			}
		}
	}

	
	public int getWuerfelZahl() {
		return wuerfelZahl;
	}

	public void setWuerfelZahl(int wuerfelZahl) {
		this.wuerfelZahl = wuerfelZahl;
	}

	public void resetAnweisungen(){
		
		for (int i = 0; i < anweisungenOrientationsVonHier.length; i++){
			anweisungenOrientationsVonHier[i] = null;
		}
		
		for (int i = 0; i < anweisungenOrientations.length; i++){
			anweisungenOrientations[i] = null;
		}
	}

	public boolean isGewuerfelt() {
		return gewuerfelt;
	}

	public void setGewuerfelt(boolean gewuerfelt) {
		this.gewuerfelt = gewuerfelt;
	}

	
	
}
