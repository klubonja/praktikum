package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Weapon{
	
	private final StringProperty name =
            new SimpleStringProperty(this, "name", null);
	
	private final IntegerProperty xCoord = 
			new SimpleIntegerProperty(this, "xCoord", 0);
	
	private final IntegerProperty yCoord = 
			new SimpleIntegerProperty(this, "yCoord", 0);
	
	public Weapon(String name, int x, int y){
		this.name.set(name);
		this.xCoord.set(x);
		this.yCoord.set(y);
	}

	
	public String getFirstName() {
		return name.get();
	}

	public int getxCoord() {
		return xCoord.get();
	}

	public int getyCoord() {
		return yCoord.get();
	}
	
	public void setFirstName(String name) {
		this.name.set(name);
	}

	public void setxCoord(int n) {
		this.xCoord.set(n);;
	}

	public void setyCoord(int n) {
		this.yCoord.set(n);
	}
	
	public final StringProperty nameProperty(){
        return name;
    }
	
	public final IntegerProperty xCoordProperty(){
        return xCoord;
    }
	
	public final IntegerProperty yCoordProperty(){
        return yCoord;
    }
	
	
}

