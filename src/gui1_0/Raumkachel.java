package gui1_0;

import javafx.scene.paint.Color;

public class Raumkachel extends Kachel {
	
	public Raumkachel(String text, int xKoordinate, int yKoordinate, boolean istRaum){
		super(text, xKoordinate, yKoordinate, istRaum);
		istRaum = true;
		this.setBackgroundColor(this, Color.RED);
	}
	
}
