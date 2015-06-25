package nedkosTestPackage;

import javafx.scene.paint.Color;

/**
 * @since 26.05.2015
 * @version 26.05.2015
 * @author Benedikt
 *
 * Eine Raumkachel ist eine Kachel, bei welcher istRaum = true ist und 
 * der Hintergrund rot ist.
 */
public class Raumkachel extends Kachel {
	
	public Raumkachel(String text, int xKoordinate, int yKoordinate, boolean istRaum){
		super(text, xKoordinate, yKoordinate, istRaum);
		istRaum = true;
		this.setBackgroundColor(this, Color.RED);
	}
	
}
