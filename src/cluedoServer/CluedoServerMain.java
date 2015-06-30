package cluedoServer;

import java.util.logging.Level;

import staticClasses.auxx;
import javafx.application.Application;
import javafx.stage.Stage;
import cluedoNetworkGUI.*;

public class CluedoServerMain extends Application{

	public static void main(String[] args) {
		auxx.setLoggingLevel(Level.INFO);
        launch(args);
    }
	   
	 @Override
	 public void start(Stage primaryStage) {
		 CluedoServerGUI gui = new CluedoServerGUI(primaryStage);
		 Server server = new Server(gui);		  
	 }
}
