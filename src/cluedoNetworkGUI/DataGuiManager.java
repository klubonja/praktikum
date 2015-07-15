package cluedoNetworkGUI;

import javafx.application.Platform;
import javafx.scene.control.ListView;
import staticClasses.Config;
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
			gui.addMessageIn(msg+"\n");
		});
	}
	
	public void cleanInput(){		
		Platform.runLater(() -> {
			gui.messagesIn.setText("");
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
	  
	  public void addGameToGui(int gameID, String playersinfo,String watchersinfo,GameStates state,String servername,String serverip){
		  Platform.runLater(() -> {
			gui.addGame(gameID, playersinfo,watchersinfo,state,servername,serverip);
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
//			try {
				 Platform.runLater(() -> {
					 try {
						    gui.removeGame(gameID);	
						    auxx.loginfo("game : "+gameID+ " removed");
						} 
						catch (Exception e) {
							try {
								wait(Config.SECOND/100);
								
							} catch (Exception e1) {
								removeGameGui(gameID);
								auxx.logsevere("nasty business on multithread javafxobserablelist update", e);					
							} //naive workaround for not beeing threadsafe							
						}					  		  
				  });	
//			} 
//			catch (Exception e) {
//				try {
//					wait(Config.SECOND/10); //naive workaround for not beeing threadsafe
//					removeGameGui(gameID);
//				} 
//				catch (InterruptedException e1) {
//					auxx.logsevere("nasty business on multithread javafxobserablelist update", e1);					
//				}
//		  }		 
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
	  
	  public void updateGame(int gameID, String playersinfo,String watchersInfo){
		Platform.runLater(() -> {
			gui.updateGame(gameID, playersinfo,watchersInfo); 	
		});
	  }
	  
	  public CluedoNetworkGUI getGui() {
		return gui;
	}
}
