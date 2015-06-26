package kacheln;

import javafx.scene.paint.Color;
import enums.Orientation;
import enums.Rooms;

/**
 * @since 06.06.2015
 * @version 13.06.2015
 * @author Benedikt Mayer
 *
 */
public class TuerKachel extends Kachel {

	/**
	 * 
	 * @param text die Aufschrift
	 * @param xKoordinate xKoordinate im Grid
	 * @param yKoordinate yKoordinate im Grid
	 * @param istRaum	ob die Kachel ein Raum ist
	 * @param orientierung falls die Kachel eine Tür ist, die Orientierung
	 * @param raum welcher Raum es ist
	 * @param istTuer ob die Kachel eine Tür ist
	 * @param moeglichkeitenHierher falls man hier her kann der Weg wie das geht
	 */	
	public TuerKachel(String text, int xKoordinate, int yKoordinate, boolean istRaum, Orientation orientierung, Rooms raum, boolean istTuer, char [] moeglichkeitenHierher, char [][] moeglichkeitenVonHier, Kachel vonHier){
		super(text, xKoordinate, yKoordinate, istRaum, orientierung, raum, istTuer, moeglichkeitenHierher, moeglichkeitenVonHier, vonHier);
		this.setBackgroundColor(this, Color.BLUEVIOLET);
	}

}
