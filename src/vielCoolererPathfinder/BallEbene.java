package vielCoolererPathfinder;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class BallEbene extends Group {

	private Circle spieler;
	private Stage stage;
	private Scene scene;
	
	private Rectangle rechteck;
	
	
	public BallEbene(){
		layoutStuff();
	}
	
	public void start(){
		stage = new Stage();
		scene = new Scene(this, 500, 500);
		stage.setScene(scene);		
		stage.show();
	}
	
	public void layoutStuff(){
		spieler = new Circle(0,0,40);
		rechteck = new Rectangle(0,0,500, 500);
		rechteck.setOpacity(0.1);
		
		GridPane test = new GridPane();
		
		spieler.setFill(Color.RED);
		rechteck.setFill(Color.BLUE);
		
		this.getChildren().add(test);
		this.getChildren().add(spieler);
		this.getChildren().add(rechteck);
		
		
	}

	public Circle getSpieler() {
		return spieler;
	}

	public void setSpieler(Circle spieler) {
		this.spieler = spieler;
	}

	
	
}
