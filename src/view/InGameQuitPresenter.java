package view;

import javafx.application.Platform;


public class InGameQuitPresenter {
	
	InGameQuitView view;
	
	public InGameQuitPresenter(InGameQuitView view){
		
		this.view = view;
		
		activateEvents();
	}
	
	public void activateEvents(){
		
		view.getYes().setOnAction(e -> quitGame());
		view.getNo().setOnAction(e -> cancel());
	}
	
	public void quitGame(){
		
		Platform.exit();
		System.exit(0);
	}
	
	public void cancel(){
		
		view.close();
	}

	public InGameQuitView getView() {
		return view;
	}

	public void setView(InGameQuitView view) {
		this.view = view;
	}

}
