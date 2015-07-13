package finderOfPaths;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import kacheln.Kachel;
import kacheln.KachelContainer;
import staticClasses.auxx;
import view.BoardView;
import cluedoNetworkLayer.CluedoPlayer;
import cluedoNetworkLayer.CluedoPosition;
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
	public PlayerCircleManager pcManager;
	private KachelContainer kacheln;
	
	private Background [][] markierteHintergruende = new Background [25][24];
	
	/**
	 * Erstellt einen Vorschlager, welcher eine gui braucht, um den Kacheln M�glichkeiten zuzuweisen.
	 * @param gui die GUI, in welcher Kacheln informationen zugewiesen bekommen.
	 */
	public Vorschlaege(BoardView gui,PlayerCircleManager pcm, KachelContainer kacheln){
		pcManager = pcm;
		this.gui = gui;
		this.kacheln = kacheln;
		markierteHintergruendeUpdaten();
		
		this.currentPlayer = pcManager.getCurrentPlayer();
		
	}
	
	/**
	 * Geht alle Kacheln durch und gibt den erreichbaren ein char [] mit Anweisungen.
	 * @param moeglichkeitenEingabe alle m�glichen Eingaben - vom pathfinder vorher berechnet.
	 * @param mehrereMoeglichkeiten wenn es mehrere Türen gibt.
	 * @param moeglichkeiten[counterAussen] das zugewiesene char []
	 * @param xPositionenEingabe alle xPositionen, von welchen aus gesucht werden soll 
	 * @param yPositionenEingabe alle yPositionen, von welchen aus gesucht werden soll
	 * @param tuerCounterEingabe von wie vielen Türen aus gesucht werden soll
	 */
	public void vorschlaegeMachen(char [][] moeglichkeitenEingabe, char [][][] mehrereMoeglichkeiten, int [] xPositionenEingabe, int [] yPositionenEingabe, int tuerCounterEingabe, PlayerCircleManager pcManager){
		
		this.xPositionen = xPositionenEingabe;
		this.yPositionen = yPositionenEingabe;
		this.tuerCounter = tuerCounterEingabe;
		this.pcManager = pcManager;
		currentPlayer = pcManager.getCurrentPlayer();
		
		auxx.logsevere("Vorschlager.vorschlaegeMachen");
		auxx.logsevere("currentPlayer Color : " +pcManager.getCurrentPlayer().getCluedoPerson().getColor());
		auxx.logsevere("currentPlayer x : " +pcManager.getCurrentPlayer().getPosition().getX() + "  ||  y : " +pcManager.getCurrentPlayer().getPosition().getY());
		
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
							&& kacheln.getKacheln()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz].isIstRaum() == false)
					{
						moeglichkeitenSetzenMitTueren(Color.GREY);
					}
					
					// Falls das Ganze zu einer T�r zeigt
					else if( (yDistanz != 0 || xDistanz != 0) && (jetzigeReihe + yDistanz >=0) && (jetzigeSpalte + xDistanz >=0) 
							&& (jetzigeReihe + yDistanz < 25) && (jetzigeSpalte + xDistanz < 24)
							&& (moeglichkeiten[counterAussen] != null)
							&& kacheln.getKacheln()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz].isIstRaum() == true
							&& kacheln.getKacheln()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz].isIstTuer() == true)
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
						&& kacheln.getKacheln()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz].isIstRaum() == false)
				{
					moeglichkeitenSetzenOhneTueren(Color.GREY);
				}
				else if( (yDistanz != 0 || xDistanz != 0) && (jetzigeReihe + yDistanz >=0) && (jetzigeSpalte + xDistanz >=0) 
						&& (jetzigeReihe + yDistanz < 25) && (jetzigeSpalte + xDistanz < 24)
						&& (moeglichkeiten[counterAussen] != null)
						&& kacheln.getKacheln()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz].isIstRaum() == true
						&& kacheln.getKacheln()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz].isIstTuer() == true)
				{
					moeglichkeitenSetzenOhneTueren(Color.CORNFLOWERBLUE);
				}
			}
		}
	}

	/**
	 * Updatet die ehemals unmarkierten Hintergründe.
	 */
	public void markierteHintergruendeUpdaten(){
		
		for( int iReihen = 0; iReihen < markierteHintergruende.length;iReihen++){			
			for (int jSpalten = 0; jSpalten < markierteHintergruende[iReihen].length;jSpalten++){
				markierteHintergruende[iReihen][jSpalten] = gui.getKrassesLabelArray()[iReihen][jSpalten].getBackground();
			}
		}
		
	}
	
	/**
	 * Setzt alle möglichkeiten wieder auf null
	 */
	public void resetAnweisungsArrays(){
		
		// Allen arrays werden leere anweisungs-arrays zugewiesen.
		for (int iReihen = 0; iReihen < kacheln.getKacheln().length;iReihen++){
			for (int jSpalten = 0; jSpalten < kacheln.getKacheln()[iReihen].length;jSpalten++){
				kacheln.getKacheln()[iReihen][jSpalten].setMoeglichkeitenHierher(null);
				kacheln.getKacheln()[iReihen][jSpalten].setMoeglichkeitenVonHier(null);
				kacheln.getKacheln()[iReihen][jSpalten].setVonHier(null);
			}				
		}
		
	}
	
	/**
	 * Setzt die Distanz auf den für die moeglichkeiten gültigen Wert
	 */
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
	
	/**
	 * Stellt sicher, dass man aus "S"-Türen etc. nur nach Süden raus kann. 
	 * @return true, falls das möglich ist.
	 */
	public boolean checkForWeirdStuff(){
		Kachel checkThis = kacheln.getKacheln()[jetzigeReihe][jetzigeSpalte];
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
	
	/**
	 * Setzt die moeglichkeitenHierHer und die moeglichkeitenVonHier einer gültigen Kachel
	 * @param farbe
	 */
	public void moeglichkeitenSetzenMitTueren(Color farbe){
		if (checkForWeirdStuff()){
			
			auxx.logsevere("Vorschlager.moeglichkeitenSetzenMitTueren:");
			auxx.logsevere("currentPlayer Color : " +pcManager.getCurrentPlayer().getCluedoPerson().getColor());
			auxx.logsevere("currentPlayer x : " +pcManager.getCurrentPlayer().getPosition().getX() + "  ||  y : " +pcManager.getCurrentPlayer().getPosition().getY());
			
			kacheln.getKacheln()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz].setMoeglichkeitenHierher(moeglichkeiten[counterAussen]);
			
			Kachel vonHier = kacheln.getKacheln()[yPositionen[counter]][xPositionen[counter]];
			kacheln.getKacheln()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz].setVonHier(vonHier);
			
			gui.getKrassesLabelArray()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz].setBackgroundColor(gui.getKrassesLabelArray()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz], farbe);
		}
	}
	

	/**
	 * Setzt die moeglichkeitenHierHer und die moeglichkeitenVonHier einer gültigen Kachel
	 * @param farbe
	 */	
	public void moeglichkeitenSetzenOhneTueren(Color farbe){
		if (checkForWeirdStuff()){
			
			kacheln.getKacheln()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz].setMoeglichkeitenHierher(moeglichkeiten[counterAussen]);
	
			Kachel vonHier = kacheln.getKacheln()[jetzigeReihe][jetzigeSpalte];
			kacheln.getKacheln()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz].setVonHier(vonHier);
	
			gui.getKrassesLabelArray()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz].setBackgroundColor(gui.getKrassesLabelArray()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz], farbe);
		}
	}

	/**
	 * Überprüft, ob diese Position eine der erreichbaren ist.
	 * @param xKoordinate zu überprüfende x Koordinate
	 * @param yKoordinate zu überprüfende y Koordinate
	 * @return true, falls diese Position gültig ist.
	 */
	public boolean hierHerValide(CluedoPosition position){
		
		for (int iReihen = 0; iReihen < kacheln.getKacheln().length; iReihen++){
			for (int jSpalten = 0; jSpalten < kacheln.getKacheln()[iReihen].length; jSpalten++){
				if (gui.getKrassesLabelArray()[iReihen][jSpalten].getBackground().getFills().get(0) == new BackgroundFill(Color.GREY, null, null)){
					if (position.getX() == jSpalten && position.getY() == iReihen){
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	/**
	 * @return der currentPlayer im vorschlager
	 */
	public CluedoPlayer getCurrentPlayer() {
		return currentPlayer;
	}
	
	/**
	 * @param currentPlayer welcher im vorschlager gesetzt werden soll.
	 */
	public void setCurrentPlayer(CluedoPlayer currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	
	
	
}
