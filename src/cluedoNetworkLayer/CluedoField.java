package cluedoNetworkLayer;

import org.json.JSONObject;

public class CluedoField extends JSONObject{
	
	public CluedoField (CluedoPosition position){
		super();
		this.put("x", position.getX());
		this.put("y", position.getY());
	}

}
