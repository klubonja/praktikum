package cluedoNetworkGUI;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

public class CluedoClientPresenter {
	
	private CluedoClientGUI view;
	
	private final Stop white = new Stop(0,Color.SNOW);
	private final Stop transparent = new Stop(1, Color.TRANSPARENT);
	private final Stop[] stops = new Stop[] { white, transparent };
	private final LinearGradient lg1 = new LinearGradient(0, 0.75, 0, 0, true, CycleMethod.NO_CYCLE, stops);
	
	private Background shadeBackground;
	private BackgroundFill buttonBackgroundFill;
	
	
	public CluedoClientPresenter(CluedoClientGUI view){
		
		this.view = view;
		
		activateEvents();
		bindComponents();
		
	}
	
	public void activateEvents(){
		
		view.getClientNicksView().setOnMouseEntered(e -> addGlow(view.getClientNicksView()));
		view.getGamesListView().setOnMouseEntered(e -> addGlow(view.getGamesListView()));
		view.getClientNicksView().setOnMouseExited(e -> removeGlow(view.getClientNicksView()));
		view.getGamesListView().setOnMouseExited(e -> removeGlow(view.getGamesListView()));
		view.getExit().setOnMouseClicked(e -> exitGame());
		
		view.getCreateGame().setOnMouseEntered(e -> shadeBackground(view.getCreateGame()));
		view.getCreateGame().setOnMouseExited(e -> defaultBackground(view.getCreateGame()));
		view.getCreateServer().setOnMouseEntered(e -> shadeBackground(view.getCreateServer()));
		view.getCreateServer().setOnMouseExited(e -> defaultBackground(view.getCreateServer()));
		view.getConnectToTestServer().setOnMouseEntered(e -> shadeBackground(view.getConnectToTestServer()));
		view.getConnectToTestServer().setOnMouseExited(e -> defaultBackground(view.getConnectToTestServer()));
		view.getRefreshGamesList().setOnMouseEntered(e -> shadeBackground(view.getRefreshGamesList()));
		view.getRefreshGamesList().setOnMouseExited(e -> defaultBackground(view.getRefreshGamesList()));
		view.getExit().setOnMouseEntered(e -> shadeBackground(view.getExit()));
		view.getExit().setOnMouseExited(e -> defaultBackground(view.getExit()));
		
	}
	
	public void bindComponents(){
		
		//view.getAudio().volumeProperty().bind(view.getVolume().valueProperty());
	}
	
	public void addGlow(Node nd){
		
		Glow glow = new Glow(0.9);
		nd.setEffect(glow);	
	}
	
	public void removeGlow(Node nd){
		
		nd.setEffect(null);	
	}
	
	public void exitGame(){
		Platform.exit();
	}
	
	public void shadeBackground(Button b){
		
		buttonBackgroundFill = new BackgroundFill(lg1 , new CornerRadii(1) , new Insets(1));
		shadeBackground = new Background(buttonBackgroundFill);
		b.setBackground(shadeBackground);
	}
	
	public void defaultBackground(Button b){
		
		b.setBackground(view.getButtonBackground());
	}

}
