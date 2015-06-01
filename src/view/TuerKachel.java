package view;

import javafx.scene.paint.Color;
import gui1_0.Raumkachel;

public class TuerKachel extends Kachel {

	private String orientierung;
	private String raum;
	
	public TuerKachel(String text, int xKoordinate, int yKoordinate, boolean istRaum, String orientierung, String raum, boolean istTuer){
		super(text, xKoordinate, yKoordinate, istRaum, istTuer);
		this.orientierung = orientierung;
		this.raum = raum;
		this.setBackgroundColor(this, Color.BLUEVIOLET);
	}
	
}
