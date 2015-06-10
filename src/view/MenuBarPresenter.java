package view;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuBarPresenter {
	
	MenuBarView view;
	Stage primaryStage;
	
	public MenuBarPresenter(MenuBarView view, Stage primaryStage){
		 
		this.view = view;
		this.primaryStage = primaryStage;
		
		activateEvents();
	}
	
	public void activateEvents(){
		view.quit.setOnAction(e -> exitGame());
		view.main.setOnAction(e -> goToMain());
	}
	
	@SuppressWarnings("unused")
	public void goToMain(){
		Stage mainStage = new Stage();
		IntroView main = new IntroView(mainStage);
		IntroPresenter presMain = new IntroPresenter(main);
		Scene scene = new Scene(main, 1000, 650);
        mainStage.setScene(scene);
        mainStage.setResizable(true);
        mainStage.setTitle("YinYanYolos Present:");
        mainStage.setResizable(false);
        mainStage.show();
        primaryStage.close();
	}
	
	public void exitGame(){
		Platform.exit();
	}

}
