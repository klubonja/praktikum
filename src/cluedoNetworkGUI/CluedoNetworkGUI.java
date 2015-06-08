package cluedoNetworkGUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public abstract class CluedoNetworkGUI {
	ObservableList<String> ips;
	ListView<String> ipListView;
	
	final TextArea messagesIn;
	final TextArea messagesOut;
	final Text status;
	final Text inLabel;
	final Text outLabel;
	final public Button startService;
	final public GridPane grid;
	final public Stage primaryStage;
	int width;
	int height;
	
	String desc;
	
	 public CluedoNetworkGUI(Stage s){
		 ips = FXCollections.observableArrayList();
		 ipListView = new ListView<String>(ips);
		 
		 primaryStage = s;
		 grid = new GridPane();
		 messagesIn = new TextArea();
		 messagesOut = new TextArea();
		 startService = new Button();
		 
		 inLabel = new Text("IN");
		 outLabel = new Text("OUT");
		 status = new Text("down");
		 
		 width = 1000;
		 height = 800;
		 		 
	}
    
    public abstract void startUp();
    
    
   	public void addIp(String ip){
       	if (!ips.contains(ip))
       			ips.add(ip);
   	  }   	
   	  
   	 
	  
	  public void removeIp(int i){
		  if (i-1 >= 0)  ips.remove(i-1);
	  }
	  
	  public  void emptyList(){
		  ipListView.getItems().clear();
	  }
	  
	  public void setStatus(String stat){
		  status.setText(stat);
	  }
	  
	  public void  addMessageIn(String mes){
   		  messagesIn.appendText(mes+"\n");
   	  }	
	  
	  public  void addMessageOut(String mes){
		  messagesOut.appendText(mes+"\n");
	  }
	  
	  public  void addMessage(String mes){
		  messagesOut.appendText(mes+"\n");
	  }
	  
	  public void setStartServiceButtonLabel(String label){
		  startService.setText(label);
	  }
	  
	  public void setWindowName(String label){
		  primaryStage.setTitle(label);
	  }
	  
	  public  ListView<String> getIpList(){
		  return ipListView;
	  }
  
}
