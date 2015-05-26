package json;
import org.json.*;

public class CluedoJSON extends JSONObject{
	
	private String type;
	
	public CluedoJSON(String type){
		super();
		this.type = type;
		put("type",type);
	}
	
	public String getType(){
		return type;
	}
}
