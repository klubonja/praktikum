package kacheln;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import enums.Orientation;
import enums.Rooms;

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
	private Rooms raum;
	private char [] moeglichkeitenHierher = new char [12];
	private char [][] moeglichkeitenVonHier;
	private Kachel vonHier;
	
	private final IntegerProperty xKoordinate = 
			new SimpleIntegerProperty(this, "xKoordinate", 0);
	

	private final IntegerProperty yKoordinate = 
			new SimpleIntegerProperty(this, "yKoordinate", 0);
	
	/**
	 * 
	 * @param text die Aufschrift
	 * @param xKoordinate xKoordinate im Grid
	 * @param yKoordinate yKoordinate im Grid
	 * @param istRaum	ob die Kachel ein Raum ist
	 * @param orientierung falls die Kachel eine Tür ist, die Orientierung
	 * @param istTuer ob die Kachel eine Tür ist
	 * @param moeglichkeitenHierher falls man hier her kann der Weg wie das geht
	 */
	public Kachel (String text, int xKoordinate, int yKoordinate, boolean istRaum,Orientation orientierung,Rooms raum, boolean istTuer, char [] moeglichkeitenHierher, char [][] moeglichkeitenVonHier, Kachel vonHier){
		super(text);
		this.xKoordinate.set(xKoordinate);
		this.yKoordinate.set(yKoordinate);
		this.istRaum = istRaum;
		this.orientierung = orientierung;
		this.raum = raum;
		this.istTuer = istTuer;
		this.moeglichkeitenHierher = moeglichkeitenHierher;
		this.moeglichkeitenVonHier = moeglichkeitenVonHier;
		this.vonHier = vonHier;
	}
	
	public Kachel(){
		
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

	public Rooms getRaum() {
		return raum;
	}

	public void setRaum(Rooms raum) {
		this.raum = raum;
	}

	public char[][] getMoeglichkeitenVonHier() {
		return moeglichkeitenVonHier;
	}

	public void setMoeglichkeitenVonHier(char[][] moeglichkeitenVonHier) {
		this.moeglichkeitenVonHier = moeglichkeitenVonHier;
	}

	public Kachel getVonHier() {
		return vonHier;
	}

	public void setVonHier(Kachel vonHier) {
		this.vonHier = vonHier;
	}
	
	
	
	
}
