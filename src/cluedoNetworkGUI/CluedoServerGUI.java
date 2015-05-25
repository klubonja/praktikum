package cluedoNetworkGUI;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
 
public class CluedoServerGUI {
	ObservableList<String> ips;
	ListView<String> clientList;
	final TextArea messages;
	final Text status;
	final public Button startServerButton;
	//final public Button startGameButton;
	final public Stage primaryStage;
	int width;
	int height;
	
	 public CluedoServerGUI(Stage s){
		 ips = FXCollections.observableArrayList();
		 clientList = new ListView<String>(ips);
		 messages = new TextArea();
		 status = new Text("down");
		 startServerButton = new Button("StartServer");
		 //startGameButton = new Button("StartGame");
		 primaryStage = s;
		 width = 1000;
		 height = 800;
		 startUp(primaryStage);
		 
		 
	}
	  public void addClient(String ip){
		  ips.add(ip);
		  
	  }
	  
	  public void removeClient(int i){
		  if (i-1 >= 0)  ips.remove(i-1);
	  }
	  
	  public void emptyList(){
		  clientList.getItems().clear();
	  }
	  
	  public void setStatus(String stat){
		  status.setText(stat);
	  }
	  
	  public void addMessage(String mes){
		  messages.appendText(mes+"\n");
	  }
    
    
    public void startUp(Stage primaryStage) {
        primaryStage.setTitle("TestServer");
        
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setGridLinesVisible(false);
        
        ColumnConstraints col0 = new ColumnConstraints();
        col0.setHgrow(Priority.ALWAYS);
        col0.setPercentWidth(20);
        grid.getColumnConstraints().add(col0);
        
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.ALWAYS);
        col1.setPercentWidth(80);
        grid.getColumnConstraints().add(col1);
        
        RowConstraints row0 = new RowConstraints();
        row0.setPercentHeight(5);        
        grid.getRowConstraints().add(row0);
        
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(5);        
        grid.getRowConstraints().add(row1);
        
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(90);        
        grid.getRowConstraints().add(row2);
        
        messages.setWrapText(true);
        
        Text title = new Text("CluedoTestServer");
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        status.setFont(Font.font("Tahoma", FontWeight.NORMAL, 10));
        
       
        grid.add(title, 0, 0, 2, 1);
        grid.add(startServerButton,0,1);
       // grid.add(startGameButton,1,1);
        grid.add(status, 1, 1);
        grid.add(clientList, 0, 2,2,3);
        grid.add(messages, 1, 2,1,3);
       

        primaryStage.setScene(new Scene(grid, width, height));
        primaryStage.show();
    }
}