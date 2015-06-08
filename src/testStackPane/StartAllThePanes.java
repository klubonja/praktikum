package testStackPane;

import javafx.application.Application;
import javafx.stage.Stage;

public class StartAllThePanes extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		HinteresPane tollesGridPane = new HinteresPane();
		VorderesPane tolleGroup = new VorderesPane();
		StackAllThePanes tolleStackPane = new StackAllThePanes(tollesGridPane, tolleGroup);
		
		tolleStackPane.start();
		//tollesGridPane.start();
		//tolleGroup.start();
	}

	
	public static void main(String[] args) {
		launch(args);
	}
	
}
