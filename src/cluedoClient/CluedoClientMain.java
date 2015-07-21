package cluedoClient;

import java.util.logging.FileHandler;
import java.util.logging.Level;

import javafx.application.Application;
import javafx.stage.Stage;
import staticClasses.auxx;
import cluedoNetworkGUI.CluedoClientGUI;
import cluedoNetworkGUI.CluedoClientPresenter;

public class CluedoClientMain extends Application {
	static FileHandler fh;
	public static void main(String[] args) {
		auxx.setLoggingLevel(Level.FINE);

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
		CluedoClientPresenter pres = new CluedoClientPresenter(gui);
		
	 }
	
	
}
