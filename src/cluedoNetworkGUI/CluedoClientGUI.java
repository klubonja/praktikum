package cluedoNetworkGUI;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import staticClasses.Config;


 
public class CluedoClientGUI extends CluedoNetworkGUI{
	
	
	final public Button submitMessageButton;
	final public Button createGame;
	final public Button connectToTestServer;
	final public Button refreshGamesList;
	final public ObservableList<String> clientNicks;
	final public ListView<String> clientNicksView;

	
	
	
	 public CluedoClientGUI(Stage primaryStage){
		 super(primaryStage);
		 submitMessageButton = new Button("Send");
		 createGame = new Button("Create Game");
		 connectToTestServer = new Button("TestServerConnection");
		 refreshGamesList = new Button("refreshGamesList");
		 clientNicks =  FXCollections.observableArrayList();
		 clientNicksView = new ListView<String>(clientNicks);
		 width = Config.CLIENT_WINDOW_WIDTH;
		 height = Config.CLIENT_WINDOW_HEIGHT;
		 
		 setStageWidth(width);
		 setStageHeight(height);
		 
		 setStartServiceButtonLabel("senddhandshake");
		 setStylesheet("cluedoNetworkGUI/networkStyle.css");
		 startUp();	 		 
	}
	    
	@Override
	public void startUp() {        
        
       // grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setGridLinesVisible(false);
        
        ColumnConstraints col0 = new ColumnConstraints();
        col0.setHgrow(Priority.ALWAYS);
        col0.setPercentWidth(35);
        grid.getColumnConstraints().add(col0);
        
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.ALWAYS);
        col1.setPercentWidth(20);
        grid.getColumnConstraints().add(col1);
        
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        col2.setPercentWidth(45);
        grid.getColumnConstraints().add(col2);
        
        RowConstraints row0 = new RowConstraints(); //menue
	    row0.setPercentHeight(6); 
	    grid.getRowConstraints().add(row0);
	    
	    RowConstraints row1 = new RowConstraints();
	    row1.setPercentHeight(6);        
	    grid.getRowConstraints().add(row1);
	    
//	    RowConstraints row2 = new RowConstraints();
//	    row1.setPercentHeight(5);        
//	    grid.getRowConstraints().add(row2);
	    
	    RowConstraints row3 = new RowConstraints();
	    row3.setPercentHeight(52);        
	    grid.getRowConstraints().add(row3);
	    
	    RowConstraints row4 = new RowConstraints();
	    row4.setPercentHeight(35);        
	    grid.getRowConstraints().add(row4);
	    
//	    RowConstraints row5 = new RowConstraints();
//	    row5.setPercentHeight(40);        
//	    grid.getRowConstraints().add(row5);
	    
//	    RowConstraints row6 = new RowConstraints();
//	    row6.setPercentHeight(10);
//	    //row6.setVgrow(Priority.ALWAYS);
//	    grid.getRowConstraints().add(row6);
        
        messagesIn.setWrapText(true);
        messagesOut.setWrapText(true);
     
        Tab tab0 = new Tab();
        tab0.setText("Spiele");           
        tab0.setContent(gameListView);
              
        
        Tab tab1 = new Tab();
        tab1.setText("Server");           
        tab1.setContent(networkActorsListView);
        
        tabPane.getTabs().add(tab1);
        tabPane.getTabs().add(tab0);  
        
        menue.getChildren().addAll(createGame,connectToTestServer,refreshGamesList);
        
        
        Text title = new Text(desc);
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        status.setFont(Font.font("Tahoma", FontWeight.NORMAL, 10));
        
        //messagesIn.setPrefHeight(150);
      
        grid.setValignment(submitMessageButton, VPos.CENTER);
       
       //grid.add(node,				  col,row,colspan,rowspan)
        grid.add(menue, 				0, 0, 	3, 		1);
        grid.add(statusContainer,   	0, 1, 	3, 		1);
	    grid.add(networkActorsListView, 0, 2, 	1, 		1);
	    grid.add(clientNicksView,       1, 2, 	1, 		1);
	    grid.add(gameListView, 			2, 2,	1, 		1);
	    
//	    grid.add(inLabel, 				1, 2, 	1, 		1);
//	    grid.add(messagesIn, 			1, 3, 	1, 		1);
//	    grid.add(outLabel, 				1, 4, 1, 1);
//	    grid.add(messagesOut, 			1, 5, 1, 1);	    
	    grid.add(inputField, 			0, 3, 	2, 		1);
	    grid.add(messagesIn,			1, 3, 	2, 		1);
	    
       

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public String askForIp() {
    	Stage ipPrompt = new Stage();
    	IpPromptGrid ipr = new IpPromptGrid(ipPrompt);
		Scene secondary = new Scene(ipr,Config.IP_PROMPT_WINDOW_WIDTH,Config.IP_PROMPT_WINDOW_HEIGHT);		
		ipPrompt.setScene(secondary);
		ipPrompt.showAndWait();	
		
		return ipr.returnIp();
	}
    
    public String[] loginPrompt(String stageTitle){
    	Stage loginStage = new Stage();
    	LoginPrompt loginPrompt = new LoginPrompt(loginStage);
    	Scene secondary = new Scene(loginPrompt,Config.LOGIN_PROMPT_WINDOW_WIDTH,Config.LOGIN_PROMPT_WINDOW_HEIGHT);		
		loginStage.setScene(secondary);
		loginStage.setTitle(stageTitle);
		loginStage.showAndWait();	
		
		return loginPrompt.returnLoginData();
    }
     
    
    public String getUserMessage(){
    	String m = inputField.getText();
    	inputField.setText("");
    	return m;
    }  
   
	  
	public void clearMessages(){
		  messagesIn.setText("");
	}
	  
	public String selectColor(ArrayList<String> colors) {
		Stage selectNewColor = new Stage();
		IntroColorPrompt select = new IntroColorPrompt(selectNewColor, colors);
	
	    Scene secondary = new Scene(select, Config.COLOR_SELECT_WINDOW_WIDTH, Config.COLOR_SELECT_WINDOW_HEIGHT);
	    selectNewColor.initStyle(StageStyle.UNDECORATED);
	    selectNewColor.setScene(secondary);
	    selectNewColor.showAndWait();
	    return select.returnColor();
	}
	
	public void addClient(String nick){
		Platform.runLater(() -> {
			clientNicks.add(nick);
			System.out.println(nick+" single added");
		});
		
	}
	
	public void removeClient(String nick){
		Platform.runLater(() -> {
			clientNicks.remove(nick);
		});
		
	}
	
	public void setClientNicks(ArrayList<String> nicks){
		Platform.runLater(() -> {
			clientNicks.clear();
			for (String nick : nicks){
				addClient(nick);
			}				
		});	
	}
}