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
	
	@SuppressWarnings("unused")
	public void goToMain(){
		
		IntroView intro = new IntroView();
        intro.start();
        view.getGameFrameView().close();
        view.close();
	}
	
	public void cancel(){
		
		view.close();
	}

}
