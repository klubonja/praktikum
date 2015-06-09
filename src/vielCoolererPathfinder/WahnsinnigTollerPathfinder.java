package vielCoolererPathfinder;

import kacheln.Kachel;

public class WahnsinnigTollerPathfinder {

	char [] anweisungen = new char [12];
	
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
		
		findThatPath();
		
	}
	
	public void findThatPath(){
		
		jetzigeAnweisung = 0;
		
		distanz = 2;
		
		
		
		
		
		for (int i=0; i < anweisungen.length;i++){
			anweisungen[i] = ' ';
		}
		
		jetzigeReihe = 0;
		jetzigeSpalte = 0;
		detectSouth();
		detectEast();
		detectEast();
		//detectSouth();
		
		// System.out.println("Bin ich ein Raum?  " +gui.getKachelArray()[0][2].isIstRaum());
		
		for (int zähler = 0; zähler < anweisungen.length;zähler++){
			System.out.println(anweisungen[zähler]);
		}
		
		System.out.println("Reihe  : " +jetzigeReihe +"   Spalte  : " + jetzigeSpalte);
		
	}
	
	public void refreshPosition(Kachel dieseKachel){
		jetzigeReihe = gui.getRowIndex(dieseKachel);
		jetzigeSpalte = gui.getColumnIndex(dieseKachel);
	}
	
	public void detectSouth(){
		jetzigeReihe++;
		System.out.println("Reihe  : " +jetzigeReihe +"   Spalte  : " + jetzigeSpalte);
		System.out.println("jetzigeAnweisung  : " + jetzigeAnweisung);
		
		System.out.println("ist es ein Raum? : " + gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstRaum());
		
		if (gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstRaum()==false){
			anweisungen[jetzigeAnweisung] = 'S';
			jetzigeAnweisung++;
			
		}

		else {
			System.out.println("Hier ist ein Raum  :" + jetzigeReihe +"  " + jetzigeSpalte);
		}
				
	}
	
	public void detectEast(){
		jetzigeSpalte++;

		System.out.println("Reihe  : " +jetzigeReihe +"   Spalte  : " + jetzigeSpalte);
		System.out.println("jetzigeAnweisung  : " + jetzigeAnweisung);
		
		System.out.println("ist es ein Raum? : " + gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstRaum());
		
		
		if (gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstRaum()==false){
			anweisungen[jetzigeAnweisung] = 'E';
			jetzigeAnweisung++;
		}
		else {
			System.out.println("Hier ist ein Raum  :" + jetzigeReihe +"  " + jetzigeSpalte);
		}
	}
	
	public void detectNorth(){

		jetzigeReihe--;
		
		System.out.println("Reihe  : " +jetzigeReihe +"   Spalte  : " + jetzigeSpalte);
		System.out.println("jetzigeAnweisung  : " + jetzigeAnweisung);
		
		System.out.println("ist es ein Raum? : " + gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstRaum());
		
		if (gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstRaum()==false){
			anweisungen[jetzigeAnweisung] = 'N';
			jetzigeAnweisung++;
		}

		else {
			System.out.println("Hier ist ein Raum  :" + jetzigeReihe +"  " + jetzigeSpalte);
		}
				
	}
	
	public void detectWest(){

		jetzigeSpalte--;
		
		System.out.println("Reihe  : " +jetzigeReihe +"   Spalte  : " + jetzigeSpalte);
		System.out.println("jetzigeAnweisung  : " + jetzigeAnweisung);
		
		System.out.println("ist es ein Raum? : " + gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstRaum());
		
		if (gui.getKachelArray()[jetzigeSpalte][jetzigeReihe].isIstRaum()==false){
			anweisungen[jetzigeAnweisung] = 'W';
			jetzigeAnweisung++;
			
		}

		else {
			System.out.println("Hier ist ein Raum  :" + jetzigeReihe +"  " + jetzigeSpalte);
		}
				
	}
	
	
	
}
