package json;
import org.json.*;

import enums.CluedoProtokollMessageTypes;

public class CluedoJSON extends JSONObject{
	
	
	private  CluedoProtokollMessageTypes type;
	
	public CluedoJSON(){
		super();
	}
	
	public CluedoJSON(String message){
		super(message);
		
	}
	
	public String getType(){
		return this.type.getName();
	}
}
