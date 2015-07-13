package testStackPane;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class StackAllThePanes extends StackPane{

	private Stage stage;
	private Scene scene;
	
	private BoardView hinten;
	private FigurenView vorne;
	
	public StackAllThePanes(BoardView hinten, FigurenView vorne){
		this.hinten = hinten;
		this.vorne = vorne;
		
	}
	
	public void start(){
		this.stage = new Stage();
		this.scene = new Scene(this, 600, 600);
		this.stage.setScene(scene);
		layoutStuff();
		this.stage.show();
	}
	
	public void layoutStuff(){
		hinten.start();
		vorne.start();

	     StackPane.setMargin(hinten, new Insets(50,50,50,50));
	     StackPane.setMargin(vorne, new Insets(7,7,7,7));
	     this.getChildren().addAll(hinten, vorne);
	     //this.getChildren().addAll(new Rectangle(100,100,Color.BLUE), new Label("Go!"));
	     
		//this.getChildren().add(hinten);
		//this.getChildren().add(vorne);
		
	}
	
}
