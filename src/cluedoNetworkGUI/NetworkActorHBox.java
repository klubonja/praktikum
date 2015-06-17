package cluedoNetworkGUI;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class NetworkActorHBox extends HBox{
	String nameID;
	String ipID;
	StringProperty ipStringProp;
	StringProperty nameStringProp;
	public NetworkActorHBox(String name,String ip) {
		super();
		nameID = name; 
		ipID = ip;
		getStyleClass().add("ipListItem");
		
		ipStringProp = new SimpleStringProperty(ipID);
		nameStringProp = new SimpleStringProperty(name);
		
		Label nameLabel = new Label("");
		nameLabel.textProperty().bind(ipStringProp);
		nameLabel.getStyleClass().add("gameNameItem");
		
		Label ipLabel = new Label("");
		ipLabel.textProperty().bind(nameStringProp);
		ipLabel.getStyleClass().add("gameInfoItem");
		
		
		getChildren().addAll(nameLabel,ipLabel);
	}
	
	public void setNameString(String newText){
		nameStringProp.set(newText);
	}
	public void setIpString(String newText){
		ipStringProp.set(newText);
	}
	
	public void appendInfoString(String newText){
		nameStringProp.set(nameStringProp.get()+", "+newText);
	}
	
	public String getNameID(){
		return nameStringProp.get();
	}
	
	public String getIpID(){
		return ipStringProp.get();
	}
	
	
	
}
