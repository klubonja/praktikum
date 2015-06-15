package cluedoNetworkGUI;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.application.Platform;
import javafx.scene.control.ListView;

public class CluedoNetworkDataGuiInterface {
	
	
	CluedoNetworkGUI gui;
	
	public CluedoNetworkDataGuiInterface(CluedoNetworkGUI gui) {
		this.gui = gui;
	}
	
	private String now(){
		SimpleDateFormat date = new SimpleDateFormat("hh:mm:ss");
		  Date now = new Date();
		  
		  return date.format(now);
	}
	
	public void addIp(String ip){
		Platform.runLater(() -> {
			gui.addIp(ip);
		});
   	  }   	 
	
	public void addMessageIn(String msg){		
		Platform.runLater(() -> {
			gui.addMessageIn(now()+" : "+msg);
		});
	}
	
	public void addMessageOut(String msg){		
		Platform.runLater(() -> {
			gui.addMessageOut(now()+" : "+msg+"\n");
		});
	}
	
	public void removeIp(int i){
		  Platform.runLater(() -> {
			  gui.removeIp(i);
		  });
	  }
	  public void removeIp(String name){
		 removeGame(name);
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
	  
	  public void removeGame(String gamename){
		  Platform.runLater(() -> {gui.removeGame(gamename);	 });
	  }
	  
	
	  public  void emptyGamesList(){
		  Platform.runLater(() -> {gui.emptyGamesList();});
	  }
	  
	  public void addGame(int gameID,String specialinfo, String info){
		  Platform.runLater(() -> {gui.addGame(gameID, specialinfo, info);});
	  }
	  
	  public void updateGame(int gameID,String specialinfo, String info){
		Platform.runLater(() -> {gui.updateGame(gameID, specialinfo, info); 		});
	  }
}
