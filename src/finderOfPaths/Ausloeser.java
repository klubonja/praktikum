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
	private Orientation [] anweisungenOrientationsVonHier = new Orientation [12];
	private WahnsinnigTollerPathfinder pathfinder;
	
	private int nullSchritte;
	
	private char [] richtigeAnweisungen;
	
	private int schritte;
	
	private Player player;
	
	private int xInsgesamt;
	private int yInsgesamt;
	
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
		ballEbene.getBeginnen().setOnMouseClicked(e -> beweger.anfangsPositionSetzen());
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
							
							//try {
								
							
							Kachel momentaneKachel = gui.getKachelArray()[iReihen][jSpalten];
							System.out.println("Reihe : "+iReihen +"  ||  Spalte : " +jSpalten);
							char [] moeglichkeitenHierher = momentaneKachel.getMoeglichkeitenHierher();
							
							Kachel startKachel = momentaneKachel.getVonHier();
							resetAnweisungen();
							anweisungenOrientations = charToOrientation(moeglichkeitenHierher);
							//anweisungenOrientationsVonHier = charToOrientation(moeglichkeitenVonHier);
							schritte = wieVieleSchritte(moeglichkeitenHierher);
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////Bewegung wird ausgelöst/////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
//							System.out.println("================");
//							System.out.println("player x " +player.getxCoord());
//							System.out.println("player y " +player.getyCoord());
							// Hier: Schon falsch
							
							insgesamteDistanz();
							
							beweger.anfangsKachelSetzen(startKachel);
							
							beweger.bewegen(anweisungenOrientations, schritte, nullSchritte);
							nullSchritte = 0;
							
							
							//}
							//catch (NullPointerException e ){
							//	
							//}
//							System.out.println("???????????????????");
//							System.out.println("player x " +player.getxCoord());
//							System.out.println("player y " +player.getyCoord());
							
						}
					}
				}
		}
	}
	
	public void checkWhatsOk(char [][] anweisungenVonHierEingabe, char [] anweisungenHierherEingabe){
		richtigeAnweisungen = new char [anweisungenHierherEingabe.length];
		int stelle = 0;
		for (int welcheVersion = 0; welcheVersion < anweisungenVonHierEingabe.length && anweisungenVonHierEingabe[welcheVersion] != null; welcheVersion++){
			for (int welcheAnweisung = 0; welcheAnweisung < anweisungenVonHierEingabe[welcheVersion].length; welcheAnweisung++){
				for (int welcherVorschlag = 0; welcherVorschlag < anweisungenHierherEingabe.length; welcherVorschlag++){
					if (anweisungenVonHierEingabe[welcheVersion][welcheAnweisung] == anweisungenHierherEingabe[welcherVorschlag]){
						
						richtigeAnweisungen[stelle] = anweisungenVonHierEingabe[welcheVersion][welcheAnweisung];
						stelle++;
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
	
}
