package cluedoNetworkGUI;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class GameVBox extends VBox{
	int gameID;
	StringProperty gameLabelStringProp;
	StringProperty gameInfoStringProp;
	
	public GameVBox(int gameid,String label, String info) {
		super();	
		gameLabelStringProp = new SimpleStringProperty(label+" :"+gameid);
		gameInfoStringProp = new SimpleStringProperty(info);
		gameID = gameid;
		setPrefHeight(50);
		getStyleClass().add("gameListItem");
		
				
		Label gameLabel = new Label(label+" :"+gameid);
		gameLabel.textProperty().bind(gameLabelStringProp);
		gameLabel.getStyleClass().add("gameNameItem");
		
		Label gameInfo = new Label("Connected :" +info);
		gameInfo.textProperty().bind(gameInfoStringProp);
		gameInfo.getStyleClass().add("gameInfoItem");
		gameInfo.setLayoutY(14);
		
		getChildren().addAll(gameLabel,gameInfo);
	}
	
	public int getGameID() {
		return gameID;
	}
	
	public void setLabelString(String newText){
		gameLabelStringProp.set(newText);
	}
	public void setInfoString(String newText){
		gameInfoStringProp.set(newText);
	}
	
	public void appendInfoString(String newText){
		gameInfoStringProp.set(gameInfoStringProp.get()+", "+newText);
	}
	
	public String getInfoString(){
		return gameInfoStringProp.get();
	}
	
	
	
	
}
