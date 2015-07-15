package cluedoNetworkGUI;

import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import enums.GameStates;

public class GameVBox extends VBox{
	int gameID;
	StringProperty gameLabelStringProp;
	StringProperty playersInfoStringProp;
	StringProperty watchersInfoStringProp;
	BooleanProperty visibilityProp;
	String serverIp;
	String serverName;
	Button startGame;
	ArrayList<HBox> kids;
	ArrayList<Label> labels;
	
	public GameVBox(int gameid, String playersinfo, String watchersinfo, String servername,String serverIp,GameStates state) {
		super();	
		labels = new ArrayList<Label>();
		gameLabelStringProp = new SimpleStringProperty(""+gameid+"");
		playersInfoStringProp = new SimpleStringProperty(playersinfo);
		watchersInfoStringProp = new SimpleStringProperty(watchersinfo);
		visibilityProp = new SimpleBooleanProperty(false);
		serverName = servername;
		this.serverIp = serverIp;
		gameID = gameid;
	
		getStyleClass().add("gameListItem");
		kids = new ArrayList<HBox>();
		
		HBox gameBox = new HBox();
		Label gameLabel = new Label("Game : ");
		gameLabel.getStyleClass().add("gameLabel");
		Label gameId = new Label(""+gameid+"");
		gameBox.getChildren().addAll(gameLabel,gameId);
		gameId.textProperty().bind(gameLabelStringProp);
		gameLabel.getStyleClass().add("gameNameItem");
		labels.add(gameLabel);
		labels.add(gameId);
		kids.add(gameBox);
		
		HBox playerBox = new HBox();
		Label playerLabel = new Label("Players : ");
		playerLabel.getStyleClass().add("gameInfoLabel");
		Label playersInfo = new Label("Connected :" +playersinfo);
		playersInfo.textProperty().bind(playersInfoStringProp);
		playersInfo.getStyleClass().add("gameInfoItem");
		playerBox.getChildren().addAll(playerLabel,playersInfo);
		kids.add(playerBox);
		labels.add(playerLabel);
		labels.add(playersInfo);
		//playersInfo.setLayoutY(14);
		
		HBox watchersBox = new HBox();
		Label watchersLabel = new Label("Watchers : ");
		watchersLabel.getStyleClass().add("gameInfoLabel");
		Label watchersInfo = new Label(watchersinfo);		
		watchersInfo.textProperty().bind(watchersInfoStringProp);
		watchersInfo.getStyleClass().add("gameInfoItem");
		watchersBox.getChildren().addAll(watchersLabel,watchersInfo);
		//watchersInfo.setLayoutY(14);
		kids.add(watchersBox);
		labels.add(watchersInfo);
		labels.add(watchersLabel);
		
		for (HBox b: kids)
			b.setAlignment(Pos.BOTTOM_LEFT);
		
		switch (state) {
			case started :
				setRunningGame();
				break;
			case ended :
				setEndedGame();
				break;
		}
		getChildren().addAll(gameBox,playerBox,watchersBox);
	}
	
	public int getGameID() {
		return gameID;
	}
	
	public void setLabelString(String newText){
		gameLabelStringProp.set(newText);
	}
	public void setInfoString(String newText){
		playersInfoStringProp.set(newText);
	}
	
	public void setWatchersInfo(String watchersInfo) {
		this.watchersInfoStringProp.set(watchersInfo);
	}
	
	public void appendInfoString(String newText){
		playersInfoStringProp.set(playersInfoStringProp.get()+", "+newText);
	}
	
	public String getInfoString(){
		return playersInfoStringProp.get();
	}
	
	public String getServerName() {
		return serverName;
	}
	
	public String getServerIp() {
		return serverIp;
	}
	
	public void setReadyGame() {
		String style = "-fx-text-fill:#ffffff; "
				+ "-fx-background-color: green;";
		setStyleAll(style);
	}
	
	public void setRunningGame() {
		String style = "-fx-text-fill:#ffffff; "
				+ "-fx-background-color: blue;";
		setStyleAll(style);
	}
	
	public void setEndedGame() {
		String style = "-fx-text-fill:#ffffff; "
				+ "-fx-background-color: red;";
		setStyleAll(style);
	}
	
	public void setGameWaiting() {
		String style = "-fx-text-fill:#000000; "
				+ "-fx-background-color: #ffffff;";
		setStyleAll(style);
	}
	
	public void setStyleAll(String style){
		for (Label l: labels)
			l.setStyle(style);
		setStyle(style);
	}
	
}
