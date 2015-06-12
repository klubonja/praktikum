package enums;

import cluedoServer.CluedoPosition;

public enum Persons {
	
	red("red","Fräulein Gloria",new CluedoPosition(10,20)),
	yellow("yellow","Oberts von Gatow",new CluedoPosition(1,2)),
	white("white","Frau Weiß",new CluedoPosition(7,23)),
	green("green","Reverend Green",new CluedoPosition(23,20)),
	blue("blue","Baronin von Porz",new CluedoPosition(20,21)),
	purple("purple","Professor Bloom",new CluedoPosition(21,7))
	;
	
	
	private String color;
	private String personName;
	private CluedoPosition startposition;
	
	Persons(String pName,String name,CluedoPosition startpos){
		this.color = name;
		this.personName = pName;
		startposition = startpos;
	}
	
	public CluedoPosition getStartposition() {
		return startposition;
	}
	
	public String getColor() {
        return this.color;
    }
	
	public String getPersonName() {
        return this.personName;
    }
	
	 static public boolean isMember(String aName) {
        Persons[] persons = Persons.values();
        for (Persons person  : persons)
            if (person.getColor().equals(aName))
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
