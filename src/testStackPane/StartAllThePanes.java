package testStackPane;

import javafx.application.Application;
import javafx.stage.Stage;

public class StartAllThePanes extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		BoardView tollesGridPane = new BoardView();
		FigurenView tolleGroup = new FigurenView();
		StackAllThePanes tolleStackPane = new StackAllThePanes(tollesGridPane, tolleGroup);
		
		tolleStackPane.start();
		//tollesGridPane.start();
		//tolleGroup.start();
	}

	
	public static void main(String[] args) {
		launch(args);
	}
	
}
