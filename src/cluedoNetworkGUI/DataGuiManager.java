package cluedoNetworkGUI;

import javafx.application.Platform;
import javafx.scene.control.ListView;
import staticClasses.auxx;
import enums.GameStates;

public class DataGuiManager   {
	
	CluedoNetworkGUI gui;
	
	public DataGuiManager(CluedoNetworkGUI gui) {
		this.gui = gui;
	}
	
	
	
	public ListView<NetworkActorVBox> getNetworkActorsListView(){
		return gui.getNetworkActorsView();
	}
	
	public ListView<GameVBox> getGameListView(){
		return gui.getGamesListView();
	}
	
	public void addMsgIn(String msg){		
		Platform.runLater(() -> {
			gui.addMessageIn(auxx.now()+" : "+msg+"\n");
		});
	}
	
	public void addMessageOut(String msg){		
		Platform.runLater(() -> {
			gui.addMessageOut(auxx.now()+" : "+msg+"\n");
		});
	}
	
	
	  public void removeNetworkActorFromGui(String name,String ip){		 
		 Platform.runLater(() -> {
			 gui.removeNetworkActor(name,ip);
		  });
	  }
	  
	  public void addNetworkActorToGui(String name,String ip,String status){		 
		 Platform.runLater(() -> {
			 gui.addNetworkActor(name,ip,status);
		  });
	  }
	  
	  public void updateNetworkActorGui(String name,String ip,String status){		 
			 Platform.runLater(() -> {
				 gui.updateNetworkActor(name,ip,status);
			  });
		  }
	  
	  public void addGameToGui(int gameID,String specialInfo, String info,GameStates state,String servername,String serverip){
		  Platform.runLater(() -> {
			gui.addGame(gameID, specialInfo, info,state,servername,serverip);
		});
	  }
	  
	  public  void emptyIpList(){
		  Platform.runLater(() -> {
			  gui.emptyIpList();});
	  }
	  
	  public void setStatus(String stat){
		  Platform.runLater(() -> {gui.setStatus(stat);});
	  }
	  
	  public void setStartServiceButtonLabel(String label){
		  Platform.runLater(() -> {gui.setStartServiceButtonLabel(label);});
	  }	  
	 
	  public void setWindowName(String label){
		  Platform.runLater(() -> {gui.setWindowName(label);});
	  }
	  
	  public void removeGameGui(int gameID){
		  Platform.runLater(() -> {gui.removeGame(gameID);	 });
	  }	  
	
	  public  void emptyGamesList(){
		  Platform.runLater(() -> {gui.emptyGamesList();});
	  }
	  
	  public  void setReadyGame(int gameID){
		  Platform.runLater(() -> {
			  gui.updateGameSetReady(gameID);
		  });
	  }
	  
	  public  void setRunningGame(int gameID){
		  Platform.runLater(() -> {
			  gui.updateGameSetRunning(gameID);
		  });
	  }
	  
	  public  void setGameEndedGui(int gameID){
		  Platform.runLater(() -> {
			  gui.updateGameSetEnded(gameID);
		  });
	  }
	  
	  public  void setGameWaitingGui(int gameID){
		  Platform.runLater(() -> {
			  gui.updateGameSetWaiting(gameID);
		  });
	  }
	  
	  public void updateGame(int gameID,String specialinfo, String info){
		Platform.runLater(() -> {
			gui.updateGame(gameID, specialinfo, info); 	
		});
	  }
	  
	  public CluedoNetworkGUI getGui() {
		return gui;
	}
}
