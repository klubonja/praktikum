package cluedoNetworkLayer;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class CluedoPosition {
	IntegerProperty x;
	IntegerProperty y;
	
	public CluedoPosition(int x, int y) {
		this.x = new SimpleIntegerProperty(x);
		this.y = new SimpleIntegerProperty(y);
	}
	
	public int getX() {
		return x.get();
	}
	public int getY() {
		return y.get();
	}
	public void setX(int x) {
		this.x.set(x);
	}
	public void setY(int y) {
		this.y.set(y);
	}
}
