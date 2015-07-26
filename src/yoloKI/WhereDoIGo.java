package yoloKI;

import java.util.ArrayList;

import kacheln.KIKachel;
import kacheln.KIKachelContainer;
import finderOfPaths.WahnsinnigTollerPathfinder;

public class WhereDoIGo {

	private WahnsinnigTollerPathfinder pathfinder;
	private KIKachelContainer kIkachelContainer;
	
	public WhereDoIGo(WahnsinnigTollerPathfinder pathfinder, KIKachelContainer kIkachelContainer){
		this.pathfinder = pathfinder;
		this.kIkachelContainer = kIkachelContainer;
	}
	
	public ArrayList <KIKachel> kachelnSuchen(){
		ArrayList <KIKachel> kacheln = new ArrayList <KIKachel> ();		
		for (int yKoordinate = 0; yKoordinate < kIkachelContainer.getKacheln().length; yKoordinate++)
			for (int xKoordinate = 0; xKoordinate < kIkachelContainer.getKacheln()[yKoordinate].length; xKoordinate++){				
				KIKachel momentaneKIKachel = kIkachelContainer.getKacheln()[yKoordinate][xKoordinate];				
				if (momentaneKIKachel.getMoeglichkeitenHierher() != null){
					kacheln.add(momentaneKIKachel);
				}
			}
		
		return kacheln;
	}
	
	public KIKachel getBestKachel(ArrayList <KIKachel> kiKacheln){
		KIKachel bestKachel = null;
		int bestRating = 0;
		
		for ( KIKachel kachel : kiKacheln){
			if (kachel.getRating() > bestRating){
				bestRating = kachel.getRating();
				bestKachel = kachel;
			}
		}		
		if (bestRating == 0){
			bestKachel = kiKacheln.get(0);
		}
		
		if (bestKachel == null) return kiKacheln.get(0);
		return bestKachel;
	}
	
	public ArrayList <KIKachel> tuerKIKachelnSuchen(ArrayList <KIKachel> kacheln){
		ArrayList <KIKachel> tuerKIKacheln = new ArrayList <KIKachel> ();
		
		for (KIKachel kachel : kacheln){
			if (kachel.isIstTuer()){
				tuerKIKacheln.add(kachel);
			}
		}
		
		return tuerKIKacheln;
	}
	
	public boolean gibtEsTueren(ArrayList <KIKachel> kacheln){		
		return tuerKIKachelnSuchen(kacheln).size() > 0; 
	}
	
	public boolean gibtEsDieseKIKachel(KIKachel kachel, ArrayList<KIKachel> kacheln){
		return kacheln.contains(kachel);
	}
	
}
