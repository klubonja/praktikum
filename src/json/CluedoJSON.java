package json;
import org.json.*;

public class CluedoJSON extends JSONObject{
	
	
	private  CluedoJSONTypes type;
	
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
