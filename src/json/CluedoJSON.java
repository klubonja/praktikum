package json;
import org.json.*;

import com.sun.org.apache.bcel.internal.generic.RETURN;

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
