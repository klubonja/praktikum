package enums;

public enum Persons {
	
	red("red","Fr‰ulein Gloria"),
	yellow("yellow","Oberts von Gatow"),
	white("white","Frau Weiﬂ"),
	green("green","Reverend Green"),
	blue("blue","Baronin von Porz"),
	purple("purple","Professor Bloom")
	;
	
	
	private String name;
	private String personName;
	
	Persons(String pName,String name){
		this.name = name;
		this.personName = pName;
	}
	
	public String getName() {
        return this.name;
    }
	
	public String getPersonName() {
        return this.personName;
    }
	
	 static public boolean isMember(String aName) {
        Persons[] persons = Persons.values();
        for (Persons person  : persons)
            if (person.getName().equals(aName))
                return true;
        return false;
    }
	 
	 static public boolean isMemberPersonName(String aName) {
        Persons[] persons = Persons.values();
        for (Persons person  : persons)
            if (person.getPersonName().equals(aName))
                return true;
        return false;
    }
}
