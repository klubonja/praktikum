package json;
import org.json.JSONObject;

import enums.CluedoProtokollMessageTypes;

public class CluedoJSON extends JSONObject{
	
	
	private  CluedoProtokollMessageTypes type;
	
	public CluedoJSON(){
		super();
	}
	
	public CluedoJSON(String typeValue){
		super();
		this.put("type", typeValue);
		
	}
	
	public CluedoJSON(JSONObject json){
		super(json, JSONObject.getNames(json));		
	}
	
	public String getType(){
		return this.type.getName();
	}
}
