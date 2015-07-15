package cluedoNetworkGUI;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import staticClasses.Config;
import enums.GameStates;

public abstract class CluedoNetworkGUI {
	
	final ObservableList<NetworkActorVBox> networkActors;
	final ListView<NetworkActorVBox> networkActorsListView;
	
	final TabPane tabPane;
	final ObservableList<GameVBox> games;
	final ListView<GameVBox> gameListView;
	
	final HBox menue;
	final HBox statusContainer;
	final TextArea messagesIn;
	final TextArea messagesOut;
	final Text status;
	final Text inLabel;
	final Text outLabel;
	final public TextArea inputField;
	final public Button button0;
	final public GridPane grid;
	final public Stage primaryStage;
	final public Scene scene;
	final public ArrayList<Button> blist;
	double width;
	double height;
	
	String desc;
	
	public abstract void startUp(); 
	
	 public CluedoNetworkGUI(Stage s){
		 blist = new ArrayList<Button>();
		 statusContainer = new HBox();
		 statusContainer.setFillHeight(true);
		 tabPane = new TabPane();
		 menue = new HBox();
		 menue.setPrefHeight(30);		
		 
		 networkActors = FXCollections.observableArrayList();
		 networkActorsListView = new ListView<NetworkActorVBox>(networkActors);		 
		 
		 games = FXCollections.observableArrayList();
		 gameListView = new ListView<GameVBox>(games);
		 
		 primaryStage = s;
		 
		 grid = new GridPane();
		 messagesIn = new TextArea();
		 messagesOut = new TextArea();
		 button0 = new Button();
		 scene = new Scene(grid);
		 inputField = new TextArea();			 
		 
		 inLabel = new Text("IN");
		 outLabel = new Text("OUT");
		 status = new Text("NO CONNECTION");
		 statusContainer.getChildren().add(status);
		 statusContainer.setAlignment(Pos.CENTER);
		 statusContainer.setPadding(new Insets(10, 0, 10, 0));
		 
		 
		 setListener();
		 
		 menue.getChildren().addAll(button0);
		 
		 messagesIn.setEditable(false);
	     messagesOut.setEditable(false);		 		 
	}  
    
   	 public boolean addNetworkActor(String name,String ip,String status){
   		 boolean notcontains = true;
       	for (NetworkActorVBox na: networkActors)
       		if (na.getIpID().equals(ip) && na.getNameID().equals(name)){
       			notcontains = !notcontains;
       			continue;
       		}
       	if (notcontains) return networkActors.add(new NetworkActorVBox(name, ip,status));
       	return false;
   	  } 
   	 
   	 public boolean updateNetworkActor(String name,String ip,String status){
       	for (NetworkActorVBox na: networkActors)
       		if (na.getIpID().equals(ip) && na.getNameID().equals(name)){
       			na.setStatusString(status);
       			na.setIpString(ip);
       			continue;
       		}
       	return false;
   	  }   	 
	  
	 
	  public boolean removeNetworkActor(String name,String ip){
	       	for (NetworkActorVBox na: networkActors){
	       		if (na.getIpID().equals(ip) && na.getNameID().equals(name)){
	       			return networkActors.remove(na);
	       		}
	       	}       		
	       	
	      return false;	      
	  }
	  
	  public  void emptyIpList(){
		  networkActorsListView.getItems().clear();
	  }
	  
	  public void setStatus(String stat){
		  status.setText(stat);
	  }
	  
	  public void  addMessageIn(String mes){	  
   		  messagesIn.appendText(mes);
   	  }	
	  
	  public  void addMessageOut(String mes){
	   	  messagesOut.appendText(mes);
	  }
	  
	  public void setStartServiceButtonLabel(String label){
		  button0.setText(label);
	  }
	  
	 
	  public void setWindowName(String label){
		  primaryStage.setTitle(label);
	  }
	  
	  public  ListView<NetworkActorVBox> getNetworkActorsView(){
		  return networkActorsListView;
	  }
	  
	  public  ListView<GameVBox> getGamesListView(){
		  return gameListView;
	  }
	  
	  public void removeGame(int gameID){		  
			for (GameVBox gameListItem: games)
	       		if (gameListItem.getGameID() == gameID) games.remove(gameListItem);		 
	  }	  
	
	  public  void emptyGamesList(){
		  games.clear();
	  }
	  
	  public void addGame(int gameID, String playersinfo,String watchersinfo, GameStates state,String servername,String serverip){
			for (GameVBox p: games)
	       		if (p.getGameID() == gameID) return;
			
			GameVBox gamelistitem = new GameVBox(gameID,playersinfo,watchersinfo,servername,serverip,state);
			gamelistitem.setPrefHeight(Config.GAME_LIST_ITEM_HEIGHT);		
			games.add(gamelistitem);	
	  }
	  
	  public void updateGame(int gameID, String playersinfo, String watchersinfo ){
		for (GameVBox p: games)
       		if (p.getGameID() == gameID){
       			p.setInfoString(playersinfo);
       			p.setWatchersInfo(watchersinfo);
       			return;
       		}			 		
	  }
	 
	  public void updateGameSetReady(int gameID){
		  getGame(gameID).setReadyGame();
		  
	  }
	  public void updateGameSetRunning(int gameID){
		  getGame(gameID).setRunningGame();
	  }
	  
	  public void updateGameSetEnded(int gameID){
		  getGame(gameID).setEndedGame();
	  }
	  
	  public void updateGameSetWaiting(int gameID){
		  getGame(gameID).setGameWaiting();
	  }
	  
	  public GameVBox getGame(int gameID){
			for (GameVBox p: games)
	       		if (p.getGameID() == gameID){
	       			return p;
	       		}
			return null;
		  }
	  
	  
	  
	  void setListener(){
		  ChangeListener<Number> gridwidthlistener = new ChangeListener<Number>() {
				@Override
				public void changed(
						ObservableValue<? extends Number> observable,
						Number oldValue, Number newValue) {
							tabPane.setMaxWidth(newValue.doubleValue()/100*40);
						}							
				};
		  grid.widthProperty().addListener(gridwidthlistener);
	  }
	  
	public void setStylesheet(String cssFile){
		tabPane.setId("tabPane");
		networkActorsListView.setId("ipList");
		tabPane.getStyleClass().add("listViewC");
		networkActorsListView.getStyleClass().add("listViewC");
		menue.setId("menue");
		gameListView.setId("gameList");
		status.setId("status");
		scene.getStylesheets().add(cssFile);
	}
	 
	public void setStageWidth(double w){
		primaryStage.setWidth(w);
	}
	 
	public void setStageHeight(double h){
	    primaryStage.setHeight(h);
	}

}
