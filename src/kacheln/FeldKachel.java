package kacheln;

import cluedoNetworkLayer.CluedoPosition;
import enums.Orientation;
import enums.Rooms;


/**
 * @since 26.05.2015
 * @version 26.05.2015
 * @author Benedikt
 *
 * Eine Feldkachel ist Eine Kachel, welche kein Raum ist.
 */
public class FeldKachel extends Kachel {

	/**
	 * @param istRaum	ob die Kachel ein Raum ist
	 * @param orientierung falls die Kachel eine Tuer ist, die Orientierung
	 * @param raum hier immer null
	 * @param istTuer ob die Kachel eine Tuer ist
	 * @param moeglichkeitenHierher falls man hier her kann der Weg wie das geht
	 * @param moeglichkeitenVonHier wo man von hier aus hin kann (falls man wo hin kann) und wie man dort hin kommt.
	 * @param vonHier eine bestimmte Kachel, welche man von hier aus betreten kann.
	 */
	public FeldKachel(CluedoPosition position, boolean istRaum, Orientation orientierung,Rooms raum, boolean istTuer, char [] moeglichkeitenHierher,char [][] moeglichkeitenVonHier, Kachel vonHier){
		super(position, istRaum, orientierung,raum, istTuer, moeglichkeitenHierher,moeglichkeitenVonHier, vonHier);
	}
	
	
	
}
