package staticClasses;

import java.util.ArrayList;

import org.json.JSONArray;

public abstract class Methods {
	
	
	public static String[] makeConjunction(String[] sa1,JSONArray sa2JSON){
		ArrayList<String> res = new ArrayList<String>();
		
		for (String s1 : sa1)
			for (Object s2o : sa2JSON)
				if (s1.equals(s2o.toString()))
					res.add(s1);
		
		
		return (String[]) res.toArray();
	}
}
