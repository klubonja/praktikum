package finderOfPaths;

import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import kacheln.Kachel;
import model.Player;
import view.BoardView;

/**
 * 
 * Gibt allen erreichbaren Kacheln ein char [] mit anweisungen, wie man dort hin kommen kann.
 * Markiert diese Kacheln auch farbig.
 * @since 13.06.2015
 * @version 13.06.2015
 * @author Benedikt Mayer, Maximilian Lammel
 *
 */
public class Vorschlaege {

	private BoardView gui;
	private char [][] moeglichkeiten;
	private Kachel jetzigeKachel;
	private int jetzigeSpalte;
	private int jetzigeReihe;
	private int counterAussen;
	private int counterInnen;
	
	private Player player;
	
	private Kachel [] markierteFelderBuffer = new Kachel [169];
	private int [] [] markierteZahlen = new int [169][2];
	
	private Background [][] markierteHintergruende = new Background [25][24];
	
	/**
	 * Erstellt einen Vorschlager, welcher eine gui braucht, um den Kacheln Möglichkeiten zuzuweisen.
	 * @param gui die GUI, in welcher Kacheln informationen zugewiesen bekommen.
	 */
	public Vorschlaege(BoardView gui, Player player){
		
		this.gui = gui;
		this.player = player;
		
		for( int iReihen = 0; iReihen < markierteHintergruende.length;iReihen++){
			
			for (int jSpalten = 0; jSpalten < markierteHintergruende[iReihen].length;jSpalten++){
				markierteHintergruende[iReihen][jSpalten] = gui.getKachelArray()[iReihen][jSpalten].getBackground();
			}
			
		}
		
		
	}
	
	/**
	 * Geht alle Kacheln durch und gibt den erreichbaren ein char [] mit Anweisungen.
	 * @param moeglichkeitenEingabe alle möglichen Eingaben - vom pathfinder vorher berechnet.
	 * @param jetzigeKachelEingabe die Kachel, von welcher geschaut werden soll.
	 * @param moeglichkeiten[counterAussen] das zugewiesene char []
	 */
	public void vorschlaegeMachen(char [][] moeglichkeitenEingabe){
		
		gui.resetBackground();
		
		//this.jetzigeKachel = jetzigeKachelEingabe;
		jetzigeSpalte = player.getxCoord();
		jetzigeReihe = player.getyCoord();
		this.moeglichkeiten = moeglichkeitenEingabe;
		
		// Allen arrays werden leere anweisungs-arrays zugewiesen.
		for (int iReihen = 0; iReihen < gui.getKachelArray().length;iReihen++){
			for (int jSpalten = 0; jSpalten < gui.getKachelArray()[iReihen].length;jSpalten++){
				gui.getKachelArray()[iReihen][jSpalten].setMoeglichkeitenHierher(null);
			}
		}
		
		
		
		/*
		for( int iSpalten = 0; iSpalten < markierteHintergruende.length;iSpalten++){
			
			for (int jReihen = 0; jReihen < markierteHintergruende[iSpalten].length;jReihen++){
				
				gui.getKachelArray()[iSpalten][jReihen].setBackground(markierteHintergruende[iSpalten][jReihen]);
				
			}
			
		}
		*/
		
		for (counterAussen = 0; counterAussen < moeglichkeiten.length; counterAussen++ ){
			int yDistanz=0;
			int xDistanz=0;
			for (counterInnen = 0; counterInnen < moeglichkeiten[counterAussen].length; counterInnen++){
				if (moeglichkeiten[counterAussen][counterInnen] == 'S'){
					yDistanz++;
				}
				
				if (moeglichkeiten[counterAussen][counterInnen] == 'E'){
					xDistanz++;
				}
				
				if (moeglichkeiten[counterAussen][counterInnen] == 'N'){
					yDistanz--;
				}
				
				if (moeglichkeiten[counterAussen][counterInnen] == 'W'){
					xDistanz--;
				}
				
			}
			counterInnen = 0;
			

			// Erreichbare Kacheln
			if ( (yDistanz != 0 || xDistanz != 0) && (jetzigeReihe + yDistanz >=0) && (jetzigeSpalte + xDistanz >=0) 
					&& (jetzigeReihe + yDistanz < 25) && (jetzigeSpalte + xDistanz < 24)
					&& (moeglichkeiten[counterAussen] != null)
					&& gui.getKachelArray()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz].isIstRaum() == false)
			{
				gui.getKachelArray()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz].setMoeglichkeitenHierher(moeglichkeiten[counterAussen]);
				
				//Farbliche Markierung
					// Buffer
				//markierteHintergruende[jetzigeSpalte + xDistanz][jetzigeReihe + yDistanz] = gui.getKachelArray()[jetzigeSpalte + xDistanz][jetzigeReihe + yDistanz].getBackground();
				
				gui.getKachelArray()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz].setBackgroundColor(gui.getKachelArray()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz], Color.GREEN);
			}
			else {
				
			}
			
		}
		counterAussen = 0;
		
		
		
		
	}
	
	
}
