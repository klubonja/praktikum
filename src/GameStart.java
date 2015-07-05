
import javafx.application.Application;
import javafx.stage.Stage;
import view.IntroView;

public class GameStart extends Application{
	
	Stage primaryStage;
	
	
	
	@SuppressWarnings("unused")
	@Override
	public void start(Stage primaryStage) {
		
		this.primaryStage = primaryStage;

        IntroView view = new IntroView();
        


        view.start();
    }

	
	
    public static void main(String[] args) {
    	
        Application.launch(args);
    }
}
