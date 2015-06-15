package cluedoNetworkGUI;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public abstract class CluedoNetworkGUI {
	
	final ObservableList<String> ips;
	final ListView<String> ipListView;
	
	final TabPane tabPane;
	final ObservableList<GameVBox> games;
	final ListView<GameVBox> gameListView;
	
	final HBox menue;
	final TextArea messagesIn;
	final TextArea messagesOut;
	final Text status;
	final Text inLabel;
	final Text outLabel;
	final public Button button0;
	final public GridPane grid;
	final public Stage primaryStage;
	final public Scene scene;
	double width;
	double height;
	
	String desc;
	
	public abstract void startUp();
	
	 public CluedoNetworkGUI(Stage s){
		 tabPane = new TabPane();
		 menue = new HBox();
		 menue.setPrefHeight(30);
		 
		 ips = FXCollections.observableArrayList();
		 ipListView = new ListView<String>(ips);
		 
		 
		 games = FXCollections.observableArrayList();
		 gameListView = new ListView<GameVBox>(games);
		 
		 primaryStage = s;
		 
		 grid = new GridPane();
		 messagesIn = new TextArea();
		 messagesOut = new TextArea();
		 button0 = new Button();
		 scene = new Scene(grid);
		 
		 
		 inLabel = new Text("IN");
		 outLabel = new Text("OUT");
		 status = new Text("down");
		 
		 setListener();
		 
		 menue.getChildren().addAll(button0,status);
		 
		 messagesIn.setEditable(false);
	     messagesOut.setEditable(false);
		 		 
	}  
    
   	 public void addIp(String ip){
       	if (!ips.contains(ip))
       			ips.add(ip);
   	  }   	 
	  
	  public void removeIp(int i){
		  if (i-1 >= 0)  ips.remove(i-1);
	  }
	  public void removeIp(String name){
		  for (int i = 0;i < ips.size(); i++)
			  if (ips.get(i).equals(name))  ips.remove(i);
	  }
	  
	  public  void emptyIpList(){
		  ipListView.getItems().clear();
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
	  
	  public  ListView<String> getIpListView(){
		  return ipListView;
	  }
	  
	  public  ListView<GameVBox> getGamesListView(){
		  return gameListView;
	  }
	  
	  public void removeGame(String gamename){
		  int index;
			for (index = 0; index < games.size(); index++)
	       		if (games.get(index).getId().equals(gamename)) games.remove(index);		 
	  }
	  
	
	  public  void emptyGamesList(){
		  games.clear();
	  }
	  
	  public void addGame(int gameID,String specialinfo, String info){
			for (GameVBox p: games)
	       		if (p.getGameID() == gameID) return;
			 	
			GameVBox gamelistitem = new GameVBox(gameID, specialinfo, info);
			gamelistitem.setPrefHeight(50);		
			games.add(gamelistitem);	
	  }
	  
	  public void updateGame(int gameID,String specialinfo, String info){
		for (GameVBox p: games)
       		if (p.getGameID() == gameID){
       			p.setLabelString(specialinfo);
       			p.setInfoString(info);
       			return;
       		}			 		
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
							tabPane.setMaxWidth(newValue.doubleValue()/100*20);
						}							
				};
		  grid.widthProperty().addListener(gridwidthlistener);
	  }
	  
	public void setStylesheet(String cssFile){
		tabPane.setId("tabPane");
		ipListView.setId("ipList");
		tabPane.getStyleClass().add("listViewC");
		ipListView.getStyleClass().add("listViewC");
		menue.setId("menue");
		gameListView.setId("gameList");
		scene.getStylesheets().add(cssFile);
	}
	 
	public void setStageWidth(double w){
		primaryStage.setWidth(w);
	}
	 
	public void setStageHeight(double h){
	    primaryStage.setHeight(h);
	}

}
