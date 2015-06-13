package vielCoolererPathfinder;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class KrasserStack extends StackPane {

	private Stage stage;
	private Scene scene;
	
	private BallEbene2 ballEbene;
	private GUI gui;
	
	public KrasserStack(BallEbene2 ballEbeneEingabe, GUI guiEingabe){
		
		this.ballEbene = ballEbeneEingabe;
		this.gui = guiEingabe;
		gui.start();
		ballEbene.start();
		
		layoutStuff();
	}
	
	public void start(){
		stage = new Stage();
		scene = new Scene(this, 500, 500);
		stage.setScene(scene);
		
		stage.show();
		
		doThatTitleThang("hans");
	}
	
	public void layoutStuff(){
		
		StackPane.setMargin(gui, new Insets(0,0,0,0));
		StackPane.setMargin(ballEbene, new Insets(0,0,0,0));
		
		this.getChildren().addAll(gui, ballEbene);
		
		
	}
	
	
	public void doThatTitleThang(String title){
		this.stage.setTitle(title);
	}

	
}
