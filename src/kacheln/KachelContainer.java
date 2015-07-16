package kacheln;

import staticClasses.Config;

public class KachelContainer {
	
	private Kachel [][] kacheln;
	
	public KachelContainer(){
		kacheln = new Kachel [Config.ROWS][Config.COLUMNS];
	}

	public Kachel[][] getKacheln() {
		return kacheln;
	}
	
	public Kachel getKachelAt(int y, int x){
		return kacheln[y][x];
	}

	public void setKacheln(Kachel[][] kacheln) {
		this.kacheln = kacheln;
	}
	
	public void setNewKachelPosition(int reihe, int spalte, Kachel kachel){
		kacheln[reihe][spalte] = kachel;
	}
	
	public void resetMoeglichkeiten(){
		
		for( int iSpalten = 0; iSpalten < kacheln.length;iSpalten++){
			
			for (int jReihen = 0; jReihen < kacheln[iSpalten].length;jReihen++){
				kacheln[iSpalten][jReihen].setMoeglichkeitenHierher(null);
			}
			
		}
		
	}
	
	
	
}
