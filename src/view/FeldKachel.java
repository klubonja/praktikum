package view;

/**
 * @since 26.05.2015
 * @version 26.05.2015
 * @author Benedikt
 *
 * Eine Feldkachel ist Eine Kachel, welche kein Raum ist.
 */
public class FeldKachel extends Kachel {

	public FeldKachel(String text, int xKoordinate, int yKoordinate, boolean istRaum, boolean istTuer){
		super(text, xKoordinate, yKoordinate, istRaum, istTuer);
	}
	
	
	
}
