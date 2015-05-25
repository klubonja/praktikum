package cluedoServer;

import javafx.application.Application;
import javafx.stage.Stage;
import cluedoNetworkGUI.*;

public class CluedoServerMain extends Application{

	public static void main(String[] args) {
        launch(args);
    }
	   
	 @Override
	 public void start(Stage primaryStage) {
		 CluedoServerGUI gui = new CluedoServerGUI(primaryStage);
		 TCPServer server = new TCPServer(gui);		  
	 }
}
