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

	private Orientation orientierung;
	private Rooms raum;
	
	/**
	 * 
	 * @param text
	 * @param xKoordinate
	 * @param yKoordinate
	 * @param istRaum
	 * @param orientierung
	 * @param raum
	 * @param istTuer
	 */
	public TuerKachel(String text, int xKoordinate, int yKoordinate, boolean istRaum, Orientation orientierung, Rooms raum, boolean istTuer, char [] moeglichkeiten){
		super(text, xKoordinate, yKoordinate, istRaum, orientierung, istTuer, moeglichkeiten);
		this.raum = raum;
		this.orientierung = orientierung;
		this.setBackgroundColor(this, Color.BLUEVIOLET);
	}

	public Rooms getRaum() {
		return raum;
	}

	public void setRaum(Rooms raum) {
		this.raum = raum;
	}

	public Orientation getOrientierung() {
		return orientierung;
	}

	public void setOrientierung(Orientation orientierung) {
		this.orientierung = orientierung;
	}
	
	
	
}
