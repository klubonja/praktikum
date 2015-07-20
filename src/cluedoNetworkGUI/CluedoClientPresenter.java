package cluedoNetworkGUI;

import javafx.scene.Node;
import javafx.scene.effect.Glow;

public class CluedoClientPresenter {
	
	private CluedoClientGUI view;
	
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
		
	}
	
	public void bindComponents(){
		
		view.getAudio().volumeProperty().bind(view.getVolume().valueProperty());
	}
	
	public void addGlow(Node nd){
		
		Glow glow = new Glow(0.9);
		nd.setEffect(glow);	
	}
	
	public void removeGlow(Node nd){
		
		nd.setEffect(null);	
	}

}
