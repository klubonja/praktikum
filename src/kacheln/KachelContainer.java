package kacheln;

import staticClasses.Config;

/**
 * Enthaelt unsere schoenen Model-Kacheln
 * @since 14.07.2015
 * @version 21.07.2015
 * @author Benedikt Mayer
 *
 */
public class KachelContainer {
	
	private Kachel [][] kacheln;
	
	/**
	 * Erstellt einen KachelContainer nach Standard-ISO-Bauanleitung
	 */
	public KachelContainer(){
		kacheln = new Kachel [Config.ROWS][Config.COLUMNS];
	}

	
	/**
	 * Eine Kachel an einer bestimmten Position 
	 * ERST Y DANN X
	 * @param y die Reihe der Zielkachel
	 * @param x die Spalte der Zielkachel
	 * @return die gewuenschte Kachel
	 */
	public Kachel getKachelAt(int y, int x){
		return kacheln[y][x];
	}

	public void setKacheln(Kachel[][] kacheln) {
		this.kacheln = kacheln;
	}
	
	/**
	 * Mit Vorsicht zu genießen!
	 * Eine Kachel an eine bestimmte Position setzen
	 * @param reihe die Reihe, an die's gehen soll
	 * @param spalte die Salte, an die's gehen soll
	 * @param kachel die zu verschiebenede Kachel
	 */
	public void setNewKachelPosition(int reihe, int spalte, Kachel kachel){
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
	

	public Kachel[][] getKacheln() {
		return kacheln;
	}
	
	
}
