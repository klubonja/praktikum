package kacheln;

import javafx.scene.paint.Color;
import enums.Orientation;
import enums.Rooms;

/**
 * @since 26.05.2015
 * @version 26.05.2015
 * @author Benedikt
 *
 * Eine Raumkachel ist eine Kachel, bei welcher istRaum = true ist und 
 * der Hintergrund rot ist.
 */
public class RaumKachel extends Kachel {
	
	 
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
	
	public RaumKachel(String text, int xKoordinate, int yKoordinate, boolean istRaum, Orientation orientierung, Rooms raum, boolean istTuer, char [] moeglichkeitenHierher, char [][] moeglichkeitenVonHier, Kachel vonHier){
		super(text, xKoordinate, yKoordinate, istRaum, orientierung, raum, istTuer, moeglichkeitenHierher, moeglichkeitenVonHier, vonHier);
		istRaum = true;
		this.setBackgroundColor(this, Color.LIGHTSALMON);

	}
	
}
