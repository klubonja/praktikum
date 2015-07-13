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

	public void setKacheln(Kachel[][] kacheln) {
		this.kacheln = kacheln;
	}
	
	public void setNewKachelPosition(int reihe, int spalte, Kachel kachel){
		kacheln[reihe][spalte] = kachel;
	}
	
}
