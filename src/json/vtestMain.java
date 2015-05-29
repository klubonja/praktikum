package json;

public class vtestMain {
	public static void main(String[] args) {
		CluedoJSON to = new CluedoJSON();
		to.put("type","loginn");
		CluedoProtokollChecker c = new CluedoProtokollChecker(to);
		if (!c.validate()) c.printErrs();
		
		
//		CluedoJSONTypes[] ty = CluedoJSONTypes.values();
//		for (CluedoJSONTypes t : ty){
//			System.out.println("static void val_"+t.getNameNoSpace()+"(CluedoJSON json){\n\n}");
//			
//		}
		
    }
		
		
	
	
}
