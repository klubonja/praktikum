package cluedoNetworkGUI;



import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import staticClasses.Config;
 
public class CluedoServerGUI extends CluedoNetworkGUI {
		
	 TabPane tabPane;
	 ObservableList<Pane> games;
	 ListView<Pane> gameListView;
	 
	 public CluedoServerGUI(Stage s){
		 super(s);
		 
		 width = Config.SERVER_WINDOW_WIDTH;
		 height = Config.SERVER_WINDOW_HEIGHT;
		 tabPane = new TabPane();
		 games = FXCollections.observableArrayList();
		 gameListView = new ListView<Pane>(games);
		 
		 setStartServiceButtonLabel("sendhandshake");	
		 startUp();	 
		 setStageWidth(width);
		 setStageHeight(height);
	}
    
    @Override
	public void startUp() {	    
	   
	    //grid.setPadding(new Insets(25, 25, 25, 25));
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
        tab0.setText("gamelist");           
        tab0.setContent(gameListView);
        tabPane.getTabs().add(tab0);            
        
        Tab tab1 = new Tab();
        tab1.setText("clientlist");           
        tab1.setContent(ipListView);
        tabPane.getTabs().add(tab1);    
	    
	    Text title = new Text(desc);
	    title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	    status.setFont(Font.font("Tahoma", FontWeight.NORMAL, 10));
	    
	   //grid.add(node,				col,row,colspan,rowspan)
	    grid.add(title, 			0, 0, 2, 1);
	    grid.add(startService, 		0, 1);
	    grid.add(tabPane, 			0, 2, 2, 4);
	    
	    grid.add(status, 			1, 1);
	    grid.add(inLabel, 			1, 2, 1, 1);
	    grid.add(messagesIn, 		1, 3, 1, 1);
	    grid.add(outLabel, 			1, 4, 1, 1);
	    grid.add(messagesOut, 		1, 5, 1, 1);
	   
	
	    primaryStage.setScene(scene);
	    primaryStage.show();
     }  
    
    
	public void addGame(String gamename,String info){
		for (Pane p: games)
       		if (p.getId().equals(gamename)) return;
		 	
		Pane pane = new Pane();
		pane.setPrefHeight(50);
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
   	 
	  
	public void removeGame(String gamename){
		  int index;
			for (index = 0; index < games.size(); index++)
	       		if (games.get(index).getId().equals(gamename)) games.remove(index);		 
	}
	  
	  @Override
	public  void emptyList(){
		  games.clear();
	  }
   
    @Override
	void setListener(){
		 super.setListener();
		
		  ChangeListener<Number> gridwidthlistener = new ChangeListener<Number>() {
				@Override
				public void changed(
						ObservableValue<? extends Number> observable,
						Number oldValue, Number newValue) {
							tabPane.setMaxWidth(newValue.doubleValue()/100*20);
						}							
					};
			grid.widthProperty().addListener(gridwidthlistener);
		  
	  }
}