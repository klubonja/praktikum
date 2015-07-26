package kacheln;

import staticClasses.Config;

public class KIKachelContainer {


	private KIKachel [][] kacheln;
	
	/**
	 * Erstellt einen KachelContainer nach Standard-ISO-Bauanleitung
	 */
	public KIKachelContainer(KachelContainer kachelContainer){
		kacheln = new KIKachel [Config.ROWS][Config.COLUMNS];
		for (int yKoordinate = 0; yKoordinate < kacheln.length; yKoordinate++){
			for (int xKoordinate = 0; xKoordinate < kacheln[yKoordinate].length; xKoordinate++){
				Kachel momentaneNichtKIKachel = kachelContainer.getKacheln()[yKoordinate][xKoordinate];
				kacheln[yKoordinate][xKoordinate] = new KIKachel (momentaneNichtKIKachel);
			}
		}
		
	}

	
	/**
	 * Eine Kachel an einer bestimmten Position 
	 * ERST Y DANN X
	 * @param y die Reihe der Zielkachel
	 * @param x die Spalte der Zielkachel
	 * @return die gewuenschte Kachel
	 */
	public KIKachel getKachelAt(int y, int x){
		return kacheln[y][x];
	}

	public void setKacheln(KIKachel[][] kacheln) {
		this.kacheln = kacheln;
	}
	
	/**
	 * Mit Vorsicht zu genießen!
	 * Eine Kachel an eine bestimmte Position setzen
	 * @param reihe die Reihe, an die's gehen soll
	 * @param spalte die Salte, an die's gehen soll
	 * @param kachel die zu verschiebenede Kachel
	 */
	public void setNewKachelPosition(int reihe, int spalte, KIKachel kachel){
		kacheln[reihe][spalte] = kachel;
	}
	
	/**
	 * Alle moeglichkeitenHierher werden auf null gesetzt
	 */
	public void resetMoeglichkeiten(){
		
		for( int iSpalten = 0; iSpalten < kacheln.length;iSpalten++){
			
			for (int jReihen = 0; jReihen < kacheln[iSpalten].length;jReihen++){
				kacheln[iSpalten][jReihen].setMoeglichkeitenHierher(null);
			}
			
		}
		
	}
	
	public int getRatingAt(int yKoordinate, int xKoordinate){
		return this.getKachelAt(yKoordinate, xKoordinate).getRating();
	}
	
	

	public KIKachel[][] getKacheln() {
		return kacheln;
	}
	
}
