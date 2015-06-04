package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class HandFrameView extends StackPane{
	
	ImageView firstCard;
	ImageView secondCard;
	ImageView thirdCard;
	Label title;
	
	final Image img =
		      new Image("http://vignette3.wikia.nocookie.net/ageofempires/images/2/23/Hades.jpg/revision/latest?cb=20110606234954");
	
	public HandFrameView(){
		
		title = new Label("Cards in hand:");
		
		firstCard = new ImageView(img);
		secondCard = new ImageView(img);
		thirdCard = new ImageView(img);
		
		this.setMaxSize(350, 450);
		this.setPrefSize(350, 450);
		this.setMinSize(350, 450);
		
		StackPane.setAlignment(title, Pos.TOP_LEFT);
		StackPane.setAlignment(firstCard, Pos.CENTER_LEFT);
		StackPane.setAlignment(secondCard, Pos.CENTER);
		StackPane.setAlignment(thirdCard, Pos.CENTER_RIGHT);
		StackPane.setMargin(firstCard, new Insets(30,30,30,30));
		StackPane.setMargin(secondCard, new Insets(0,0,0,0));
		StackPane.setMargin(thirdCard, new Insets(30,30,30,30));
		
		this.getChildren().addAll(title, thirdCard, secondCard, firstCard);
		
		
	}
	
	

}
