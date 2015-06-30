package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;

public class Player {

	
	private final StringProperty name =
            new SimpleStringProperty(this, "name", null);
	
	private final IntegerProperty xCoord = 
			new SimpleIntegerProperty(0);
	
	private final IntegerProperty yCoord = 
			new SimpleIntegerProperty(0);
	
	Color color;
	
	
	public Player(String name, int x, int y, Color color){
		this.name.set(name);
		this.xCoord.set(x);
		this.yCoord.set(y);
		this.color = color;
	}
	
	public Player(String name, Color color){
		this.name.set(name);
		this.color = color;
	}

	
	public Color getColor() {
		return color;
	}


	public void setColor(Color color) {
		this.color = color;
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
		this.xCoord.set(n);
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
