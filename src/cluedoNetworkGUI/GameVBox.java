package cluedoNetworkGUI;

import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import enums.GameStates;

public class GameVBox extends VBox{
	int gameID;
	StringProperty gameLabelStringProp;
	StringProperty gameInfoStringProp;
	BooleanProperty visibilityProp;
	String serverIp;
	String serverName;
	Button startGame;
	ArrayList<Label> kids;
	
	public GameVBox(int gameid,String label, String info,String servername,String serverIp,GameStates state) {
		super();	
		gameLabelStringProp = new SimpleStringProperty(label+" :"+gameid);
		gameInfoStringProp = new SimpleStringProperty(info);
		visibilityProp = new SimpleBooleanProperty(false);
		serverName = servername;
		this.serverIp = serverIp;
		gameID = gameid;
	
		getStyleClass().add("gameListItem");
		kids = new ArrayList<Label>();
				
		Label gameLabel = new Label(label+" :"+gameid);
		kids.add(gameLabel);
		gameLabel.textProperty().bind(gameLabelStringProp);
		gameLabel.getStyleClass().add("gameNameItem");
		
		Label gameInfo = new Label("Connected :" +info);
		kids.add(gameInfo);
		gameInfo.textProperty().bind(gameInfoStringProp);
		gameInfo.getStyleClass().add("gameInfoItem");
		gameInfo.setLayoutY(14);
		
		
		//HBox hb = new HBox();
		
//		startGame = new Button("Start Game");
//		startGame.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
//		startGame.getStyleClass().add("startGameButtonGamesList");
//		hb.getChildren().add(startGame);
//		hb.setAlignment(Pos.CENTER_RIGHT);
//		hb.visibleProperty().bind(visibilityProp);
		switch (state) {
			case started :
				setRunningGame();
				break;
			case ended :
				setEndedGame();
				break;
		}
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
	
	public String getServerName() {
		return serverName;
	}
	
	public String getServerIp() {
		return serverIp;
	}
	
	public void setReadyGame() {
		String style = "-fx-text-fill:#ffffff; "
				+ "-fx-background-color: green;";
		for (Label l: kids)
			l.setStyle(style);
		setStyle(style);
	}
	
	public void setRunningGame() {
		String style = "-fx-text-fill:#ffffff; "
				+ "-fx-background-color: blue;";
		for (Label l: kids)
			l.setStyle(style);
		setStyle(style);
	}
	
	public void setEndedGame() {
		String style = "-fx-text-fill:#ffffff; "
				+ "-fx-background-color: red;";
		for (Label l: kids)
			l.setStyle(style);
		setStyle(style);
	}
	
	public void setGameWaiting() {
		String style = "-fx-text-fill:#000000; "
				+ "-fx-background-color: #ffffff;";
		for (Label l: kids)
			l.setStyle(style);
		setStyle(style);
	}
	
	
	
}
