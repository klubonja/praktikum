package enums;

public enum Persons {
	
	red("Fräulein Gloria"),
	yellow("Oberts von Gatow"),
	white("Frau Weiß"),
	green("Reverend Green"),
	blue("Baronin von Porz"),
	purple("Professor Bloom")
	;
	
	
	private String name;
	
	Persons(String name){
		this.name = name;
	}
	
	public String getName() {
        return this.name;
    }
	
	 static public boolean isMember(String aName) {
        Persons[] persons = Persons.values();
        for (Persons person  : persons)
            if (person.getName().equals(aName))
                return true;
        return false;
    }
}
