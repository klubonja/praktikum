package finderOfPaths;

import model.Player;
import view.BoardView;
import enums.Orientation;

/**
 * @since 10.06.2015
 * @version 13.06.2015
 * @author Benedikt Mayer, Maximilian Lammel
 *
 *	Hier werden alle M�glichkeiten berechnet, von der momentanen Position aus
 * sich zu bewegen in die entsprechenden Himmelsrichtungen. 
 */
public class WahnsinnigTollerPathfinder {

	/**
	 * Alle M�glichkeiten, welche an Wegen ausgegeben werden.
	 */
	private char [][] moeglichkeiten = new char [2440000][12];
	private int welcheMoeglichkeit; 
	
	private char hans;
	
	private boolean letztesMalTuer;
	
	private String tuerRichtung;
	
	/**
	 * Die momentane Position an gegebenem Baum-Level
	 */
	private int [] rootX = new int [13];
	private int [] rootY = new int [13];
	
	/**
	 * Das momentane Baum-Level
	 */
	private int level;
	
	
	/**
	 * Die Position, von welcher momentan �berpr�ft wird.
	 */
	private int jetzigeSpalte;
	private int jetzigeReihe;
	
	/**
	 * Das Spielfeld und die Ballebene mit welcher gerechnet wird.
	 */
	private BoardView gui;
	private BallEbene2 ballEbene;
	
	/**
	 * Der momentane String mit Anweisungen
	 */
	private String currentEntry;
	
	/**
	 * Der Model-Spieler
	 */
	private Player player;
	
	private int schritte;
	
	/**
	 *  Die Himmelsrichtungen S, E, N, W
	 */
    private char[] himmelsrichtungen = new char[] {'S','E', 'N', 'W'};
	
	/**
	 * Konstruktor f�r den pathfinder, welcher alle m�glichen Wege berechnet.
	 * @param gui
	 * @param ballEbene
	 */
	public WahnsinnigTollerPathfinder(BoardView gui, BallEbene2 ballEbene, Player player){
		
		this.gui = gui;
		this.ballEbene = ballEbene;
		this.player = player;
		
	}
	
	/**
	 * Leerer Konstruktor, wenn man ohne Parameter einen pathfinder erstellen will.
	 */
	public WahnsinnigTollerPathfinder(){		
	}
	
	/**
	 * Hauptmethode, mit welcher die ganzen checks aufgerufen werden.
	 */
	public void findThatPathBetter(int wuerfelZahl){

        // Die Werte werden auf die Urpsrungsposition gesetzt.
        reset(player.getyCoord(),player.getxCoord());
        
        //System.out.println("!!!!!!!!!!!!!!!!!!!");
        //System.out.println("player x "+player.getxCoord());
        //System.out.println("player y "+player.getyCoord());
        //System.out.println("jetzt x "+jetzigeReihe);
        //System.out.println("jetzt y "+jetzigeSpalte);
        ausgangsPosition(jetzigeReihe, jetzigeSpalte);
        currentEntry = null;
        
        possibleMoves(wuerfelZahl, himmelsrichtungen,"");
	}
		
	/**
	 * Hier wird die momentane Position (y,x) gesetzt.
	 * @param reihe hierauf wird die jetzigeSpalte gesetzt
	 * @param spalte hierauf wird die jetzigeReihe gesetzt
	 */
	public void reset(int reihe, int spalte){
		
		jetzigeReihe = reihe;
		jetzigeSpalte = spalte;
		
		System.out.println("````````````````````````````````````````");
		System.out.println("hans : "+hans);
		if (hans == ' '){
			System.out.println("gleich!");
		}
		for (int i = 0; i < moeglichkeiten.length; i++){
			for (int j = 0; j < moeglichkeiten[i].length; j++){
				moeglichkeiten[i][j] = hans;
			}
			
		}
		
		
		
	}

	/**
	 * Hier wird 
	 * @param jetzigeSpalte auf @param rootY[level] gesetzt 
	 * @param jetzigeReihe auf @param rootX[level] gesetzt
	 */
	public void refreshRoot(){
		jetzigeSpalte = rootX[level];
		jetzigeReihe = rootY[level];
	}
	
