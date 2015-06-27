package cluedoNetworkGUI;

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
	
	public GameVBox(int gameid,String label, String info,String servername,String serverIp,GameStates state) {
		super();	
		gameLabelStringProp = new SimpleStringProperty(label+" :"+gameid);
		gameInfoStringProp = new SimpleStringProperty(info);
		visibilityProp = new SimpleBooleanProperty(false);
		serverName = servername;
		this.serverIp = serverIp;
		gameID = gameid;
	
		getStyleClass().add("gameListItem");
		
				
		Label gameLabel = new Label(label+" :"+gameid);
		gameLabel.textProperty().bind(gameLabelStringProp);
		gameLabel.getStyleClass().add("gameNameItem");
		
		Label gameInfo = new Label("Connected :" +info);
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
				setRunningGame();
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
		setStyle("-fx-text-fill:#ffffff; "
				+ "-fx-background-color: green;");
	}
	
	public void setRunningGame() {
		setStyle("-fx-text-fill:#ffffff; "
				+ "-fx-background-color: blue;");
	}
	
	public void setEndedGame() {
		setStyle("-fx-text-fill:#ffffff; "
				+ "-fx-background-color: red;");
	}
	
	public void setGameWaiting() {
		setStyle("-fx-text-fill:#000000; "
				+ "-fx-background-color: #ffffff;");
	}
	
	
	
}
