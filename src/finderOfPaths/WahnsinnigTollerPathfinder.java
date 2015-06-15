package finderOfPaths;

import model.Player;
import view.BoardView;
import enums.Orientation;

/**
 * @since 10.06.2015
 * @version 13.06.2015
 * @author Benedikt Mayer, Maximilian Lammel
 *
 *	Hier werden alle Möglichkeiten berechnet, von der momentanen Position aus
 * sich zu bewegen in die entsprechenden Himmelsrichtungen. 
 */
public class WahnsinnigTollerPathfinder {

	/**
	 * Alle Möglichkeiten, welche an Wegen ausgegeben werden.
	 */
	private char [][] moeglichkeiten = new char [244000][12];
	private int welcheMoeglichkeit; 
	
	private char hans;
	
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
	 * Die Position, von welcher momentan überprüft wird.
	 */
	private int jetzigeReihe;
	private int jetzigeSpalte;
	
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
	 * Konstruktor für den pathfinder, welcher alle möglichen Wege berechnet.
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
        //System.out.println("jetzt x "+jetzigeSpalte);
        //System.out.println("jetzt y "+jetzigeReihe);
        ausgangsPosition(jetzigeSpalte, jetzigeReihe);
        currentEntry = null;
        
        possibleMoves(wuerfelZahl, himmelsrichtungen,"");
	}
		
	/**
	 * Hier wird die momentane Position (y,x) gesetzt.
	 * @param reihe hierauf wird die jetzigeReihe gesetzt
	 * @param spalte hierauf wird die jetzigeSpalte gesetzt
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
	 * @param richtung gibt die zu überprüfende Himmelsrichtung an
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
			//System.out.println("ungültige Himmelsrichtung");
			return false;
		}
	}
	
	/**
	 * Hier wird überprüft ob man sich nach Süden bewegen darf
	 * @return true falls im Süden keine Tür ist
	 * @return false falls im Süden eine Tür ist
	 */
	public boolean detectSouth(){
		
		//System.out.println("Er sucht Süden");
		
		level = currentEntry.length();
		
		if (rootY[level-1] != 24)
		{
			rootY[level] = rootY[level-1] + 1;
		}
		
		rootX[level] = rootX[level-1];
		//rootY[level] = rootY[level-1] + 1;
		
		refreshRoot();
		
		//System.out.println("Reihe  : " +jetzigeReihe +"   Spalte  : " + jetzigeSpalte);
		//System.out.println("ist es ein Raum? : " + gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstRaum());
		
		if (gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstRaum()==false){
			return true;
		}

		else if (gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstRaum() &&  gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstTuer()
				&& gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].getOrientierung()==Orientation.S)
				{
					//System.out.println("hier ist ein Tür");
					return false;
				}
		
		else {
			//System.out.println("Hier ist ein Raum  :" + jetzigeReihe +"  " + jetzigeSpalte);
			level = currentEntry.length();;
			return false;
		}
		
				
	}

	/**
	 * Hier wird überprüft ob man sich nach Osten bewegen darf
	 * @return true falls im Osten keine Tür ist
	 * @return false falls im Osten eine Tür ist
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

		//System.out.println("Reihe  : " +jetzigeReihe +"   Spalte  : " + jetzigeSpalte);
		//System.out.println("ist es ein Raum? : " + gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstRaum());
		
		if (gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstRaum()==false){
			return true;
		}
		
		else if (gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstRaum() &&  gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstTuer()
				&& gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].getOrientierung()==Orientation.O)
				{
					//System.out.println("hier ist eine Tür");
					return false;
				}
		
		else {
			//System.out.println("Hier ist ein Raum  :" + jetzigeReihe +"  " + jetzigeSpalte);
			level = currentEntry.length();;
			return false;
		}
	}

	/**
	 * Hier wird überprüft ob man sich nach Norden bewegen darf
	 * @return true falls im Norden keine Tür ist
	 * @return false falls im Norden eine Tür ist
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
		
		//System.out.println("Reihe  : " +jetzigeReihe +"   Spalte  : " + jetzigeSpalte);
		//System.out.println("ist es ein Raum? : " + gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstRaum());
		
		if (gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstRaum()==false){
			return true;
		}

		else if (gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstRaum() &&  gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstTuer()
				&& gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].getOrientierung()==Orientation.N)
				{
					//System.out.println("hier ist eine Tür");
					return false;
				}
		
		else {
			//System.out.println("Hier ist ein Raum  :" + jetzigeReihe +"  " + jetzigeSpalte);
			level = currentEntry.length();;
			return false;
		}
				
	}

	/**
	 * Hier wird überprüft ob man sich nach Westen bewegen darf
	 * @return true falls im Westen keine Tür ist
	 * @return false falls im Westen eine Tür ist
	 */
	public boolean detectWest(){

		
		//System.out.println("Er sucht Westen");
		
		level = currentEntry.length();;
		
		if (rootX[level-1] != 0)
		{
			rootX[level] = rootX[level-1] - 1;
		}
		
		
		//rootX[level] = rootX[level-1] - 1;
		rootY[level] = rootY[level-1];
		
		refreshRoot();
		
		//System.out.println("Reihe  : " +jetzigeReihe +"   Spalte  : " + jetzigeSpalte);
		//System.out.println("ist es ein Raum? : " + gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstRaum());
		
		if (gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstRaum()==false){
			return true;
		}
		
		else if (gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstRaum() &&  gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstTuer()
				&& gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].getOrientierung()==Orientation.W)
				{
					//System.out.println("hier ist eine Tür");
					return false;
				}
		

		else {
			//System.out.println("Hier ist ein Raum  :" + jetzigeReihe +"  " + jetzigeSpalte);
			level = currentEntry.length();;
			level = currentEntry.length();;
			return false;
		}
				
	}
	

	/**
	 * Hier wird der liste moeglichkeiten mit Himmelsrichtungen gefüllt.
	 * @param maximaleSchritte die maximale Anzahl an Schritten
	 * @param himmelsrichtungen die zu überprüfenden Himmelsrichtungen
	 * @param currentEntry der momentane zu-überprüfende Eintrag
	 * @param moeglichkeiten Die Ausgabeliste mit allen Möglichkeiten
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
            
            for (int i = 1; i<=welcheMoeglichkeit;i++){
            	//System.out.println(currentEntry);
            	//System.out.println(moeglichkeiten[i]);
            }
            // level reduzieren            
            level = currentEntry.length();;
        }    


        // sonst falls es gültige Richtungen gibt weiter diese hinzufügen.
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
	 * @return die momentan zu-überprüfende Reihe
	 */
	public int getJetzigeReihe() {
		return jetzigeReihe;
	}

	/**
	 * 
	 * @param jetzigeReihe setzt die momentan zu-überprüfende Reihe
	 */
	public void setJetzigeReihe(int jetzigeReihe) {
		this.jetzigeReihe = jetzigeReihe;
	}

	/**
	 * 
	 * @return die momentan zu-überprüfende Spalte
	 */
	public int getJetzigeSpalte() {
		return jetzigeSpalte;
	}

	/**
	 * 
	 * @param jetzigeSpalte setzt die momentan zu-überprüfende Spalte
	 */
	public void setJetzigeSpalte(int jetzigeSpalte) {
		this.jetzigeSpalte = jetzigeSpalte;
	}

	/**
	 * Der erste Wert ist der Index der Möglichkeit.
	 * Der zweite Wert ist ein Wert wie "S, O, N, W"
	 * @return das char[][] mit allen Möglichkeiten
	 */
	public char[][] getMoeglichkeiten() {
		return moeglichkeiten;
	}

	/**
	 * Der erste Wert ist der Index der Möglichkeit.
	 * Der zweite Wert ist ein Wert wie "S, O, N, W"
	 * @param moeglichkeiten setzt das char[][] mit allen Möglichkeiten
	 */
	public void setMoeglichkeiten(char[][] moeglichkeiten) {
		this.moeglichkeiten = moeglichkeiten;
	}

	
	
}
