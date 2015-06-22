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
	
	private Rooms raum;
	
	public RaumKachel(String text, int xKoordinate, int yKoordinate, boolean istRaum, Orientation orientierung, Rooms raum, boolean istTuer, char [] moeglichkeiten){
		super(text, xKoordinate, yKoordinate, istRaum, orientierung, istTuer, moeglichkeiten);
		this.raum = raum;
		istRaum = true;
		this.setBackgroundColor(this, Color.LIGHTSALMON);

	}

	public Rooms getRaum() {
		return raum;
	}

	public void setRaum(Rooms raum) {
		this.raum = raum;
	}
	
	
	
}
