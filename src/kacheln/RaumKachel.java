package kacheln;

import javafx.scene.paint.Color;
import cluedoNetworkLayer.CluedoPosition;
import enums.Orientation;
import enums.Rooms;

/**
 * @since 26.05.2015
 * @version 21.07.2015
 * @author Benedikt Mayer
 *
 * Eine Raumkachel ist eine Kachel, bei welcher istRaum = true ist und 
 * der Hintergrund eine interessante Farbe hat.
 */
public class RaumKachel extends Kachel {
	
	 
	/**
	 * @param position die CluedoPosition der Kachel
	 * @param istRaum	ob die Kachel ein Raum ist
	 * @param orientierung falls die Kachel eine T�r ist, die Orientierung
	 * @param raum welcher Raum es ist
	 * @param istTuer ob die Kachel eine T�r ist
	 * @param moeglichkeitenHierher falls man hier her kann der Weg wie das geht
	 * @param moeglichkeitenVonHier wo man von hier aus hin kann (falls man wo hin kann) und wie man dort hin kommt.
	 * @param vonHier eine bestimmte Kachel, welche man von hier aus betreten kann.
	 */
	
	public RaumKachel(CluedoPosition position, boolean istRaum, Orientation orientierung, Rooms raum, boolean istTuer, char [] moeglichkeitenHierher, char [][] moeglichkeitenVonHier, Kachel vonHier){
		super(position, istRaum, orientierung, raum, istTuer, moeglichkeitenHierher, moeglichkeitenVonHier, vonHier);
		istRaum = true;

	}
	
}
