package finderOfPaths;

import kacheln.Kachel;
import kacheln.KachelContainer;
import kommunikation.PlayerCircleManager;
import staticClasses.auxx;
import cluedoNetworkLayer.CluedoPlayer;
import enums.Orientation;

/**
 * @since 10.06.2015
 * @version 21.07.2015
 * @author Benedikt Mayer, Maximilian Lammel
 *
 *	Hier werden alle Moeglichkeiten berechnet, von der momentanen Position aus
 * sich zu bewegen in die entsprechenden Himmelsrichtungen. 
 */
public class WahnsinnigTollerPathfinder {

	/**
	 * Alle Moeglichkeiten, welche an Wegen ausgegeben werden.
	 */
	private char [][] moeglichkeiten = new char [1000][12];
	private int welcheMoeglichkeit; 
	
	private int [] xCheck = new int [144];
	private int [] yCheck = new int [144];
	private int checkIndex;
	
	/**
	 * Die wichtigste Variable des Spiels
	 */
	private char hans;
	
	private Kachel [] suchKacheln = new Kachel [4];
	private int tuerCounter;
	
	private boolean letztesMalTuer;
	private int welcheKachel;
	
	private String tuerRichtung;
	
	private int [] xPositionen = new int [4];
	private int [] yPositionen = new int [4];
	
	private char [][][] mehrereMoeglichkeiten = new char [4][1000][12];
	
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
	 * Der momentane String mit Anweisungen
	 */
	private String currentEntry;
	
	/**
	 * Der Model-Spieler
	 */
	
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
    
    public PlayerCircleManager pcManager;
    
    /**
     * Kacheln un' so.
     */
    private KachelContainer kacheln;
    
    /**
     * Der unglaublich tolle, atemberaubende Pathfinder
     * @param pcManager kuchen
     * @param kacheln unsere schoenen Kacheln, sprich Spielfelder
     */
	public WahnsinnigTollerPathfinder(PlayerCircleManager pcManager, KachelContainer kacheln){
		this.pcManager = pcManager;
		this.kacheln = kacheln;
		
	}
	
	/**
	 * Hauptmethode, mit welcher die ganzen checks aufgerufen werden.
	 * THE SEARCH HAS BEGUN!
	 */
	public void findThatPathBetter(int wuerfelZahl, PlayerCircleManager pcManager){
		this.pcManager = pcManager;
		
		auxx.logsevere("pathfinder.findThatPathBetter");
		auxx.logsevere("pcManager.getCurrentPlayer() Color : " +pcManager.getCurrentPlayer().getCluedoPerson().getColor());
		auxx.logsevere("pcManager.getCurrentPlayer() x : " +pcManager.getCurrentPlayer().getPosition().getX() + "  ||  y : " +pcManager.getCurrentPlayer().getPosition().getY());
		
		refreshChecks();

        // Die Werte werden auf die Urpsrungsposition gesetzt.
		reset(pcManager.getCurrentPlayer().getPosition().getY(),pcManager.getCurrentPlayer().getPosition().getX());
        
        checkForRoom();
        
        if (kacheln.getKacheln()[jetzigeReihe][jetzigeSpalte].isIstRaum()){
	        for (welcheKachel = 0; welcheKachel < tuerCounter; welcheKachel++){
	        	
	        	refreshChecks();
	        	
	        	Kachel momentaneKachel = suchKacheln[welcheKachel];
	        	
	        	pcManager.getCurrentPlayer().getPosition().setY(momentaneKachel.getPosition().getY());
	        	pcManager.getCurrentPlayer().getPosition().setX(momentaneKachel.getPosition().getX());
	        	
	        	xPositionen[welcheKachel] = pcManager.getCurrentPlayer().getPosition().getX();
	        	yPositionen[welcheKachel] = pcManager.getCurrentPlayer().getPosition().getY();
	        	
	        	jetzigeReihe = pcManager.getCurrentPlayer().getPosition().getY();
	        	jetzigeSpalte = pcManager.getCurrentPlayer().getPosition().getX();
	        	
	        	System.out.println((welcheKachel+1) +". Durchgang" +" <<<>>> player y : " +pcManager.getCurrentPlayer().getPosition().getY() +"   ||   x : " +pcManager.getCurrentPlayer().getPosition().getX());
	        	
	        	ausgangsPosition(jetzigeReihe, jetzigeSpalte);
	            
	            currentEntry = null;
	            
	            possibleMoves(wuerfelZahl, himmelsrichtungen,"");
	        }
	    }
        
        else {
        	ausgangsPosition(jetzigeReihe, jetzigeSpalte);
        	currentEntry = null;
        	possibleMoves(wuerfelZahl, himmelsrichtungen,"");
        }
        
	}

