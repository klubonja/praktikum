package networkTest;


import javafx.application.Application;
import javafx.stage.Stage;
import cluedoNetworkGUI.CluedoClientGUI;
import cluedoNetworkGUI.CluedoServerGUI;
import cluedoServer.*;
import cluedoClient.*;

public class NetworkTestMain extends Application {

	@Override
	public void start(Stage primaryStage) {
		 CluedoServerGUI serverGUI = new CluedoServerGUI(primaryStage);
		 TCPServer server = new TCPServer(serverGUI);	
		 
		 CluedoClientGUI clientGUI = new CluedoClientGUI(new Stage());
			TCPClient client = new TCPClient(clientGUI);	
	}

	public static void main(String[] args) {
		launch(args);
	}
}