	/**
	 * Hier werden die kompletten root Werte auf die Eingaben gesetzt
	 * @param eingabeSpalte bestimmt alle @param rootX -Werte
	 * @param eingabeReihe bestimmt alle @param rootY -Werte
	 */
	public void ausgangsPosition( int eingabeReihe,int eingabeSpalte){
		level = 0;
		
		// Alle roots werden auf die Eingabe gesetzt.
		for (int i = 0; i < rootX.length; i++){
			rootX[i] = eingabeSpalte;
			rootY[i] = eingabeReihe;
		}		
		
	}
	
	/**
	 * Hier werden die detect-Methoden aufgerufen und gecheckt ob der move legal ist.
	 * @param richtung gibt die zu �berpr�fende Himmelsrichtung an
	 * @return true wenn die input-Richtung erlaubt ist.
	 * @return false wenn die input-Richtung verboten ist.
	 */
	public boolean detectHimmelsrichtung(char richtung){
		
		level = currentEntry.length();
		
		//System.out.println("rootX[level] : " + rootX[level]);
		//System.out.println("rootY[level] : " + rootY[level]);
		
		if (richtung == 'S'){
			return detectSouth();
		}
		
		else if (richtung == 'E'){
			return detectEast();
		}
		
		else if (richtung == 'N'){
			return detectNorth();
		}
		
		else if (richtung == 'W'){
			return detectWest();
		}
		
		else {
			//System.out.println("ung�ltige Himmelsrichtung");
			return false;
		}
	}
	
	/**
	 * Hier wird �berpr�ft ob man sich nach S�den bewegen darf
	 * @return true falls im S�den keine T�r ist
	 * @return false falls im S�den eine T�r ist
	 */
	public boolean detectSouth(){
		
		//System.out.println("Er sucht S�den");
		
		
		
		level = currentEntry.length();
		
		if (rootY[level-1] != 24)
		{
			rootY[level] = rootY[level-1] + 1; 
		}
		
		rootX[level] = rootX[level-1];
		//rootY[level] = rootY[level-1] + 1;
		
		refreshRoot();
		
		if (jetzigeReihe == 25 && jetzigeSpalte == 9){
			return false;
		}
		
		//System.out.println("Reihe  : " +jetzigeSpalte +"   Spalte  : " + jetzigeReihe);
		//System.out.println("ist es ein Raum? : " + gui.getKachelArray()[jetzigeReihe][jetzigeSpalte].isIstRaum());
		
		if (gui.getKachelArray()[jetzigeReihe][jetzigeSpalte].isIstRaum()==false){
			
			if (jetzigeReihe == 25 && jetzigeSpalte == 9){
				System.out.println("Cheater");
			}
			return true;
		}

		else if (gui.getKachelArray()[jetzigeReihe][jetzigeSpalte].isIstRaum() &&  gui.getKachelArray()[jetzigeReihe][jetzigeSpalte].isIstTuer()
				&& gui.getKachelArray()[jetzigeReihe][jetzigeSpalte].getOrientierung()==Orientation.N
				)
				{
					System.out.println("hier ist ein T�r : " +jetzigeSpalte +"  " +jetzigeReihe + "  " +Orientation.N);
					letztesMalTuer = true;
					tuerRichtung = "S";
					return false;
				}
		
		else {
			//System.out.println("Hier ist ein Raum  :" + jetzigeSpalte +"  " + jetzigeReihe);
			level = currentEntry.length();;
			return false;
		}
		
				
	}

