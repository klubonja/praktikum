package kacheln;

import javafx.scene.paint.Color;

/**
 * @since 26.05.2015
 * @version 26.05.2015
 * @author Benedikt
 *
 * Eine Raumkachel ist eine Kachel, bei welcher istRaum = true ist und 
 * der Hintergrund rot ist.
 */
public class RaumKachel extends Kachel {
	
	private String raum;
	
	public RaumKachel(String text, int xKoordinate, int yKoordinate, boolean istRaum, String raum, boolean istTuer){
		super(text, xKoordinate, yKoordinate, istRaum, istTuer);
		this.raum = raum;
		istRaum = true;
		this.setBackgroundColor(this, Color.LIGHTSALMON);

	}
	
}