	/**
	 * Hier werden die detect-Methoden aufgerufen und gecheckt ob der move legal ist.
	 * @param richtung gibt die zu ueberpruefende Himmelsrichtung an
	 * @return true wenn die input-Richtung erlaubt ist.
	 * @return false wenn die input-Richtung verboten ist.
	 */
	public boolean detectHimmelsrichtung(char richtung){
		
		level = currentEntry.length();
		
		if (richtung == 'S' && jetzigeReihe < 25){
			return detectSouth();
		}
		
		else if (richtung == 'E' && jetzigeSpalte < 25){
			return detectEast();
		}
		
		else if (richtung == 'N' && jetzigeReihe > 0){
			return detectNorth();
		}
		
		else if (richtung == 'W' && jetzigeSpalte > 0){
			return detectWest();
		}
		
		else {
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
		
        // Falls wir bei der maximalen Anzahl an Schritten angekommen sind
        if(currentEntry.length() == maximaleSchritte) {
            if (checkThatPlace(currentEntry)){
            	moeglichkeiten[welcheMoeglichkeit] = currentEntry.toCharArray();
                if (kacheln.getKacheln()[jetzigeReihe][jetzigeSpalte].isIstRaum()){
                	mehrereMoeglichkeiten[welcheKachel] = moeglichkeiten;
                	setMehrereMoeglichkeiten(mehrereMoeglichkeiten);
                }                
                welcheMoeglichkeit++;
                setMoeglichkeiten(moeglichkeiten);
            }
            else {
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
                else if (!detectHimmelsrichtung(himmelsrichtungen[i]) && letztesMalTuer){
                	tuerRichtung = null;
                	for ( int j = currentEntry.length(); j < maximaleSchritte; j++){
            			currentEntry += "T";
            		}                	
                	letztesMalTuer = false;
                	possibleMoves(maximaleSchritte,himmelsrichtungen,currentEntry);
            	
                }
                	currentEntry = oldEntry;
                }
        }
	}

	/**
	 * Hier wird ueberprueft ob man sich nach Sueden bewegen darf
	 * @return true falls im Sueden keine Tuer ist
	 * @return false falls im Sueden eine Tuer ist
	 */
	public boolean detectSouth(){
		
		level = currentEntry.length();
		
		if (rootY[level-1] != 24)
		{
			rootY[level] = rootY[level-1] + 1; 
		}
		
		rootX[level] = rootX[level-1];
		
		refreshRoot();
		
		if (jetzigeReihe == 25 && jetzigeSpalte == 9){
			return false;
		}
		
		if ( !kacheln.getKacheln()[jetzigeReihe][jetzigeSpalte].isIstRaum() && !detectPlayer(jetzigeReihe, jetzigeSpalte)){
			
			return true;
		}

		else if (kacheln.getKacheln()[jetzigeReihe][jetzigeSpalte].isIstRaum() &&  kacheln.getKacheln()[jetzigeReihe][jetzigeSpalte].isIstTuer()
				&& kacheln.getKacheln()[jetzigeReihe][jetzigeSpalte].getOrientierung()==Orientation.N){
					letztesMalTuer = true;
					tuerRichtung = "S";
					return false;
				}
		
		else {
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
		
		level = currentEntry.length();;
		if (rootX[level-1] != 24)
		{
			rootX[level] = rootX[level-1] + 1;
		}
		
		rootY[level] = rootY[level-1];
		
		refreshRoot();

		if ( !kacheln.getKacheln()[jetzigeReihe][jetzigeSpalte].isIstRaum() && !detectPlayer(jetzigeReihe, jetzigeSpalte)){
			return true;
		}
		
		else if (kacheln.getKacheln()[jetzigeReihe][jetzigeSpalte].isIstRaum() &&  kacheln.getKacheln()[jetzigeReihe][jetzigeSpalte].isIstTuer()
				&& kacheln.getKacheln()[jetzigeReihe][jetzigeSpalte].getOrientierung()==Orientation.W){
					letztesMalTuer = true;
					tuerRichtung = "E";
					return false;
				}
		
		else {
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

		level = currentEntry.length();;
		rootX[level] = rootX[level-1];
		if (rootY[level-1] != 0)
		{
			rootY[level] = rootY[level-1] - 1;
		}
		
		refreshRoot();
		
		if ( !kacheln.getKacheln()[jetzigeReihe][jetzigeSpalte].isIstRaum() && !detectPlayer(jetzigeReihe, jetzigeSpalte)){
			return true;
		}

		else if (kacheln.getKacheln()[jetzigeReihe][jetzigeSpalte].isIstRaum() &&  kacheln.getKacheln()[jetzigeReihe][jetzigeSpalte].isIstTuer()
				&& kacheln.getKacheln()[jetzigeReihe][jetzigeSpalte].getOrientierung()==Orientation.S){
					letztesMalTuer = true;
					tuerRichtung = "N";
					return false;
				}
		
		else {
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

		level = currentEntry.length();;
		
		if (rootX[level-1] != 0)
		{
			rootX[level] = rootX[level-1] - 1;
		}

		rootY[level] = rootY[level-1];
		
		refreshRoot();
		
		if ( !kacheln.getKacheln()[jetzigeReihe][jetzigeSpalte].isIstRaum() && !detectPlayer(jetzigeReihe, jetzigeSpalte)){
			return true;
		}
		
		else if (kacheln.getKacheln()[jetzigeReihe][jetzigeSpalte].isIstRaum() &&  kacheln.getKacheln()[jetzigeReihe][jetzigeSpalte].isIstTuer()
				&& kacheln.getKacheln()[jetzigeReihe][jetzigeSpalte].getOrientierung()==Orientation.O){
					letztesMalTuer = true;
					tuerRichtung = "W";
					return false;
				}
		
		else {
			level = currentEntry.length();;
			level = currentEntry.length();;
			return false;
		}
				
	}
	
	
	/**
	 * @param entry zu ueberpruefender eintrag
	 * @return true, falls man hier hin kann, false sonst
	 */
	public boolean checkThatPlace(String entry){
		char [] entries = entry.toCharArray();
		
		int xSaved = 0;
		int ySaved = 0;
		
		for (int i = 0; i < entries.length; i++){
			if (entries[i] == 'W'){
				xSaved--;
			}
			else if (entries[i] == 'E'){
				xSaved++;
			}
			else if (entries[i] == 'N'){
				ySaved--;
			}
			else if (entries[i] == 'S'){
				ySaved++;
			}
		}
		
		for (int x = 0; x < xCheck.length; x++){
			if (xCheck[x] == xSaved && yCheck[x] == ySaved){
				return false;
			}
		}
		
			xCheck[checkIndex] = xSaved;
			yCheck[checkIndex] = ySaved;
			checkIndex++;
		return true;
		
		
		
	}
	
	/**
	 * 
	 * @param reihe zu ueberpruefende Reihe
	 * @param spalte zu ueberpruefende Spalte
	 * @return true wenn ein Spieler im Weg ist.
	 */
	public boolean detectPlayer(int reihe, int spalte){
		int size = pcManager.getSize();
		for (int spieler = 0; spieler < size; spieler++){
			CluedoPlayer momentanerPlayer = pcManager.getPlayerByIndex(spieler);
			if (momentanerPlayer.getPosition().getX() == spalte && momentanerPlayer.getPosition().getY() == reihe){
				return true;
			}
		}
		return false;
		
	}
	
	/**
	 * ueberprueft ob an der momentanen Reihe/Spalte ein Raum ist bzw. moeglicherweise eine Tuer
	 * Setzt den tuerCounter und die suchKacheln
	 */
	public void checkForRoom(){
		if (kacheln.getKacheln()[jetzigeReihe][jetzigeSpalte].isIstRaum()){
	
			int welcheKachel = 0;
	
			for (int iReihen = 0; iReihen < kacheln.getKacheln().length; iReihen++){
				for (int jSpalten = 0; jSpalten < kacheln.getKacheln()[iReihen].length; jSpalten++){
						
					Kachel momentanteKachel = kacheln.getKacheln()[jetzigeReihe][jetzigeSpalte];
					Kachel vergleichsKachel = kacheln.getKacheln()[iReihen][jSpalten];
						
					if (momentanteKachel.getRaum() == vergleichsKachel.getRaum() && vergleichsKachel.isIstTuer()){
						suchKacheln[welcheKachel] = vergleichsKachel;
						welcheKachel++;
						tuerCounter++;
					}
				}
			}
		}
	}
	
	/**
	 * Hier wird die momentane Position (y,x) gesetzt.
	 * @param reihe hierauf wird die jetzigeSpalte gesetzt
	 * @param spalte hierauf wird die jetzigeReihe gesetzt
	 */
	public void reset(int reihe, int spalte){
		
		jetzigeReihe = reihe;
		jetzigeSpalte = spalte;
		welcheMoeglichkeit = 0;
		
		for (int i = 0; i < moeglichkeiten.length; i++){
			for (int j = 0; j < moeglichkeiten[i].length; j++){
				moeglichkeiten[i][j] = hans;
				for (int hamana = 0; hamana < mehrereMoeglichkeiten.length; hamana++){
					mehrereMoeglichkeiten[hamana][i][j] = hans;
				}
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

	
	public void refreshChecks(){
		for (int x = 0; x < xCheck.length; x++){
			for (int y = 0; y < yCheck.length; y++){
				xCheck[x] = 200;
				yCheck[y] = 200;
			}			
		}
		checkIndex = 0;

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

	public char[][][] getMehrereMoeglichkeiten() {
		return mehrereMoeglichkeiten;
	}

	public void setMehrereMoeglichkeiten(char[][][] mehrereMoeglichkeiten) {
		this.mehrereMoeglichkeiten = mehrereMoeglichkeiten;
	}

	public int getTuerCounter() {
		return tuerCounter;
	}

	public void setTuerCounter(int tuerCounter) {
		this.tuerCounter = tuerCounter;
	}

	public int[] getxPositionen() {
		return xPositionen;
	}

	public void setxPositionen(int[] xPositionen) {
		this.xPositionen = xPositionen;
	}

	public int[] getyPositionen() {
		return yPositionen;
	}

	public void setyPositionen(int[] yPositionen) {
		this.yPositionen = yPositionen;
	}

	public int getWelcheKachel() {
		return welcheKachel;
	}

	public void setWelcheKachel(int welcheKachel) {
		this.welcheKachel = welcheKachel;
	}
}