	/**
	 * Hier wird �berpr�ft ob man sich nach Osten bewegen darf
	 * @return true falls im Osten keine T�r ist
	 * @return false falls im Osten eine T�r ist
	 */
	public boolean detectEast(){
		
		//System.out.println("Er sucht Osten");
		
		level = currentEntry.length();;
		if (rootX[level-1] != 24)
		{
			rootX[level] = rootX[level-1] + 1;
		}
		
		//rootX[level] = rootX[level-1] + 1;
		rootY[level] = rootY[level-1];
		
		refreshRoot();

		//System.out.println("Reihe  : " +jetzigeSpalte +"   Spalte  : " + jetzigeReihe);
		//System.out.println("ist es ein Raum? : " + gui.getKachelArray()[jetzigeReihe][jetzigeSpalte].isIstRaum());
		
		if (gui.getKachelArray()[jetzigeReihe][jetzigeSpalte].isIstRaum()==false){
			return true;
		}
		
		else if (gui.getKachelArray()[jetzigeReihe][jetzigeSpalte].isIstRaum() &&  gui.getKachelArray()[jetzigeReihe][jetzigeSpalte].isIstTuer()
				&& gui.getKachelArray()[jetzigeReihe][jetzigeSpalte].getOrientierung()==Orientation.W
				)
				{
					System.out.println("hier ist eine T�r : " +jetzigeSpalte +"  " +jetzigeReihe+ "  " +Orientation.W);
					letztesMalTuer = true;
					tuerRichtung = "E";
					return false;
				}
		
		else {
			//System.out.println("Hier ist ein Raum  :" + jetzigeSpalte +"  " + jetzigeReihe);
			level = currentEntry.length();;
			return false;
		}
	}

	/**
	 * Hier wird �berpr�ft ob man sich nach Norden bewegen darf
	 * @return true falls im Norden keine T�r ist
	 * @return false falls im Norden eine T�r ist
	 */
	public boolean detectNorth(){

		//System.out.println("Er sucht Norden");
		
		level = currentEntry.length();;
		rootX[level] = rootX[level-1];
		if (rootY[level-1] != 0)
		{
			rootY[level] = rootY[level-1] - 1;
		}
		
		refreshRoot();
		
		//System.out.println("Reihe  : " +jetzigeSpalte +"   Spalte  : " + jetzigeReihe);
		//System.out.println("ist es ein Raum? : " + gui.getKachelArray()[jetzigeReihe][jetzigeSpalte].isIstRaum());
		
		if (gui.getKachelArray()[jetzigeReihe][jetzigeSpalte].isIstRaum()==false){
			return true;
		}

		else if (gui.getKachelArray()[jetzigeReihe][jetzigeSpalte].isIstRaum() &&  gui.getKachelArray()[jetzigeReihe][jetzigeSpalte].isIstTuer()
				&& gui.getKachelArray()[jetzigeReihe][jetzigeSpalte].getOrientierung()==Orientation.S
				)
				{
					System.out.println("hier ist eine T�r : " +jetzigeSpalte +"  " +jetzigeReihe+ "  " +Orientation.S);
					letztesMalTuer = true;
					tuerRichtung = "N";
					return false;
				}
		
		else {
			//System.out.println("Hier ist ein Raum  :" + jetzigeSpalte +"  " + jetzigeReihe);
			level = currentEntry.length();;
			return false;
		}
				
	}

	/**
	 * Hier wird �berpr�ft ob man sich nach Westen bewegen darf
	 * @return true falls im Westen keine T�r ist
	 * @return false falls im Westen eine T�r ist
	 */
	public boolean detectWest(){

		//System.out.println("Er sucht Westen");
		
		level = currentEntry.length();;
		
		if (rootX[level-1] != 0)
		{
			rootX[level] = rootX[level-1] - 1;
		}
		
		

		rootY[level] = rootY[level-1];
		
		refreshRoot();
		
		//System.out.println("Reihe  : " +jetzigeSpalte +"   Spalte  : " + jetzigeReihe);
		//System.out.println("ist es ein Raum? : " + gui.getKachelArray()[jetzigeReihe][jetzigeSpalte].isIstRaum());
		
		if (gui.getKachelArray()[jetzigeReihe][jetzigeSpalte].isIstRaum()==false){
			return true;
		}
		
		else if (gui.getKachelArray()[jetzigeReihe][jetzigeSpalte].isIstRaum() &&  gui.getKachelArray()[jetzigeReihe][jetzigeSpalte].isIstTuer()
				&& gui.getKachelArray()[jetzigeReihe][jetzigeSpalte].getOrientierung()==Orientation.O
				)
				{
					System.out.println("hier ist eine T�r : " +jetzigeSpalte +"  " +jetzigeReihe+ "  " +Orientation.O);
					letztesMalTuer = true;
					tuerRichtung = "W";
					return false;
				}
		

		else {
			//System.out.println("Hier ist ein Raum  :" + jetzigeSpalte +"  " + jetzigeReihe);
			level = currentEntry.length();;
			level = currentEntry.length();;
			return false;
		}
				
	}
	

