package kacheln;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import enums.Orientation;

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

	private boolean istRaum;
	private boolean istTuer;
	private BackgroundFill hintergrundfarbe;
	private Orientation orientierung;
	private char [] moeglichkeitenHierher = new char [12];
	
	private final IntegerProperty xKoordinate = 
			new SimpleIntegerProperty(this, "xKoordinate", 0);
	

	private final IntegerProperty yKoordinate = 
			new SimpleIntegerProperty(this, "yKoordinate", 0);
	
	
	public Kachel (String text, int xKoordinate, int yKoordinate, boolean istRaum,Orientation orientierung, boolean istTuer, char [] moeglichkeitenHierher){
		super(text);
		this.xKoordinate.set(xKoordinate);
		this.yKoordinate.set(yKoordinate);
		this.istRaum = istRaum;
		this.orientierung = orientierung;
		this.istTuer = istTuer;
		this.moeglichkeitenHierher = moeglichkeitenHierher;
	}
	
	public void setBackgroundColor(Label label, Color farbe){
		hintergrundfarbe = new BackgroundFill(farbe, null, null);
		Background hintergrund = new Background(hintergrundfarbe);
		label.setBackground(hintergrund);
	}
	
	public Orientation getOrientierung() {
		return orientierung;
	}

	public void setOrientierung(Orientation orientierung) {
		this.orientierung = orientierung;
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

	public boolean isIstTuer() {
		return istTuer;
	}

	public void setIstTuer(boolean istTuer) {
		this.istTuer = istTuer;
	}
	

	public int getxKoordinate() {
		return xKoordinate.get();
	}

	public void setKoordinate(int n) {
		this.xKoordinate.set(n);;
	}


	public final IntegerProperty xKoordinateProperty(){
        return xKoordinate;
    }
	
	public int getyKoordinate() {
		return yKoordinate.get();
	}

	public void setyKoordinate(int n) {
		this.yKoordinate.set(n);;
	}


	public final IntegerProperty yKoordinateProperty(){
        return yKoordinate;
    }

	public char[] getMoeglichkeitenHierher() {
		return moeglichkeitenHierher;
	}

	public void setMoeglichkeitenHierher(char[] moeglichkeitenHierher) {
		this.moeglichkeitenHierher = moeglichkeitenHierher;
	}
	
	
	
	
}
