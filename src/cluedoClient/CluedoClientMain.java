package cluedoClient;

import javafx.application.Application;
import javafx.stage.Stage;
import cluedoNetworkGUI.CluedoClientGUI;

public class CluedoClientMain extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	// der Client berechnet das ganze spiel
	@Override
	 public void start(Stage primaryStage) {		
		CluedoClientGUI gui = new CluedoClientGUI(primaryStage);
		Client client = new Client(gui);		 
	 }
}
