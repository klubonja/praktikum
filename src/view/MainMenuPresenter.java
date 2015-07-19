package view;

import javafx.scene.Scene;
import javafx.stage.Stage;
import cluedoClient.Client;
import cluedoNetworkGUI.CluedoClientGUI;

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
		
	
			Stage stage = new Stage();
			CluedoClientGUI gui = new CluedoClientGUI(stage);
			Client client = new Client(gui);
			stage.show();
			view.close();
		}
		

	public MainMenuView getView() {
		return view;
	}

	public void setView(MainMenuView view) {
		this.view = view;
	}
}
