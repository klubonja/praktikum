package vielCoolererPathfinder;

import java.util.ArrayList;

import kacheln.Kachel;

public class WahnsinnigTollerPathfinder {

	private char [] anweisungen = new char [12];
	
	private char [][] moeglichkeiten = new char [244000][12];
	private int welcheMoeglichkeit; 
	
	private int [] rootX = new int [12];
	private int [] rootY = new int [12];
	
	private int level;
	
	private Kachel jetzigeKachel;
	
	private int jetzigeReihe;
	private int jetzigeSpalte;
	
	private GUI gui;
	private BallEbene ballEbene;
	
	private int jetzigeAnweisung;
	
	private int distanz;
	
	public int south;
	public int east;
	public int north;
	public int west;
	
	public WahnsinnigTollerPathfinder(GUI gui, BallEbene ballEbene){
		
		this.gui = gui;
		this.ballEbene = ballEbene;
		
		findThatPathBetter();
		
	}
	
	public WahnsinnigTollerPathfinder(){		
	}
	
	public void findThatPathBetter(){

		
		jetzigeAnweisung = 0;
		
		for (int i=0; i < anweisungen.length;i++){
			anweisungen[i] = ' ';
		}
		
		// Create an alphabet to work with
        char[] alphabet = new char[] {'S','E', 'N', 'W'};
        // Find all possible combinations of this alphabet in the string size of 3
        reset();
        ausgangsPosition(jetzigeSpalte, jetzigeReihe);
        possibleMoves(2, alphabet,"");
	}
	
	public void reset(){
		jetzigeReihe = 2;
		jetzigeSpalte = 1;
	}

	
	public void refreshPosition(Kachel dieseKachel){
		jetzigeReihe = gui.getRowIndex(dieseKachel);
		jetzigeSpalte = gui.getColumnIndex(dieseKachel);
	}
	
	public void refreshRoot(){
		jetzigeReihe = rootY[level];
		jetzigeSpalte = rootX[level];
	}
	
	
	public void ausgangsPosition(int eingabeSpalte, int eingabeReihe){
		level = 0;
		
		for (int i = 0; i < rootX.length; i++){
			rootX[i] = eingabeSpalte;
			rootY[i] = eingabeReihe;
		}		
		
	}
	
	public boolean detectHimmelsrichtung(char richtung){
		
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
			System.out.println("ungültige Himmelsrichtung");
			return false;
		}
	}
	
	
	public boolean detectSouth(){
		
		System.out.println("Er sucht Süden");
		
		level++;		
		rootX[level] = rootX[level-1];
		rootY[level] = rootY[level-1] + 1;
		
		refreshRoot();
		System.out.println("Reihe  : " +jetzigeReihe +"   Spalte  : " + jetzigeSpalte);
		
		System.out.println("ist es ein Raum? : " + gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstRaum());
		
		if (gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstRaum()==false){
			return true;
			
		}

		else {
			System.out.println("Hier ist ein Raum  :" + jetzigeReihe +"  " + jetzigeSpalte);
			level--;
			return false;
			
		}
		
				
	}
	
	public boolean detectEast(){
		
		
		
		System.out.println("Er sucht Osten");
		
		level++;
		rootX[level] = rootX[level-1] + 1;
		rootY[level] = rootY[level-1];
		
		refreshRoot();

		System.out.println("Reihe  : " +jetzigeReihe +"   Spalte  : " + jetzigeSpalte);
		
		
		System.out.println("ist es ein Raum? : " + gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstRaum());
		
		
		if (gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstRaum()==false){
			return true;
		}
		else {
			System.out.println("Hier ist ein Raum  :" + jetzigeReihe +"  " + jetzigeSpalte);
			level--;
			return false;
		}
	}
	
	public boolean detectNorth(){

		System.out.println("Er sucht Norden");
		
		level++;
		rootX[level] = rootX[level-1];
		rootY[level] = rootY[level-1] - 1;
		
		refreshRoot();
		
		System.out.println("Reihe  : " +jetzigeReihe +"   Spalte  : " + jetzigeSpalte);
		
		
		System.out.println("ist es ein Raum? : " + gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstRaum());
		
		if (gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstRaum()==false){
			return true;
		}

		else {
			System.out.println("Hier ist ein Raum  :" + jetzigeReihe +"  " + jetzigeSpalte);
			level--;
			return false;
		}
				
	}
	
	public boolean detectWest(){

		System.out.println("Er sucht Westen");
		
		level++;
		rootX[level] = rootX[level-1] - 1;
		rootY[level] = rootY[level-1];
		
		refreshRoot();
		
		System.out.println("Reihe  : " +jetzigeReihe +"   Spalte  : " + jetzigeSpalte);
		
		System.out.println("ist es ein Raum? : " + gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstRaum());
		
		if (gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstRaum()==false){
			return true;
		}

		else {
			System.out.println("Hier ist ein Raum  :" + jetzigeReihe +"  " + jetzigeSpalte);
			level--;
			level--;
			return false;
		}
				
	}
	

	
	public void possibleMoves(int maxLength, char[] alphabet, String curr) {
		
		System.out.println("curr : " + curr);
		
		System.out.println("level : " + level);
		
        // If the current string has reached it's maximum length
        if(curr.length() == maxLength) {
            //System.out.println(curr);
            welcheMoeglichkeit++;
            moeglichkeiten[welcheMoeglichkeit] = curr.toCharArray();
            
            for (int i = 1; i<=welcheMoeglichkeit;i++){
            	System.out.println(moeglichkeiten[i]);
            }
            while (curr.toCharArray()[level-1] == 'W'){
            	level--;
            }
            
            level--;
            
            // falls curr[level] == Westen, dann gehe solange zurück (level reduzieren)
            // bis curr[level] != Westen.
            
            // level reduzieren

        // Else add each letter from the alphabet to new strings and process these new strings again
        } else {
            for(int i = 0; i < alphabet.length; i++) {
            	
            	String oldCurr = curr;
                curr += alphabet[i];
                if (detectHimmelsrichtung(alphabet[i])){
                	possibleMoves(maxLength,alphabet,curr);
                    
                }
                	curr = oldCurr;
                }
        }
	}


	
	
	
	
	
	
	
	
	/*
	public void findThatPath(){
		
		
		
		distanz = 2;		
jetzigeAnweisung = 0;
		
		for (int i=0; i < anweisungen.length;i++){
			anweisungen[i] = ' ';
		}
		
		

		
		while (distanz > 0){
			detectSouth();
			distanz--;
			
		}
		//System.out.println("Bin ich ein Raum?  " +gui.getKachelArray()[0][2].isIstRaum());
		
		for (int zähler = 0; zähler < anweisungen.length;zähler++){
			System.out.println(anweisungen[zähler]);
		}
		
		System.out.println("Reihe  : " +jetzigeReihe +"   Spalte  : " + jetzigeSpalte);
		
	}
	
	*/
	
	
	public int getJetzigeReihe() {
		return jetzigeReihe;
	}

	public void setJetzigeReihe(int jetzigeReihe) {
		this.jetzigeReihe = jetzigeReihe;
	}

	public int getJetzigeSpalte() {
		return jetzigeSpalte;
	}

	public void setJetzigeSpalte(int jetzigeSpalte) {
		this.jetzigeSpalte = jetzigeSpalte;
	}

	public char[][] getMoeglichkeiten() {
		return moeglichkeiten;
	}

	public void setMoeglichkeiten(char[][] moeglichkeiten) {
		this.moeglichkeiten = moeglichkeiten;
	}

	
	
}
