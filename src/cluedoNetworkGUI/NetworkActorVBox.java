package cluedoNetworkGUI;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import staticClasses.Config;

public class NetworkActorVBox extends VBox{

	StringProperty ipStringProp;
	StringProperty nameStringProp;
	StringProperty statusStringProp;
	
	public NetworkActorVBox(String name,String ip,String stat) {
		super();
		getStyleClass().add("ipListItem");
		
		setPrefHeight(Config.NETWORK_ACTOR_LIST_ITEM_HEIGHT);
		
		ipStringProp = new SimpleStringProperty(ip);
		nameStringProp = new SimpleStringProperty(name);
		statusStringProp = new SimpleStringProperty(stat);
		
		Label nameLabel = new Label("");
		nameLabel.textProperty().bind(nameStringProp);
		nameLabel.getStyleClass().add("ipNameItem");
		
		Label ipLabel = new Label("");
		ipLabel.textProperty().bind(ipStringProp);
		ipLabel.getStyleClass().add("ipIpItem");
		
		Label statusLabel = new Label("");
		statusLabel.textProperty().bind(statusStringProp);
		statusLabel.getStyleClass().add("ipStatusItem");
		
		
		HBox cont = new HBox();
		cont.getChildren().addAll(nameLabel,statusLabel);
		cont.setMargin(nameLabel,new Insets(5,10,5,5));
		cont.setAlignment(Pos.CENTER_LEFT);
		
		getChildren().addAll(cont,ipLabel);
		setAlignment(Pos.CENTER_LEFT);
		setMargin(ipLabel,new Insets(0,0,0,5));
	}
	
	public void setStatusString(String stat){
		statusStringProp.set(stat);
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
