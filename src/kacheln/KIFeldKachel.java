package kacheln;

import cluedoNetworkLayer.CluedoPosition;
import enums.Orientation;
import enums.Rooms;

public class KIFeldKachel extends KIKachel {

	public KIFeldKachel(CluedoPosition position, boolean istRaum,Orientation orientierung, Rooms raum, boolean istTuer,char[] moeglichkeitenHierher, char[][] moeglichkeitenVonHier,Kachel vonHier, int rating) {
		super(position, istRaum, orientierung, raum, istTuer, moeglichkeitenHierher,moeglichkeitenVonHier, vonHier, rating);
		
	}

	
}