package cluedoServer;

import java.util.logging.Level;

import javafx.application.Application;
import javafx.stage.Stage;
import staticClasses.auxx;
import cluedoNetworkGUI.CluedoServerGUI;

public class CluedoServerMain extends Application{

	public static void main(String[] args) {
		auxx.setLoggingLevel(Level.INFO);
		auxx.setLoggingLevel(Level.SEVERE);
        launch(args);
    }
	   
	 @Override
	 public void start(Stage primaryStage) {
		 CluedoServerGUI gui = new CluedoServerGUI(primaryStage);
		 Server server = new Server(gui);		  
	 }
}
