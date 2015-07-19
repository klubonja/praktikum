
import javafx.application.Application;
import javafx.stage.Stage;
import view.MainMenuView;

public class GameStart extends Application{
	
	Stage primaryStage;
	

	
	@Override
	public void start(Stage primaryStage) {
		
		this.primaryStage = primaryStage;
// Create
        MainMenuView view = new MainMenuView();
        


        view.start();
    }

	
	
    public static void main(String[] args) {
    	
        Application.launch(args);
    }
}
