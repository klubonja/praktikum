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
		 Server server = new Server(serverGUI);	
		 
		 CluedoClientGUI clientGUI = new CluedoClientGUI(new Stage());
			Client client = new Client(clientGUI);	
	}

	public static void main(String[] args) {
		launch(args);
	}
}
