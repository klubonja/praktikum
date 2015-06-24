package finderOfPaths;

import enums.Orientation;
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
	public void vorschlaegeMachen(char [][] moeglichkeitenEingabe, char [][][] mehrereMoeglichkeiten, int [] xPositionen, int [] yPositionen, int tuerCounter){
		
		gui.resetBackground();
		if (tuerCounter != 0){
			System.out.println("is it happening??");
			
			System.out.println("tuerCounter" +tuerCounter);			
			
			// Allen arrays werden leere anweisungs-arrays zugewiesen.
						for (int iReihen = 0; iReihen < gui.getKachelArray().length;iReihen++){
							for (int jSpalten = 0; jSpalten < gui.getKachelArray()[iReihen].length;jSpalten++){
								gui.getKachelArray()[iReihen][jSpalten].setMoeglichkeitenHierher(null);
								gui.getKachelArray()[iReihen][jSpalten].setMoeglichkeitenVonHier(null);
								gui.getKachelArray()[iReihen][jSpalten].setVonHier(null);
							}				
						}
			
			// Falls man von einem Raum aus geht
			for (int counter = 0; counter < tuerCounter; counter++){
				System.out.println("?????????????????????????????");
				System.out.println((counter+1) +". Durchgang Vorschläge");
				

											//jetzigeSpalte = player.getxCoord();
											//jetzigeReihe = player.getyCoord();
							//				for (int eins = 0; eins < mehrereMoeglichkeiten.length; eins++){
							//					for (int zwei = 0; zwei < mehrereMoeglichkeiten[eins].length; zwei++){
							//						for (int drei = 0; drei < mehrereMoeglichkeiten[eins][zwei].length; zwei++){
							//							System.out.println(mehrereMoeglichkeiten[eins][zwei][drei]);
							//						}
							//					}
							//				}
				System.out.println();
				
				jetzigeSpalte = xPositionen[counter];
				jetzigeReihe = yPositionen[counter];
				moeglichkeiten = mehrereMoeglichkeiten[counter];
				
				System.out.println("counter : " +counter);
				System.out.println("jetzigeSpalte : " +jetzigeSpalte +"  ||  jetzigeReihe : " +jetzigeReihe); // --> 0
				
				//this.moeglichkeiten = moeglichkeitenEingabe;
				
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
						Kachel vonHier = gui.getKachelArray()[yPositionen[counter]][yPositionen[counter]];
						gui.getKachelArray()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz].setVonHier(vonHier);
							//Mehr oder weniger unnötig:
						//char [] [] welchesVonHier = new char [moeglichkeiten.length][];
						//welchesVonHier[counterAussen] = moeglichkeiten[counterAussen];
						
						//ohmygiddygiddygosh++;
						//System.out.println("oh my giddy giddy gosh : " +ohmygiddygiddygosh);
						//gui.getKachelArray()[jetzigeReihe][jetzigeSpalte].setMoeglichkeitenVonHier(welchesVonHier);
						
						gui.getKachelArray()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz].setBackgroundColor(gui.getKachelArray()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz], Color.GREEN);
					}
					
					// Falls das Ganze zu einer Tür zeigt
					else if( (yDistanz != 0 || xDistanz != 0) && (jetzigeReihe + yDistanz >=0) && (jetzigeSpalte + xDistanz >=0) 
							&& (jetzigeReihe + yDistanz < 25) && (jetzigeSpalte + xDistanz < 24)
							&& (moeglichkeiten[counterAussen] != null)
							&& gui.getKachelArray()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz].isIstRaum() == true
							&& gui.getKachelArray()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz].isIstTuer() == true
							) {
						gui.getKachelArray()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz].setMoeglichkeitenHierher(moeglichkeiten[counterAussen]);
							//Mehr oder weniger unnötig:
						Kachel vonHier = gui.getKachelArray()[yPositionen[counter]][yPositionen[counter]];
						gui.getKachelArray()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz].setVonHier(vonHier);
						
						gui.getKachelArray()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz].setBackgroundColor(gui.getKachelArray()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz], Color.CORNFLOWERBLUE);
						
					}
					
				}
				//counterAussen = 0;
			
				

				}

			
			
		}
		
		else {
			jetzigeSpalte = player.getxCoord();
			jetzigeReihe = player.getyCoord();
			
			
			this.moeglichkeiten = moeglichkeitenEingabe;
			
			// Allen arrays werden leere anweisungs-arrays zugewiesen.
			for (int iReihen = 0; iReihen < gui.getKachelArray().length;iReihen++){
				for (int jSpalten = 0; jSpalten < gui.getKachelArray()[iReihen].length;jSpalten++){
					gui.getKachelArray()[iReihen][jSpalten].setMoeglichkeitenHierher(null);
					gui.getKachelArray()[iReihen][jSpalten].setMoeglichkeitenVonHier(null);
				}
			}
			
			for (counterAussen = 0; counterAussen < moeglichkeiten.length; counterAussen++ ){
				//int ohmygiddygiddygosh = 0;
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
						//Mehr oder weniger unnötig:
					Kachel vonHier = gui.getKachelArray()[jetzigeReihe][jetzigeSpalte];
					gui.getKachelArray()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz].setVonHier(vonHier);

					//System.out.println("yo");
					gui.getKachelArray()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz].setBackgroundColor(gui.getKachelArray()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz], Color.GREEN);
				}
				else if( (yDistanz != 0 || xDistanz != 0) && (jetzigeReihe + yDistanz >=0) && (jetzigeSpalte + xDistanz >=0) 
						&& (jetzigeReihe + yDistanz < 25) && (jetzigeSpalte + xDistanz < 24)
						&& (moeglichkeiten[counterAussen] != null)
						&& gui.getKachelArray()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz].isIstRaum() == true
						&& gui.getKachelArray()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz].isIstTuer() == true
						) {
					gui.getKachelArray()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz].setMoeglichkeitenHierher(moeglichkeiten[counterAussen]);
						//Mehr oder weniger unnötig:
					Kachel vonHier = gui.getKachelArray()[jetzigeReihe][jetzigeSpalte];
					gui.getKachelArray()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz].setVonHier(vonHier);
					
					gui.getKachelArray()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz].setBackgroundColor(gui.getKachelArray()[jetzigeReihe + yDistanz][jetzigeSpalte + xDistanz], Color.CORNFLOWERBLUE);
					
				}
				
			}
			counterAussen = 0;
		}
	}

	
}
