package cluedoClient;

import java.util.logging.Level;

import javafx.application.Application;
import javafx.stage.Stage;
import staticClasses.auxx;
import cluedoNetworkGUI.CluedoClientGUI;

public class CluedoClientMain extends Application {

	public static void main(String[] args) {
		auxx.setLoggingLevel(Level.INFO);
		launch(args);
	}
	// der Client berechnet das ganze spiel
	@Override
	 public void start(Stage primaryStage) {		
		CluedoClientGUI gui = new CluedoClientGUI(primaryStage);
		Client client = new Client(gui);		 
	 }
}
