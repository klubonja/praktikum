package testStackPane;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class BoardView extends GridPane {

	private Stage stage;
	private Scene scene;

	public void start(){
		this.stage = new Stage();
		this.scene = new Scene(this, 500, 500);
		this.stage.setScene(scene);
		stage.setTitle("hinten");
		Label label1 = new Label("ich bin ein GridPane");
		Label label2 = new Label("Wow hier ist ein tolles Stackpane");
		this.add(label1, 1, 1);
		this.add(label2, 3, 3);
		BackgroundFill hintergrundFarbe = new BackgroundFill(Color.AQUA, null, null);
		Background hintergrund = new Background(hintergrundFarbe);
		this.setBackground(hintergrund);
		// this.stage.show();
	}
	
	
	
	
}
