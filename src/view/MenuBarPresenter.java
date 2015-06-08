package view;

import javafx.application.Platform;

public class MenuBarPresenter {
	
	MenuBarView view;
	
	public MenuBarPresenter(MenuBarView view){
		 
		this.view = view;
		
		activateEvents();
	}
	
	public void activateEvents(){
		view.quit.setOnAction(e -> exitGame());
	}
	
	public void exitGame(){
		Platform.exit();
	}

}
