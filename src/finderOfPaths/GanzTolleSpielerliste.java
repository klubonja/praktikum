package finderOfPaths;

import java.util.ArrayList;

import javafx.scene.shape.Circle;
import cluedoNetworkLayer.CluedoPlayer;


public class GanzTolleSpielerliste<Objects> extends ArrayList {
	
	private Object currentObject;
	
	public static GanzTolleSpielerliste<CluedoPlayer> playerManager = new GanzTolleSpielerliste<CluedoPlayer>();
	public static GanzTolleSpielerliste<Circle> circleManager = new GanzTolleSpielerliste<Circle>();
	
	private int index = 0;
 	
	public GanzTolleSpielerliste(){
		super();
	}
	
	public void next(){
		
		index = (index + 1) % this.size();
		currentObject = this.get(index % this.size());
		
	}

	public Object getCurrentObject() {
		return currentObject;
	}

	public void setCurrentObject(Object currentObject) {
		this.currentObject = currentObject;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	
	
}
