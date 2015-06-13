
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.*;

public class GameStart extends Application{
	
	
	
	@SuppressWarnings("unused")
	@Override
	public void start(Stage primaryStage) {

        IntroView view = new IntroView();
        IntroPresenter presenter = new IntroPresenter(view);

        view.start();
    }

	
	
    public static void main(String[] args) {
    	
        Application.launch(args);
    }
}
