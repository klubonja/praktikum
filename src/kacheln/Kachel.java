package kacheln;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import cluedoNetworkLayer.CluedoPosition;
import enums.Orientation;
import enums.Rooms;

/**
 * @since 26.05.2015
 * @version 21.05.2015
 * @author Benedikt Mayer
 * 
 * Eine Kachel ist ein Label mit Koordinaten und Hintergrundfarbe
 * Kachel hat die Unterklassen Feldkachel und Raumkachel.
 *
 */
public class Kachel{

	private boolean istRaum;
	private boolean istTuer;
	private BackgroundFill hintergrundfarbe;
	private Orientation orientierung;
	private Rooms raum;
	private char [] moeglichkeitenHierher = new char [12];
	private char [][] moeglichkeitenVonHier;
	private Kachel vonHier;
	private CluedoPosition position; 
		
	/**
	 * @param position die CluedoPosition der Kachel
	 * @param istRaum ob die Kachel ein Raum ist
	 * @param orientierung falls die Kachel eine Tuer ist, die Orientierung
	 * @param istTuer ob die Kachel eine Tuer ist
	 * @param raum der Raum hier (falls es einer ist)
	 * @param moeglichkeitenHierher falls man hier her kann der Weg wie das geht
	 * @param moeglichkeitenVonHier wo man von hier aus hin kann (falls man wo hin kann) und wie man dort hin kommt.
	 * @param vonHier eine bestimmte Kachel, welche man von hier aus betreten kann.
	 */
	public Kachel (CluedoPosition position, boolean istRaum,Orientation orientierung,Rooms raum, boolean istTuer, char [] moeglichkeitenHierher, char [][] moeglichkeitenVonHier, Kachel vonHier){
		this.position = position;
		this.istRaum = istRaum;
		this.orientierung = orientierung;
		this.raum = raum;
		this.istTuer = istTuer;
		this.moeglichkeitenHierher = moeglichkeitenHierher;
		this.moeglichkeitenVonHier = moeglichkeitenVonHier;
		this.vonHier = vonHier;
	}
	
	/**
	 * Quick n' dirty zum initiieren ohne viel zu wissen.
	 */
	public Kachel(){}
	
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

	public CluedoPosition getPosition() {
		return position;
	}

	public void setPosition(CluedoPosition position) {
		this.position = position;
	}
	
	
	
	
}
