package kacheln;

import javafx.scene.paint.Color;
import enums.Orientation;

/**
 * @since 06.06.2015
 * @version 13.06.2015
 * @author Benedikt Mayer
 *
 */
public class TuerKachel extends Kachel {

	private String orientierung;
	private String raum;
	
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
	public TuerKachel(String text, int xKoordinate, int yKoordinate, boolean istRaum, Orientation orientierung, String raum, boolean istTuer){
		super(text, xKoordinate, yKoordinate, istRaum, orientierung, istTuer);
		this.raum = raum;
		this.setBackgroundColor(this, Color.BLUEVIOLET);
	}

	public String getRaum() {
		return raum;
	}

	public void setRaum(String raum) {
		this.raum = raum;
	}
	
	
	
}
