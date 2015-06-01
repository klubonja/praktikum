package enums;


public enum Orientation {
	N("North"),
	O("East"),//Englishoderdeutschoderwas?
	S("South"),
	W("West");
	
	private String orientationName;
	
	Orientation(String name){
		orientationName = name;
	}
	
	public String getOName(){
		return orientationName;
	}
}
