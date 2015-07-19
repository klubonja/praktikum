
import javafx.application.Application;
import javafx.stage.Stage;
import view.MainMenuPresenter;
import view.MainMenuView;

public class GameStart extends Application{
	
	Stage primaryStage;
	

	
	@Override
	public void start(Stage primaryStage) {
		
		this.primaryStage = primaryStage;

        MainMenuView view = new MainMenuView();
        MainMenuPresenter presenter = new MainMenuPresenter(view);
        


        view.start();
    }

	
	
    public static void main(String[] args) {
    	
        Application.launch(args);
    }
}
