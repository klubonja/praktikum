package cluedoClient;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

import javafx.application.Application;
import javafx.stage.Stage;
import staticClasses.auxx;
import cluedoNetworkGUI.CluedoClientGUI;

public class CluedoClientMain extends Application {
	static FileHandler fh;
	public static void main(String[] args) {
		auxx.setLoggingLevel(Level.INFO);

		// This block configure the logger with handler and formatter  
//        try {
//			fh = new FileHandler("./log.log");
//		} catch (SecurityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}  
//        auxx.log.addHandler(fh);
//        SimpleFormatter formatter = new SimpleFormatter();  
//        fh.setFormatter(formatter);  
		launch(args);
	}
	@Override
	 public void start(Stage primaryStage) {		
		CluedoClientGUI gui = new CluedoClientGUI(primaryStage);
		Client client = new Client(gui);		 
	 }
}
