package vielCoolererPathfinder;

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
	private char [][] moeglichkeiten = new char [2440][12];
	private int welcheMoeglichkeit; 
	
	/**
	 * Die momentane Position an gegebenem Baum-Level
	 */
	private int [] rootX = new int [12];
	private int [] rootY = new int [12];
	
	/**
	 * Das momentane Baum-Level
	 */
	private int level;
	
	
	/**
	 * Die Position, von welcher momentan �berpr�ft wird.
	 */
	private int jetzigeReihe;
	private int jetzigeSpalte;
	
	/**
	 * Das Spielfeld und die Ballebene mit welcher gerechnet wird.
	 */
	private GUI gui;
	private BallEbene2 ballEbene;
	
	private String currentEntry;
	
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
	public WahnsinnigTollerPathfinder(GUI gui, BallEbene2 ballEbene){
		
		this.gui = gui;
		this.ballEbene = ballEbene;
		
	}
	
	/**
	 * Leerer Konstruktor, wenn man ohne Parameter einen pathfinder erstellen will.
	 */
	public WahnsinnigTollerPathfinder(){		
	}
	
	/**
	 * Hauptmethode, mit welcher die ganzen checks aufgerufen werden.
	 */
	public void findThatPathBetter(){

        // Die Werte werden auf die Urpsrungsposition gesetzt.
        reset(2,1);
        ausgangsPosition(jetzigeSpalte, jetzigeReihe);
        possibleMoves(1, himmelsrichtungen,"");
	}
		
	/**
	 * Hier wird die momentane Position (y,x) gesetzt.
	 * @param reihe hierauf wird die jetzigeReihe gesetzt
	 * @param spalte hierauf wird die jetzigeSpalte gesetzt
	 */
	public void reset(int reihe, int spalte){
		jetzigeReihe = reihe;
		jetzigeSpalte = spalte;
	}

	/**
	 * Hier wird 
	 * @param jetzigeReihe auf @param rootY[level] gesetzt 
	 * @param jetzigeSpalte auf @param rootX[level] gesetzt
	 */
	public void refreshRoot(){
		jetzigeReihe = rootY[level];
		jetzigeSpalte = rootX[level];
	}
	
	/**
	 * Hier werden die kompletten root Werte auf die Eingaben gesetzt
	 * @param eingabeSpalte bestimmt alle @param rootX -Werte
	 * @param eingabeReihe bestimmt alle @param rootY -Werte
	 */
	public void ausgangsPosition(int eingabeSpalte, int eingabeReihe){
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
		
		System.out.println("rootX[level] : " + rootX[level]);
		System.out.println("rootY[level] : " + rootY[level]);
		
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
			System.out.println("ung�ltige Himmelsrichtung");
			return false;
		}
	}
	
	/**
	 * Hier wird �berpr�ft ob man sich nach S�den bewegen darf
	 * @return true falls im S�den keine T�r ist
	 * @return false falls im S�den eine T�r ist
	 */
	public boolean detectSouth(){
		
		System.out.println("Er sucht S�den");
		
		level = currentEntry.length();;		
		rootX[level] = rootX[level-1];
		rootY[level] = rootY[level-1] + 1;
		
		refreshRoot();
		
		System.out.println("Reihe  : " +jetzigeReihe +"   Spalte  : " + jetzigeSpalte);
		System.out.println("ist es ein Raum? : " + gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstRaum());
		
		if (gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstRaum()==false){
			return true;
		}

		else if (gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstRaum() &&  gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstTuer()
				&& gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].getOrientierung()==Orientation.S)
				{
					System.out.println("hier ist ein T�r");
					return false;
				}
		
		else {
			System.out.println("Hier ist ein Raum  :" + jetzigeReihe +"  " + jetzigeSpalte);
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
		
		System.out.println("Er sucht Osten");
		
		level = currentEntry.length();;
		rootX[level] = rootX[level-1] + 1;
		rootY[level] = rootY[level-1];
		
		refreshRoot();

		System.out.println("Reihe  : " +jetzigeReihe +"   Spalte  : " + jetzigeSpalte);
		System.out.println("ist es ein Raum? : " + gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstRaum());
		
		if (gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstRaum()==false){
			return true;
		}
		
		else if (gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstRaum() &&  gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstTuer()
				&& gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].getOrientierung()==Orientation.O)
				{
					System.out.println("hier ist eine T�r");
					return false;
				}
		
		else {
			System.out.println("Hier ist ein Raum  :" + jetzigeReihe +"  " + jetzigeSpalte);
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

		System.out.println("Er sucht Norden");
		
		level = currentEntry.length();;
		rootX[level] = rootX[level-1];
		rootY[level] = rootY[level-1] - 1;
		
		refreshRoot();
		
		System.out.println("Reihe  : " +jetzigeReihe +"   Spalte  : " + jetzigeSpalte);
		System.out.println("ist es ein Raum? : " + gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstRaum());
		
		if (gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstRaum()==false){
			return true;
		}

		else if (gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstRaum() &&  gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstTuer()
				&& gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].getOrientierung()==Orientation.N)
				{
					System.out.println("hier ist eine T�r");
					return false;
				}
		
		else {
			System.out.println("Hier ist ein Raum  :" + jetzigeReihe +"  " + jetzigeSpalte);
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

		
		System.out.println("Er sucht Westen");
		
		level = currentEntry.length();;
		rootX[level] = rootX[level-1] - 1;
		rootY[level] = rootY[level-1];
		
		refreshRoot();
		
		System.out.println("Reihe  : " +jetzigeReihe +"   Spalte  : " + jetzigeSpalte);
		System.out.println("ist es ein Raum? : " + gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstRaum());
		
		if (gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstRaum()==false){
			return true;
		}
		
		else if (gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstRaum() &&  gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstTuer()
				&& gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].getOrientierung()==Orientation.W)
				{
					System.out.println("hier ist eine T�r");
					return false;
				}
		

		else {
			System.out.println("Hier ist ein Raum  :" + jetzigeReihe +"  " + jetzigeSpalte);
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
		
		System.out.println("curr : " + currentEntry);
		System.out.println("level : " + level);
		
        // Falls wir bei der maximalen Anzahl an Schritten angekommen sind
        if(currentEntry.length() == maximaleSchritte) {
            
            moeglichkeiten[welcheMoeglichkeit] = currentEntry.toCharArray();
            welcheMoeglichkeit++;
            setMoeglichkeiten(moeglichkeiten);
            
            for (int i = 1; i<=welcheMoeglichkeit;i++){
            	System.out.println(moeglichkeiten[i]);
            }
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
                	currentEntry = oldEntry;
                }
        }
	}

	/**
	 * 
	 * @return die momentan zu-�berpr�fende Reihe
	 */
	public int getJetzigeReihe() {
		return jetzigeReihe;
	}

	/**
	 * 
	 * @param jetzigeReihe setzt die momentan zu-�berpr�fende Reihe
	 */
	public void setJetzigeReihe(int jetzigeReihe) {
		this.jetzigeReihe = jetzigeReihe;
	}

	/**
	 * 
	 * @return die momentan zu-�berpr�fende Spalte
	 */
	public int getJetzigeSpalte() {
		return jetzigeSpalte;
	}

	/**
	 * 
	 * @param jetzigeSpalte setzt die momentan zu-�berpr�fende Spalte
	 */
	public void setJetzigeSpalte(int jetzigeSpalte) {
		this.jetzigeSpalte = jetzigeSpalte;
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
