package kacheln;

import enums.Orientation;


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
	
	public FeldKachel(String text, int xKoordinate, int yKoordinate, boolean istRaum, Orientation orientierung, boolean istTuer){
		super(text, xKoordinate, yKoordinate, istRaum, orientierung, istTuer);
		if (peter==hans){
			
		}
	}
	
	
	
}
