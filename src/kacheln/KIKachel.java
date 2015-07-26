package kacheln;

import cluedoNetworkLayer.CluedoPosition;
import enums.Orientation;
import enums.Rooms;

public class KIKachel extends Kachel {

	private int rating;
	
	public KIKachel(CluedoPosition position, boolean istRaum,Orientation orientierung,Rooms raum, boolean istTuer, char [] moeglichkeitenHierher, char [][] moeglichkeitenVonHier, Kachel vonHier, int rating){
		super (position, istRaum,orientierung,raum,istTuer, moeglichkeitenHierher, moeglichkeitenVonHier,vonHier);
		this.rating = rating;
	}
	
	public KIKachel (Kachel kachel, int rating){
		super(kachel.getPosition(), kachel.isIstRaum(), kachel.getOrientierung(), kachel.getRaum(), kachel.isIstTuer(), kachel.getMoeglichkeitenHierher(), kachel.getMoeglichkeitenVonHier(), kachel.getVonHier());
		this.rating = rating;
	}
	
	public KIKachel (Kachel kachel){
		super(kachel.getPosition(), kachel.isIstRaum(), kachel.getOrientierung(), kachel.getRaum(), kachel.isIstTuer(), kachel.getMoeglichkeitenHierher(), kachel.getMoeglichkeitenVonHier(), kachel.getVonHier());
		this.rating = 1;
		if (kachel.isIstTuer()){
			this.rating = 4;
		}
	}

	public KIKachel(){
		this.rating = 0;
	}
	
	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
	
	
}
