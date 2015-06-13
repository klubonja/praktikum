package cluedoNetworkGUI;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
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
	final public Scene scene;
	double width;
	double height;
	
	String desc;
	
	 public CluedoNetworkGUI(Stage s){
		 ips = FXCollections.observableArrayList();
		 ipListView = new ListView<String>(ips);
		 
		 primaryStage = s;
		 
		 grid = new GridPane();
		 messagesIn = new TextArea();
		 messagesOut = new TextArea();
		 startService = new Button();
		 scene = new Scene(grid);
		 
		 
		 inLabel = new Text("IN");
		 outLabel = new Text("OUT");
		 status = new Text("down");
		 
		 setListener();
		 
		 grid.setValignment(startService, VPos.CENTER);
		 grid.setMargin(startService, new Insets(0, 0, 0, 10));
		 messagesIn.setEditable(false);
	     messagesOut.setEditable(false);
		 		 
	}
    
    public abstract void startUp();
    
    
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
	  
	  public  void emptyList(){
		  ipListView.getItems().clear();
	  }
	  
	  public void setStatus(String stat){
		  status.setText(stat);
	  }
	  
	  public void  addMessageIn(String mes){
	  SimpleDateFormat date = new SimpleDateFormat("hh:mm:ss");
	  Date now = new Date();
   		  messagesIn.appendText(date.format(now)+" : "+mes+"\n");
   	  }	
	  
	  public  void addMessageOut(String mes){
		  SimpleDateFormat date = new SimpleDateFormat("hh:mm:ss");
		  Date now = new Date();
	   		  messagesOut.appendText(date.format(now)+" : "+mes+"\n");
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
	  
	  void setListener(){
		  ChangeListener<Number> gridwidthlistener = new ChangeListener<Number>() {
				@Override
				public void changed(
						ObservableValue<? extends Number> observable,
						Number oldValue, Number newValue) {
							ipListView.setMaxWidth(newValue.doubleValue()/100*20);
						}							
					};
			grid.widthProperty().addListener(gridwidthlistener);
	  }
	  
	 public void setStylesheet(String cssFile){
		 scene.getStylesheets().add(cssFile);

	  }
	 
	 public void setStageWidth(double w){
		 primaryStage.setWidth(w);
	 }
	 
	 public void setStageHeight(double h){
		 primaryStage.setHeight(h);
	 }

}
