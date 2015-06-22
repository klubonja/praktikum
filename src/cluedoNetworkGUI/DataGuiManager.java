package cluedoNetworkGUI;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.application.Platform;
import javafx.scene.control.ListView;

public class DataGuiManager   {
	
	CluedoNetworkGUI gui;
	
	public DataGuiManager(CluedoNetworkGUI gui) {
		this.gui = gui;
	}
	
	private String now(){
		SimpleDateFormat date = new SimpleDateFormat("hh:mm:ss");
		  Date now = new Date();
		  
		  return date.format(now);
	}
	
	public ListView<NetworkActorVBox> getNetworkActorsListView(){
		return gui.getNetworkActorsView();
	}
	
	public ListView<GameVBox> getGameListView(){
		return gui.getGamesListView();
	}
	
	public void addMsgIn(String msg){		
		Platform.runLater(() -> {
			gui.addMessageIn(now()+" : "+msg+"\n");
		});
	}
	
	public void addMessageOut(String msg){		
		Platform.runLater(() -> {
			gui.addMessageOut(now()+" : "+msg+"\n");
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
	  
	  public void addGameToGui(int gameID,String specialInfo, String info){
		  Platform.runLater(() -> {
			gui.addGame(gameID, specialInfo, info);
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
	  
	  public void removeGame(int gameID){
		  Platform.runLater(() -> {gui.removeGame(gameID);	 });
	  }
	  
	
	  public  void emptyGamesList(){
		  Platform.runLater(() -> {gui.emptyGamesList();});
	  }
	  
	  public void addGame(int gameID,String specialinfo, String info){
		  Platform.runLater(() -> {gui.addGame(gameID, specialinfo, info);});
	  }
	  
	  
	  
	  public void updateGame(int gameID,String specialinfo, String info){
		Platform.runLater(() -> {
			gui.updateGame(gameID, specialinfo, info); 	
			System.out.println("special info in dataguimanager l105"+info);
		});
	  }
	  
	  public CluedoNetworkGUI getGui() {
		return gui;
	}
}
