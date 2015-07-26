package view;


import java.util.ArrayList;

import staticClasses.Config;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class ShowKarten extends GridPane{
	
	private Stage parent;
	private Button closeButton = new Button("Close");
	private String karte;
	private String ueberschrift;
	private ArrayList<String> karten;
	
	public ShowKarten(Stage stage, String karte){
		this.parent = stage;
		this.ueberschrift = "Disprove";
		this.karte = karte;
		
		setHandlerDisprove();
		layoutGrid();
		setHandler();
	}
	
	public ShowKarten(Stage stage, ArrayList<String> karten){
		this.parent = stage;
		this.ueberschrift = "Poolcards";
		this.karten = karten;
		setHandlerPoolcards();
		layoutGrid();
		setHandler();
	}
	
	private void setHandlerDisprove(){
		Text status = new Text(ueberschrift);
		GridPane.setValignment(status, VPos.CENTER);
		GridPane.setConstraints(status, 0, 0);
		Image card = new Image ("media/" + karte + ".png");
		ImageView cardView = new ImageView(card);
		GridPane.setValignment(cardView, VPos.CENTER);
		GridPane.setConstraints(cardView, 0, 1);
		GridPane.setValignment(closeButton, VPos.CENTER);
		GridPane.setConstraints(closeButton, 0, 1);
		this.getChildren().addAll(status, cardView, closeButton);
		
		ColumnConstraints col0 = new ColumnConstraints();
        col0.setHgrow(Priority.ALWAYS);
        
	}
	
	private void setHandlerPoolcards(){
		Config.SHOWKARTEN_WIDTH = 0;
		int counter = 0;
		int columnCounter = 0;
		for (String s : karten){
			Config.SHOWKARTEN_WIDTH += 150;
			Image card = new Image ("media/" + s + ".png");
			ImageView cardView = new ImageView(card);
			GridPane.setValignment(cardView, VPos.CENTER);
			GridPane.setConstraints(cardView, columnCounter, 1);
			this.getChildren().add(cardView);
			columnCounter++;
			counter++;
		}
		
		for(int i = 0; i < counter; i++){
			ColumnConstraints col0 = new ColumnConstraints();
	        col0.setHgrow(Priority.ALWAYS);
	        col0.setPercentWidth(100/counter);
		}
		
		this.getChildren().add(closeButton);
	}
	
	public void layoutGrid(){
		 this.setPadding(new Insets(15));
		  this.setHgap(20);
		  this.setVgap(5);
	      this.setGridLinesVisible(false);
	      this.setAlignment(Pos.CENTER);
	      
	      Rectangle rect = new Rectangle(415, 400);
	      rect.setFill(Color.WHITE);
	      rect.setArcHeight(30);
	      rect.setArcWidth(30);

	      this.setClip(rect);
	        
	      ColumnConstraints col0 = new ColumnConstraints();
	      col0.setMaxWidth(160);
	      col0.setPrefWidth(160);
	      this.getColumnConstraints().add(col0);
	}
	
	public void setHandler() {
		closeButton.setOnMouseClicked(e -> {
			parent.close();
		});
	}
	
	
	
	
	
}
