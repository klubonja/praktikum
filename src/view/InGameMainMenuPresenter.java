package view;


public class InGameMainMenuPresenter {
	
	InGameMainMenuView view;
	
	public InGameMainMenuPresenter(InGameMainMenuView view){
		
		this.view = view;
		
		activateEvents();
	}
	
	public void activateEvents(){
		
		view.getYes().setOnAction(e -> goToMain());
		view.getNo().setOnAction(e -> cancel());
	}
	
	
	public void goToMain(){
		
		MainMenuView menu = new MainMenuView();
        menu.start();
        view.getGameFrameView().getAudio().stop();
        view.getGameFrameView().close();
        view.close();
	}
	
	public void cancel(){
		
		view.close();
	}

	public InGameMainMenuView getView() {
		return view;
	}

	public void setView(InGameMainMenuView view) {
		this.view = view;
	}

}
