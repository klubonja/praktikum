package kacheln;

import cluedoNetworkLayer.CluedoPosition;


public class KIKachel {

	private int rating;
	private Kachel kachel;
	
	public KIKachel(Kachel k,int rating){
		kachel = k;
		this.rating = rating;
	}
	
	public KIKachel (Kachel kachel){
		this.rating = 1;
		this.kachel = kachel;
		if (kachel.isIstTuer()){
			this.rating = 4;
		}
	}
	
	public Kachel getKachel() {
		return kachel;
	}
	
	public CluedoPosition getPosition(){
		return kachel.getPosition();
	}
	
	public int getX(){
		return kachel.getPosition().getX();
	}
	
	public int getY(){
		return kachel.getPosition().getY();
	}
	
	public boolean isIstRaum(){
		return kachel.isIstRaum();
	}
	
	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
	public boolean isIstTuer(){
		return kachel.isIstTuer();
	}
	public char[] getMoeglichkeitenHierher() {
		return kachel.getMoeglichkeitenHierher();
	}
	
	public void setMoeglichkeitenHierher(char[] moeglichkeitenHierher){
		kachel.setMoeglichkeitenHierher(moeglichkeitenHierher);
	}
	
}