	/**
	 * Hier wird der liste moeglichkeiten mit Himmelsrichtungen gef�llt.
	 * @param maximaleSchritte die maximale Anzahl an Schritten
	 * @param himmelsrichtungen die zu �berpr�fenden Himmelsrichtungen
	 * @param currentEntry der momentane zu-�berpr�fende Eintrag
	 * @param moeglichkeiten Die Ausgabeliste mit allen M�glichkeiten
	 */
	public void possibleMoves(int maximaleSchritte, char[] himmelsrichtungen, String currentEntryEingabe) {
		
		this.currentEntry = currentEntryEingabe;
		
		this.schritte = maximaleSchritte;
		
		//System.out.println("curr : " + currentEntry);
		//System.out.println("level : " + level);
		
        // Falls wir bei der maximalen Anzahl an Schritten angekommen sind
        if(currentEntry.length() == maximaleSchritte) {
            
            moeglichkeiten[welcheMoeglichkeit] = currentEntry.toCharArray();
            welcheMoeglichkeit++;
            setMoeglichkeiten(moeglichkeiten);
            
            if (currentEntry == "OONNnullnullnull"){
            	System.out.println("juchu!");
            }
            
            
            //for (int i = 1; i<=welcheMoeglichkeit;i++){
            	//System.out.println(currentEntry);
            	//System.out.println(moeglichkeiten[i]);
            //}
            // level reduzieren            
            level = currentEntry.length();;
        }    


        // sonst falls es g�ltige Richtungen gibt weiter diese hinzuf�gen.
        else {
        	for(int i = 0; i < himmelsrichtungen.length; i++) {
            	
            	String oldEntry = currentEntry;
                currentEntry += himmelsrichtungen[i];
                
                if (detectHimmelsrichtung(himmelsrichtungen[i])){
                	
                	possibleMoves(maximaleSchritte,himmelsrichtungen,currentEntry);                	
                    
                }
                else if (!detectHimmelsrichtung(himmelsrichtungen[i]) && letztesMalTuer){
                	//currentEntry += tuerRichtung;
                	tuerRichtung = null;
                	System.out.println("length: " +currentEntry.length());
                	for ( int j = currentEntry.length(); j < maximaleSchritte; j++){
            			currentEntry += "T";
            		}
                	System.out.println("length: " +currentEntry.length());
                	//for (int i = 1; i<=welcheMoeglichkeit;i++){
                		System.out.println(currentEntry);
                    	//System.out.println(moeglichkeiten[i]);
                    //}
                	
                	letztesMalTuer = false;
                	possibleMoves(maximaleSchritte,himmelsrichtungen,currentEntry);
            	
                }
                	currentEntry = oldEntry;
                }
        }
	}

	/**
	 * 
	 * @return die momentan zu-�berpr�fende Reihe
	 */
	public int getjetzigeSpalte() {
		return jetzigeSpalte;
	}

	/**
	 * 
	 * @param jetzigeSpalte setzt die momentan zu-�berpr�fende Reihe
	 */
	public void setjetzigeSpalte(int jetzigeSpalte) {
		this.jetzigeSpalte = jetzigeSpalte;
	}

	/**
	 * 
	 * @return die momentan zu-�berpr�fende Spalte
	 */
	public int getjetzigeReihe() {
		return jetzigeReihe;
	}

	/**
	 * 
	 * @param jetzigeReihe setzt die momentan zu-�berpr�fende Spalte
	 */
	public void setjetzigeReihe(int jetzigeReihe) {
		this.jetzigeReihe = jetzigeReihe;
	}

	/**
	 * Der erste Wert ist der Index der M�glichkeit.
	 * Der zweite Wert ist ein Wert wie "S, O, N, W"
	 * @return das char[][] mit allen M�glichkeiten
	 */
	public char[][] getMoeglichkeiten() {
		return moeglichkeiten;
	}

	/**
	 * Der erste Wert ist der Index der M�glichkeit.
	 * Der zweite Wert ist ein Wert wie "S, O, N, W"
	 * @param moeglichkeiten setzt das char[][] mit allen M�glichkeiten
	 */
	public void setMoeglichkeiten(char[][] moeglichkeiten) {
		this.moeglichkeiten = moeglichkeiten;
	}

	
	
}
