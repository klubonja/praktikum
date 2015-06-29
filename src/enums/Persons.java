package enums;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import cluedoNetworkLayer.CluedoPosition;

public enum Persons {
	
	red("red","Fräulein Gloria",new CluedoPosition(10,20),Color.RED),
	yellow("yellow","Oberts von Gatow",new CluedoPosition(1,2),Color.YELLOW),
	white("white","Frau Weiss",new CluedoPosition(7,23), Color.WHITE),
	green("green","Reverend Green",new CluedoPosition(23,20), Color.GREEN),
	blue("blue","Baronin von Porz",new CluedoPosition(20,21), Color.BLUE),
	purple("purple","Professor Bloom",new CluedoPosition(21,7), Color.PURPLE)
	;
	
	
	private String color;
	private Color farbe;
	private String personName;
	private CluedoPosition startposition;
	
	Persons(String color,String name,CluedoPosition startpos,Color farbe){
		this.color = color;
		this.personName = name;
		this.farbe = farbe;
		startposition = startpos;
	}
	
	public CluedoPosition getStartposition() {
		return startposition;
	}
	
	public String getColor() {
        return this.color;
    }
	
	public Color getFarbe() {
		return farbe;
	}

	public String getPersonName() {
        return this.personName;
    }
	
	 static public boolean isMemberColor(String aName) {
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
	
	 static public Persons getPersonByName(String name) {
	        Persons[] persons = Persons.values();
	        for (Persons person  : persons)
	            if (person.getPersonName().equals(name))
	                return person;
	        return null;
	    }
	 
	 static public Persons getPersonByColor(String color) {
	        Persons[] persons = Persons.values();
	        for (Persons person  : persons)
	            if (person.getColor().equals(color))
	                return person;
	        return null;
	    }
	 public static ArrayList<String> getPersonsString() {        
	        Persons[] persons = Persons.values();
	        ArrayList<String> pnames = new ArrayList<String>();
	        for (Persons p: persons)
	        	pnames.add(p.getPersonName());
	        return pnames;
	    }
}
