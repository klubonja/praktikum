package finderOfPaths;

import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import kacheln.Kachel;
import view.BoardView;
import cluedoNetworkLayer.CluedoPlayer;
import enums.Orientation;

/**
 * 
 * Gibt allen erreichbaren Kacheln ein char [] mit anweisungen, wie man dort hin kommen kann.
 * Markiert diese Kacheln auch farbig.
 * @since 13.06.2015
 * @version 25.06.2015
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
	
	private int [] xPositionen;
	private int [] yPositionen;
	
	private int tuerCounter;
	private int counter;
	
	private int xDistanz;
	private int yDistanz;
	
	private CluedoPlayer currentPlayer;
	
	private Background [][] markierteHintergruende = new Background [25][24];
	
	/**
	 * Erstellt einen Vorschlager, welcher eine gui braucht, um den Kacheln M�glichkeiten zuzuweisen.
	 * @param gui die GUI, in welcher Kacheln informationen zugewiesen bekommen.
	 */
	public Vorschlaege(BoardView gui){
		
		this.gui = gui;
		markierteHintergruendeUpdaten();
		
		this.currentPlayer = (CluedoPlayer) GanzTolleSpielerliste.playerManager.get(0);
		
	}
	
	/**
	 * Geht alle Kacheln durch und gibt den erreichbaren ein char [] mit Anweisungen.
	 * @param moeglichkeitenEingabe alle m�glichen Eingaben - vom pathfinder vorher berechnet.
	 * @param jetzigeKachelEingabe die Kachel, von welcher geschaut werden soll.
	 * @param moeglichkeiten[counterAussen] das zugewiesene char []
	 */
	public void vorschlaegeMachen(char [][] moeglichkeitenEingabe, char [][][] mehrereMoeglichkeiten, int [] xPositionenEingabe, int [] yPositionenEingabe, int tuerCounterEingabe){
		
		this.xPositionen = xPositionenEingabe;
		this.yPositionen = yPositionenEingabe;
		this.tuerCounter = tuerCounterEingabe;
		
		gui.resetBackground();
		
		if (tuerCounter != 0){
			
			resetAnweisungsArrays();
			
			// Falls man von einem Raum aus geht
			for (counter = 0; counter < tuerCounter; counter++){
				
				jetzigeSpalte = xPositionen[counter];
				jetzigeReihe = yPositionen[counter];
				moeglichkeiten = mehrereMoeglichkeiten[counter];
		
				
				for (counterAussen = 0; counterAussen < moeglichkeiten.length; counterAussen++ ){
					
					distanzenUpdaten();
					
					// Erreichbare Kacheln
					if ( (yDistanz != 0 || xDistanz != 0) && (jetzigeReihe + yDistanz >=0) && (jetzigeSpalte + xDistanz >=0) 
							&& (jetzigeReihe + yDistanz < 25) && (jetzigeSpalte + xDistanz < 24)
							&& (moeglichkeiten[counterAussen] != null)
							&& gui.getKachelArray()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz].isIstRaum() == false)
					{
						moeglichkeitenSetzenMitTueren(Color.GREY);
					}
					
					// Falls das Ganze zu einer T�r zeigt
					else if( (yDistanz != 0 || xDistanz != 0) && (jetzigeReihe + yDistanz >=0) && (jetzigeSpalte + xDistanz >=0) 
							&& (jetzigeReihe + yDistanz < 25) && (jetzigeSpalte + xDistanz < 24)
							&& (moeglichkeiten[counterAussen] != null)
							&& gui.getKachelArray()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz].isIstRaum() == true
							&& gui.getKachelArray()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz].isIstTuer() == true)
					{
						moeglichkeitenSetzenMitTueren(Color.CORNFLOWERBLUE);
					}
				}
			}
		}
		
		else {
			jetzigeSpalte = currentPlayer.getPosition().getX();
			jetzigeReihe = currentPlayer.getPosition().getY();
			
			this.moeglichkeiten = moeglichkeitenEingabe;
			
			resetAnweisungsArrays();
			
			for (counterAussen = 0; counterAussen < moeglichkeiten.length; counterAussen++ ){
	
				distanzenUpdaten();
				
				// Erreichbare Kacheln
				if ( (yDistanz != 0 || xDistanz != 0) && (jetzigeReihe + yDistanz >=0) && (jetzigeSpalte + xDistanz >=0) 
						&& (jetzigeReihe + yDistanz < 25) && (jetzigeSpalte + xDistanz < 24)
						&& (moeglichkeiten[counterAussen] != null)
						&& gui.getKachelArray()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz].isIstRaum() == false)
				{
					moeglichkeitenSetzenOhneTueren(Color.GREY);
				}
				else if( (yDistanz != 0 || xDistanz != 0) && (jetzigeReihe + yDistanz >=0) && (jetzigeSpalte + xDistanz >=0) 
						&& (jetzigeReihe + yDistanz < 25) && (jetzigeSpalte + xDistanz < 24)
						&& (moeglichkeiten[counterAussen] != null)
						&& gui.getKachelArray()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz].isIstRaum() == true
						&& gui.getKachelArray()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz].isIstTuer() == true)
				{
					moeglichkeitenSetzenOhneTueren(Color.CORNFLOWERBLUE);
				}
			}
		}
	}

	public void markierteHintergruendeUpdaten(){
		
		for( int iReihen = 0; iReihen < markierteHintergruende.length;iReihen++){			
			for (int jSpalten = 0; jSpalten < markierteHintergruende[iReihen].length;jSpalten++){
				markierteHintergruende[iReihen][jSpalten] = gui.getKachelArray()[iReihen][jSpalten].getBackground();
			}
		}
		
	}
	
	public void resetAnweisungsArrays(){
		
		// Allen arrays werden leere anweisungs-arrays zugewiesen.
		for (int iReihen = 0; iReihen < gui.getKachelArray().length;iReihen++){
			for (int jSpalten = 0; jSpalten < gui.getKachelArray()[iReihen].length;jSpalten++){
				gui.getKachelArray()[iReihen][jSpalten].setMoeglichkeitenHierher(null);
				gui.getKachelArray()[iReihen][jSpalten].setMoeglichkeitenVonHier(null);
				gui.getKachelArray()[iReihen][jSpalten].setVonHier(null);
			}				
		}
		
	}
	
	public void distanzenUpdaten(){
		
		yDistanz=0;
		xDistanz=0;
		
		for (counterInnen = 0; counterInnen < moeglichkeiten[counterAussen].length; counterInnen++){
			if (moeglichkeiten[counterAussen][counterInnen] == 'S'){
				yDistanz++;
			}
			
			else if (moeglichkeiten[counterAussen][counterInnen] == 'E'){
				xDistanz++;
			}
			
			else if (moeglichkeiten[counterAussen][counterInnen] == 'N'){
				yDistanz--;
			}
			
			else if (moeglichkeiten[counterAussen][counterInnen] == 'W'){
				xDistanz--;
			}
			
		}
	}
	
	public boolean checkForWeirdStuff(){
		Kachel checkThis = gui.getKachelArray()[jetzigeReihe][jetzigeSpalte];
		if (checkThis.isIstTuer()){
			if (checkThis.getOrientierung() == Orientation.S && moeglichkeiten[counterAussen][0] != 'S'){
				return false;
			}
			else if (checkThis.getOrientierung() == Orientation.W && moeglichkeiten[counterAussen][0] != 'W'){
				return false;
			}
			else if (checkThis.getOrientierung() == Orientation.N && moeglichkeiten[counterAussen][0] != 'N'){
				return false;
			}
			else if (checkThis.getOrientierung() == Orientation.O && moeglichkeiten[counterAussen][0] != 'E'){
				return false;
			}
			else {
				return true;
			}
		}
		else {
			return true;
		}
	}
	
	public void moeglichkeitenSetzenMitTueren(Color farbe){
		if (checkForWeirdStuff()){
			
			gui.getKachelArray()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz].setMoeglichkeitenHierher(moeglichkeiten[counterAussen]);
			
			Kachel vonHier = gui.getKachelArray()[yPositionen[counter]][xPositionen[counter]];
			gui.getKachelArray()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz].setVonHier(vonHier);
			
			gui.getKachelArray()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz].setBackgroundColor(gui.getKachelArray()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz], farbe);
		}
	}
	
	public void moeglichkeitenSetzenOhneTueren(Color farbe){
		if (checkForWeirdStuff()){
			
			gui.getKachelArray()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz].setMoeglichkeitenHierher(moeglichkeiten[counterAussen]);
	
			Kachel vonHier = gui.getKachelArray()[jetzigeReihe][jetzigeSpalte];
			gui.getKachelArray()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz].setVonHier(vonHier);
	
			gui.getKachelArray()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz].setBackgroundColor(gui.getKachelArray()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz], farbe);
		}
	}

	public CluedoPlayer getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(CluedoPlayer currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	
	
	
}
