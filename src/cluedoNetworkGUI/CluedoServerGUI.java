package cluedoNetworkGUI;

import javafx.scene.control.Tab;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import staticClasses.Config;
 
public class CluedoServerGUI extends CluedoNetworkGUI {
	 
	 public CluedoServerGUI(Stage s){
		 super(s);
		 
		 width = Config.SERVER_WINDOW_WIDTH;
		 height = Config.SERVER_WINDOW_HEIGHT;
		 
		 setStylesheet("cluedoNetworkGUI/networkStyle.css");

		 setStartServiceButtonLabel("sendhandshake");	
		 startUp();	 
		 setStageWidth(width);
		 setStageHeight(height);
	}
    
    
	public void startUpN() {	    
	   
	    //grid.setPadding(new Insets(25, 25, 25, 25));
	    grid.setGridLinesVisible(false);
	    
	    ColumnConstraints col0 = new ColumnConstraints();
	    col0.setHgrow(Priority.ALWAYS);
	    col0.setPercentWidth(40);
	    grid.getColumnConstraints().add(col0);
	    
	    ColumnConstraints col1 = new ColumnConstraints();
	    col1.setHgrow(Priority.ALWAYS);
	    col1.setPercentWidth(60);
	    grid.getColumnConstraints().add(col1);
	    
	    RowConstraints row0 = new RowConstraints();
	    row0.setPrefHeight(30);       
	    grid.getRowConstraints().add(row0);
	    
	    RowConstraints row1 = new RowConstraints();
	    row1.setPercentHeight(5);        
	    grid.getRowConstraints().add(row1);
	    
	    RowConstraints row2 = new RowConstraints();
	    row1.setPercentHeight(5);        
	    grid.getRowConstraints().add(row2);
	    
	    RowConstraints row3 = new RowConstraints();
	    row3.setPercentHeight(45);        
	    grid.getRowConstraints().add(row3);
	    
	    RowConstraints row4 = new RowConstraints();
	    row1.setPercentHeight(5);        
	    grid.getRowConstraints().add(row4);
	    
	    RowConstraints row5 = new RowConstraints();
	    row5.setPercentHeight(45);        
	    grid.getRowConstraints().add(row5);
	    
	    messagesIn.setWrapText(true);
	    messagesOut.setWrapText(true);
		messagesIn.setEditable(false);
		messagesOut.setEditable(false);
        
        Tab tab0 = new Tab();
        tab0.setText("Spiele");           
        tab0.setContent(gameListView);
        
        
        Tab tab1 = new Tab();
        tab1.setText("Clients");           
        tab1.setContent(networkActorsListView);
        
        tabPane.getTabs().add(tab1);
        tabPane.getTabs().add(tab0);    
	    
	    Text title = new Text(desc);
	    title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	    status.setFont(Font.font("Tahoma", FontWeight.NORMAL, 10));
	    
	   //grid.add(node,				col,row,colspan,rowspan)
	    grid.add(menue, 			0, 0, 2, 1);
	    grid.add(tabPane, 			0, 2, 2, 4);
	    grid.add(inLabel, 			1, 2, 1, 1);
	    grid.add(messagesIn, 		1, 3, 1, 1);
	    grid.add(outLabel, 			1, 4, 1, 1);
	    grid.add(messagesOut, 		1, 5, 1, 1);
	   
	
	    primaryStage.setScene(scene);
	    primaryStage.show();
     }   
    
    @Override
	public void startUp() {        
        
        // grid.setPadding(new Insets(25, 25, 25, 25));
         grid.setGridLinesVisible(false);
         
         ColumnConstraints col0 = new ColumnConstraints();
         col0.setHgrow(Priority.ALWAYS);
         col0.setPercentWidth(40);
         grid.getColumnConstraints().add(col0);
         
         ColumnConstraints col1 = new ColumnConstraints();
         col1.setHgrow(Priority.ALWAYS);
         col1.setPercentWidth(60);
         grid.getColumnConstraints().add(col1);
         
         RowConstraints row0 = new RowConstraints(); //menue
 	    row0.setPercentHeight(6); 
 	    grid.getRowConstraints().add(row0);
 	    
 	    RowConstraints row1 = new RowConstraints();
 	    row1.setPercentHeight(6);        
 	    grid.getRowConstraints().add(row1);
 	    
// 	    RowConstraints row2 = new RowConstraints();
// 	    row1.setPercentHeight(5);        
// 	    grid.getRowConstraints().add(row2);
 	    
 	    RowConstraints row3 = new RowConstraints();
 	    row3.setPercentHeight(52);        
 	    grid.getRowConstraints().add(row3);
 	    
 	    RowConstraints row4 = new RowConstraints();
 	    row4.setPercentHeight(35);        
 	    grid.getRowConstraints().add(row4);
 	    
// 	    RowConstraints row5 = new RowConstraints();
// 	    row5.setPercentHeight(40);        
// 	    grid.getRowConstraints().add(row5);
 	    
// 	    RowConstraints row6 = new RowConstraints();
// 	    row6.setPercentHeight(10);
// 	    //row6.setVgrow(Priority.ALWAYS);
// 	    grid.getRowConstraints().add(row6);
         
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
         
         //menue.getChildren().addAll(createGame,connectToTestServer,refreshGamesList);
         
         
         Text title = new Text(desc);
         title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
         status.setFont(Font.font("Tahoma", FontWeight.NORMAL, 10));
         
         //messagesIn.setPrefHeight(150);
       
        // grid.setValignment(submitMessageButton, VPos.CENTER);
        
        //grid.add(node,				  col,row,colspan,rowspan)
         grid.add(menue, 				0, 0, 	2, 		1);
         grid.add(statusContainer,   	0, 1, 	2, 		1);
 	    grid.add(networkActorsListView, 0, 2, 	1, 		1);
 	    //grid.add(submitMessageButton, 0, 5, 	1, 		1);
 	    grid.add(gameListView, 			1, 2,	1, 		1);
 	    
// 	    grid.add(inLabel, 				1, 2, 	1, 		1);
// 	    grid.add(messagesIn, 			1, 3, 	1, 		1);
// 	    grid.add(outLabel, 				1, 4, 1, 1);
// 	    grid.add(messagesOut, 			1, 5, 1, 1);	    
 	    grid.add(inputField, 			0, 3, 	1, 		1);
 	    grid.add(messagesIn,			1, 3, 	1, 		1);
 	    
        

         primaryStage.setScene(scene);
         //primaryStage.show(); // wir brauchen keine gui
     }
    
	void setAdditionalListener(){
			  
		  
	  }
}