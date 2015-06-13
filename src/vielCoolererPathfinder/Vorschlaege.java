package vielCoolererPathfinder;

import javafx.scene.paint.Color;
import kacheln.Kachel;

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

	private GUI gui;
	private char [][] moeglichkeiten;
	private Kachel jetzigeKachel;
	private int jetzigeReihe;
	private int jetzigeSpalte;
	private int counterAussen;
	
	/**
	 * Erstellt einen Vorschlager, welcher eine gui braucht, um den Kacheln Möglichkeiten zuzuweisen.
	 * @param gui die GUI, in welcher Kacheln informationen zugewiesen bekommen.
	 */
	public Vorschlaege(GUI gui){
		
		this.gui = gui;
		
	}
	
	/**
	 * Geht alle Kacheln durch und gibt den erreichbaren ein char [] mit Anweisungen.
	 * @param moeglichkeitenEingabe alle möglichen Eingaben - vom pathfinder vorher berechnet.
	 * @param jetzigeKachelEingabe die Kachel, von welcher geschaut werden soll.
	 * @param moeglichkeiten[counterAussen] das zugewiesene char []
	 */
	public void vorschlaegeMachen(char [][] moeglichkeitenEingabe, Kachel jetzigeKachelEingabe){
		
		this.jetzigeKachel = jetzigeKachelEingabe;
		jetzigeReihe = gui.getRowIndex(jetzigeKachel);
		jetzigeSpalte = gui.getColumnIndex(jetzigeKachel);
		this.moeglichkeiten = moeglichkeitenEingabe;
		
		// Allen arrays werden leere anweisungs-arrays zugewiesen.
		for (int i = 0; i < gui.getKachelArray().length;i++){
			for (int j = 0; j < gui.getKachelArray()[i].length;j++){
				char [] tollesArray = {' ', ' '};
				gui.getKachelArray()[i][j].setMoeglichkeitenHierher(tollesArray);
			}
		}
		
		
		for (counterAussen = 0; counterAussen < moeglichkeiten.length; counterAussen++ ){
			int xDistanz=0;
			int yDistanz=0;
			for (int counterInnen = 0; counterInnen < moeglichkeiten[counterAussen].length; counterInnen++){
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
			gui.getKachelArray()[jetzigeSpalte + xDistanz][jetzigeReihe + yDistanz].setMoeglichkeitenHierher(moeglichkeiten[counterAussen]);

			// Farbliche Markierung für erreichbare Kacheln.
			if (xDistanz != 0 || yDistanz != 0)
			{
				gui.getKachelArray()[jetzigeSpalte + xDistanz][jetzigeReihe + yDistanz].setBackgroundColor(gui.getKachelArray()[jetzigeSpalte + xDistanz][jetzigeReihe + yDistanz], Color.ORANGE);
			}
			
		}
		counterAussen = 0;
		
		
		
		
	}
	
	
}
