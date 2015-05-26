package cluedoNetworkGUI;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
 
public class CluedoClientGUI {
	ObservableList<String> ips;
	ListView<String> clientList;
	final TextArea messages;
	final Text status;
	final public Button startClientButton;
	final public Button submitMessageButton;
	final public TextField inputField;
	GridPane grid;
	final public Stage primaryStage;
	
	 public CluedoClientGUI(Stage primaryStage){
		 this.primaryStage = primaryStage;
		 ips = FXCollections.observableArrayList();
		 clientList = new ListView<String>(ips);
		 messages = new TextArea();
		 status = new Text("down");
		 startClientButton = new Button("StartClient");
		 submitMessageButton = new Button("submitMessage");
		 inputField = new TextField();		 
		 grid = new GridPane();
		 startUp(primaryStage);
		 
	}
	  public void addClient(String ip){
		  ips.add(ip);
	  }
	  public void emptyList(){
		  ips = FXCollections.observableArrayList();
	  }
	  
	  public void setStatus(String stat){
		  status.setText(stat);
	  }
	  
	  public void addMessage(String mes){
		  messages.appendText(mes+"\n");
	  }
	  
	  public void clearMessages(){
		  messages.setText("");
	  }
    
    
    public void startUp(Stage primaryStage) {
        primaryStage.setTitle("CluedoTestClient");
        
        
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setGridLinesVisible(false);
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setFillWidth(true);
        columnConstraints.setHgrow(Priority.ALWAYS);
        grid.getColumnConstraints().add(columnConstraints);
        
       
        
        messages.setPrefRowCount(10);
        messages.setPrefColumnCount(100);
        messages.setWrapText(true);
        messages.setPrefWidth(400);        
        
        Text title = new Text("CluedoTestClient");
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        status.setFont(Font.font("Tahoma", FontWeight.NORMAL, 10));
        
       
        grid.add(title, 0, 0, 2, 1);
        grid.add(startClientButton,0,1);
        grid.add(status, 1, 1);
        grid.add(messages, 1, 2);
        grid.add(clientList, 0, 2);
       // grid.add(submitMessageButton,0, 3);
        //grid.add(inputField, 1,3);

        primaryStage.setScene(new Scene(grid, 700, 250));
        primaryStage.show();
    }
    
    public String askForIp() {
    	Stage ipPrompt = new Stage();
    	IpPromptGrid ipr = new IpPromptGrid(ipPrompt);
		Scene secondary = new Scene(ipr,300,200);		
		ipPrompt.setScene(secondary);
		ipPrompt.showAndWait();	
		
		return ipr.returnIp();
	}
    
    public String[] loginPrompt(){
    	Stage loginStage = new Stage();
    	LoginPrompt loginPrompt = new LoginPrompt(loginStage);
    	Scene secondary = new Scene(loginPrompt,300,200);		
		loginStage.setScene(secondary);
		loginStage.showAndWait();	
		
		return loginPrompt.returnLoginData();
    }
    
    public String getUserMessage(){
    	String m = inputField.getText();
    	inputField.setText("");
    	return m;
    }
}