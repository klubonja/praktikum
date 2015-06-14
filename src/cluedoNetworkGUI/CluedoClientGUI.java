package cluedoNetworkGUI;


import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import staticClasses.Config;
 
public class CluedoClientGUI extends CluedoNetworkGUI{
	
	
	final public Button submitMessageButton;
	final public TextArea inputField;
	final public Button manualIPConnect;
	
	 public CluedoClientGUI(Stage primaryStage){
		 super(primaryStage);
		 submitMessageButton = new Button("Send");
		 inputField = new TextArea();	
		 width = Config.CLIENT_WINDOW_WIDTH;
		 height = Config.CLIENT_WINDOW_HEIGHT;
		 manualIPConnect = new Button("Connect to IP Address manually");
		 
		 setStageWidth(width);
		 setStageHeight(height);
		 

		 setStartServiceButtonLabel("Send Handshake");	

		 setStylesheet("cluedoNetworkGUI/networkStyle.css");

		 startUp();	 		 
	}
	    
	@Override
	public void startUp() {        
        
       // grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setGridLinesVisible(false);
        
        ColumnConstraints col0 = new ColumnConstraints();
        col0.setHgrow(Priority.ALWAYS);
        col0.setPercentWidth(20);
        grid.getColumnConstraints().add(col0);
        
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.ALWAYS);
        col1.setPercentWidth(40);
        grid.getColumnConstraints().add(col1);
        
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        col2.setPercentWidth(40);
        grid.getColumnConstraints().add(col2);
        
        RowConstraints row0 = new RowConstraints();
	    row0.setPercentHeight(5);    
	    grid.getRowConstraints().add(row0);
	    
	    RowConstraints row1 = new RowConstraints();
	    row1.setPercentHeight(5);        
	    grid.getRowConstraints().add(row1);
	    
	    RowConstraints row2 = new RowConstraints();
	    row1.setPercentHeight(5);        
	    grid.getRowConstraints().add(row2);
	    
	    RowConstraints row3 = new RowConstraints();
	    row3.setPercentHeight(40);        
	    grid.getRowConstraints().add(row3);
	    
	    RowConstraints row4 = new RowConstraints();
	    row1.setPercentHeight(5);        
	    grid.getRowConstraints().add(row4);
	    
	    RowConstraints row5 = new RowConstraints();
	    row5.setPercentHeight(40);        
	    grid.getRowConstraints().add(row5);
	    
	    RowConstraints row6 = new RowConstraints();
	    row6.setPercentHeight(10);
	    //row6.setVgrow(Priority.ALWAYS);
	    grid.getRowConstraints().add(row6);
        
        messagesIn.setWrapText(true);
        messagesOut.setWrapText(true);
        
        Tab tab0 = new Tab();
        tab0.setText("Game list");           
        tab0.setContent(gameListView);
        tabPane.getTabs().add(tab0);            
        
        Tab tab1 = new Tab();
        tab1.setText("Server List");           
        tab1.setContent(ipListView);
        tabPane.getTabs().add(tab1);    
        
        
        Text title = new Text(desc);
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        status.setFont(Font.font("Tahoma", FontWeight.NORMAL, 10));
      
        grid.setValignment(submitMessageButton, VPos.CENTER);
			
       
       //grid.add(node,				col,row,colspan,rowspan)
        grid.add(title, 				0, 0, 2, 1);
	    grid.add(startService,		 	0, 1);
	    grid.add(tabPane, 				0, 2, 2, 4);
	    grid.add(submitMessageButton, 	0, 6, 1, 1);
	    grid.add(status, 				1, 1);
	    grid.add(manualIPConnect, 		2, 1);
	    grid.add(inLabel, 				1, 2, 2, 1);
	    grid.add(messagesIn, 			1, 3, 2, 1);
	    grid.add(outLabel, 				1, 4, 2, 1);
	    grid.add(messagesOut, 			1, 5, 2, 1);	    
	    grid.add(inputField, 			1, 6, 2, 1);
	    
       

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
    
    @Override
	public void addGame(String gamename,String info){
		for (Pane p: games)
       		if (p.getId().equals(gamename)) return;
		 	
		Pane pane = new Pane();
		pane.setPrefHeight(50);
		pane.setPrefWidth(400);
		pane.getStyleClass().add("gameListItem");
		Label gameName = new Label(gamename);
		gameName.getStyleClass().add("gameNameItem");
		Label gameInfo = new Label("Connected plyaers :" +info);
		gameInfo.getStyleClass().add("gameInfoItem");
		gameInfo.setLayoutY(14);
		pane.setId(gamename);
		pane.getChildren().addAll(gameInfo,gameName);
		games.add(pane);		     
  }  
    
    public String getUserMessage(){
    	String m = inputField.getText();
    	inputField.setText("");
    	return m;
    }  
   
	  
	  public void clearMessages(){
		  messagesIn.setText("");
	  }
}