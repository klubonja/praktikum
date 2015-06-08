
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.IntroPresenter;
import view.IntroView;

public class GameStart extends Application{
	
	Stage primaryStage;
	
	
	
	@SuppressWarnings("unused")
	@Override
	public void start(Stage primaryStage) {
		
		this.primaryStage = primaryStage;

        IntroView view = new IntroView(primaryStage);
        IntroPresenter presenter = new IntroPresenter(view);

        Scene scene = new Scene(view, 1000, 650);

        this.primaryStage.setScene(scene);
        this.primaryStage.setResizable(false);
        this.primaryStage.setTitle("YinYanYolos present:");
        this.primaryStage.show();
    }

    public static void main(String[] args) {
    	
        Application.launch(args);
    }
}
