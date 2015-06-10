package nedkosTestPackage;

import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

/**
 * @since 26.05.2015
 * @version 26.05.2015
 * @author Benedikt Mayer
 * 
 * Eine Kachel ist ein Label mit Koordinaten und Hintergrundfarbe
 * Kachel hat die Unterklassen Feldkachel und Raumkachel.
 *
 */
public class Kachel extends Label {

	private int xKoordinate;
	private int yKoordinate;
	private boolean istRaum;
	private BackgroundFill hintergrundfarbe;
	
	public Kachel (String text, int xKoordinate, int yKoordinate, boolean istRaum){
		super(text);
		this.xKoordinate = xKoordinate;
		this.yKoordinate = yKoordinate;
		this.istRaum = istRaum;
	}
	
	public void setBackgroundColor(Label label, Color farbe){
		hintergrundfarbe = new BackgroundFill(farbe, null, null);
		Background hintergrund = new Background(hintergrundfarbe);
		label.setBackground(hintergrund);
	}

	public int getxKoordinate() {
		return xKoordinate;
	}

	public void setxKoordinate(int xKoordinate) {
		this.xKoordinate = xKoordinate;
	}

	public int getyKoordinate() {
		return yKoordinate;
	}

	public void setyKoordinate(int yKoordinate) {
		this.yKoordinate = yKoordinate;
	}

	public boolean isIstRaum() {
		return istRaum;
	}

	public void setIstRaum(boolean istRaum) {
		this.istRaum = istRaum;
	}

	public BackgroundFill getHintergrundfarbe() {
		return hintergrundfarbe;
	}

	public void setHintergrundfarbe(BackgroundFill hintergrundfarbe) {
		this.hintergrundfarbe = hintergrundfarbe;
	}
	
	
}
