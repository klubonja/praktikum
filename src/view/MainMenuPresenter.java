package view;

import javafx.scene.effect.Glow;
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
		view.getPlay().setOnMouseEntered(e -> addGlow());
		view.getPlay().setOnMouseExited(e -> removeGlow());
		
	}
	
	public void createClient(){
		
	
			Stage stage = new Stage();
			CluedoClientGUI gui = new CluedoClientGUI(stage);
			Client client = new Client(gui);
			stage.show();
			view.close();
		}
	
	public void addGlow(){
		
		Glow glow = new Glow();
		glow.setLevel(0.5);
		view.getPlay().setStyle("-fx-background-color: #090a0c, linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%)," 
		        + "linear-gradient(#20262b, #191d22),"
		        + "radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));"
		     + "-fx-background-radius: 5,4,3,5;"
		     + "-fx-background-insets: 0,1,2,0;"
		    + "-fx-text-fill: white;"
		    + "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );"
		     + "-fx-font-family: Arial;"
		    + "-fx-text-fill: linear-gradient(white, #d0d0d0);"
		    + "-fx-font-size: 12px;"
		    + "-fx-padding: 10 20 10 20;"
		    + "-fx-effect: glow(0.5);");
	}
	
	public void removeGlow(){
		
		view.getPlay().setEffect(null);
	}
		

	public MainMenuView getView() {
		return view;
	}

	public void setView(MainMenuView view) {
		this.view = view;
	}
}
