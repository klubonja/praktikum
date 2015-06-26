
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

        Scene scene = new Scene(view, 1000, 650);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("YinYanYolos present:");
        primaryStage.show();
    }

    public static void main(String[] args) {
    	
        Application.launch(args);
    }
}
