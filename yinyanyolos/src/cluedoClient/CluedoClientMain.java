package cluedoClient;

import cluedoNetworkGUI.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class CluedoClientMain extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	// der Client berechnet das ganze spiel
	@Override
	 public void start(Stage primaryStage) {		
		CluedoClientGUI gui = new CluedoClientGUI(primaryStage);
		TCPClient client = new TCPClient(gui);		 
	 }
}
