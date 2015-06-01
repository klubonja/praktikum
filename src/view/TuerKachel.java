package view;

import javafx.scene.paint.Color;
import gui1_0.Raumkachel;

public class TuerKachel extends Kachel {

	private String orientierung;
	
	public TuerKachel(String text, int xKoordinate, int yKoordinate, boolean istRaum, String orientierung){
		super(text, xKoordinate, yKoordinate, istRaum);
		this.orientierung = orientierung;
		this.setBackgroundColor(this, Color.BLUEVIOLET);
	}
	
}
