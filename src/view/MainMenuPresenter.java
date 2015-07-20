package view;

public class MainMenuPresenter {

	private MainMenuView view;
	
	public MainMenuPresenter(MainMenuView view){
		
		this.view = view;
		
		activateEvents();
		
		}
	
	public void activateEvents(){
		
		view.getPlay().setOnMouseClicked(e -> createClient());
		
	}
	
	public void createClient(){
		
	}

	public MainMenuView getView() {
		return view;
	}

	public void setView(MainMenuView view) {
		this.view = view;
	}
}
