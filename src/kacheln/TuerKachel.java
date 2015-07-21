package kacheln;

import cluedoNetworkLayer.CluedoPosition;
import enums.Orientation;
import enums.Rooms;

/**
 * @since 06.06.2015
 * @version 21.07.2015
 * @author Benedikt Mayer
 *
 * Eine TuerKachel repraesentiert eine Kachel, welche den Eingang eines Raumes markiert.
 * Hat eine Orientierung, von welcher aus man sie betreten kann (und damit den Raum)
 *
 */
public class TuerKachel extends Kachel {

	/**
	 * @param position die CluedoPosition der Kachel
	 * @param istRaum	ob die Kachel ein Raum ist
	 * @param orientierung falls die Kachel eine T�r ist, die Orientierung
	 * @param raum welcher Raum es ist
	 * @param istTuer ob die Kachel eine T�r ist
	 * @param moeglichkeitenHierher falls man hier her kann der Weg wie das geht
	 * @param moeglichkeitenVonHier wo man von hier aus hin kann (falls man wo hin kann) und wie man dort hin kommt.
	 * @param vonHier eine bestimmte Kachel, welche man von hier aus betreten kann.
	 */	
	public TuerKachel(CluedoPosition position, boolean istRaum, Orientation orientierung, Rooms raum, boolean istTuer, char [] moeglichkeitenHierher, char [][] moeglichkeitenVonHier, Kachel vonHier){
		super(position, istRaum, orientierung, raum, istTuer, moeglichkeitenHierher, moeglichkeitenVonHier, vonHier);
	}

}
