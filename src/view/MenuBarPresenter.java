package view;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuBarPresenter {
	
	MenuBarView view;
	private GameFrameView gameFrameView;
	
	
	
	public MenuBarPresenter(MenuBarView view, GameFrameView gameFrameView){
		 
		this.view = view;
		this.gameFrameView = gameFrameView;
		
		activateEvents();
	}
	
	public void activateEvents(){
		view.quit.setOnAction(e -> exitGame());
		view.main.setOnAction(e -> goToMain());
	}
	
	@SuppressWarnings("unused")
	public void goToMain(){
		IntroView intro = new IntroView();
		IntroPresenter presMain = new IntroPresenter(intro);
       intro.start();
       gameFrameView.close();
	}
	
	public void exitGame(){
		Platform.exit();
	}

}
