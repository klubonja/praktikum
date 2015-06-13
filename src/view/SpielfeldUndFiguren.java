package view;

import view.BoardView;
import figuren.FigurenView;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SpielfeldUndFiguren extends StackPane {


	private Stage stage;
	private Scene scene;
	
	private BoardView spielbrett;
	private FigurenView figuren;
	
	public SpielfeldUndFiguren (BoardView spielbrett, FigurenView figuren){
		this.spielbrett = spielbrett;
		this.figuren = figuren;
		
	}
	
	public void start(){
		this.stage = new Stage();
		this.scene = new Scene(this, 700, 700);
		this.stage.setScene(scene);
		layoutStuff();
	}
	
	public void layoutStuff(){

	     this.setMargin(spielbrett, new Insets(0,0,0,0));
	     this.setMargin(figuren, new Insets(0,0,0,0));
	     this.getChildren().addAll(spielbrett, figuren);
	     //this.getChildren().addAll(new Rectangle(100,100,Color.BLUE), new Label("Go!"));
	     
		//this.getChildren().add(hinten);
		//this.getChildren().add(vorne);
		
	}

	
}
