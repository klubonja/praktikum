package testStackPane;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class FigurenView extends Group {

	private Stage stage;
	private Scene scene;

	public void start(){
		this.stage = new Stage();
		this.scene = new Scene(this, 400, 400);
		this.stage.setScene(scene);
		stage.setTitle("vorne");
		Circle figur = new Circle(200,200,50);
		
		this.getChildren().add(figur);
		//BackgroundFill hintergrundFarbe = new BackgroundFill(Color.AQUA, null, null);
		//Background hintergrund = new Background(hintergrundFarbe);
		//this.setBackground(hintergrund);
		
		//this.stage.show();
	}
	
}
