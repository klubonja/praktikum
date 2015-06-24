package kacheln;

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

	Orientation peter = Orientation.N;
	Orientation hans = Orientation.S;
	

	/**
	 * 
	 * @param text die Aufschrift
	 * @param xKoordinate xKoordinate im Grid
	 * @param yKoordinate yKoordinate im Grid
	 * @param istRaum	ob die Kachel ein Raum ist
	 * @param orientierung falls die Kachel eine Tür ist, die Orientierung
	 * @param raum hier immer null
	 * @param istTuer ob die Kachel eine Tür ist
	 * @param moeglichkeitenHierher falls man hier her kann der Weg wie das geht
	 */
	public FeldKachel(String text, int xKoordinate, int yKoordinate, boolean istRaum, Orientation orientierung,Rooms raum, boolean istTuer, char [] moeglichkeitenHierher,char [] moeglichkeitenVonHier){
		super(text, xKoordinate, yKoordinate, istRaum, orientierung,raum, istTuer, moeglichkeitenHierher,moeglichkeitenVonHier);
		if (peter==hans){
			
		}
	}
	
	
	
}
