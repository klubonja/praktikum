package vielCoolererPathfinder;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class BallEbene extends Group {

	private Circle spieler;
	private Stage stage;
	private Scene scene;
	
	
	public BallEbene(){
		
	}
	
	public void start(){
		stage = new Stage();
		scene = new Scene(this, 500, 500);
		layoutStuff();
		stage.setScene(scene);		
	}
	
	public void layoutStuff(){
		spieler = new Circle(0,0,40);
		this.getChildren().add(spieler);
	}

	public Circle getSpieler() {
		return spieler;
	}

	public void setSpieler(Circle spieler) {
		this.spieler = spieler;
	}

	
	
}
