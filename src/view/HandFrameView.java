package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class HandFrameView extends StackPane{
	
	private ImageView firstCard;
	private ImageView secondCard;
	private ImageView thirdCard;
	private Label title;
	
	final Image img =
		      new Image("http://vignette3.wikia.nocookie.net/ageofempires/images/2/23/Hades.jpg/revision/latest?cb=20110606234954");
	
	public HandFrameView(){
		
		title = new Label("Cards in hand:");
		
		firstCard = new ImageView(img);
		secondCard = new ImageView(img);
		thirdCard = new ImageView(img);
		
		this.setMaxSize(350, 350);
		this.setPrefSize(350, 350);
		this.setMinSize(350, 350);
		
		StackPane.setAlignment(title, Pos.TOP_LEFT);
		StackPane.setAlignment(firstCard, Pos.CENTER_LEFT);
		StackPane.setAlignment(secondCard, Pos.CENTER);
		StackPane.setAlignment(thirdCard, Pos.CENTER_RIGHT);
		StackPane.setMargin(firstCard, new Insets(30,30,30,30));
		StackPane.setMargin(secondCard, new Insets(0,0,0,0));
		StackPane.setMargin(thirdCard, new Insets(30,30,30,30));
		
		this.getChildren().addAll(title, thirdCard, secondCard, firstCard);
		
		
	}

	//Getters and Setters
	public ImageView getFirstCard() {
		return firstCard;
	}

	public void setFirstCard(ImageView firstCard) {
		this.firstCard = firstCard;
	}

	public ImageView getSecondCard() {
		return secondCard;
	}

	public void setSecondCard(ImageView secondCard) {
		this.secondCard = secondCard;
	}

	public ImageView getThirdCard() {
		return thirdCard;
	}

	public void setThirdCard(ImageView thirdCard) {
		this.thirdCard = thirdCard;
	}

	public Label getTitle() {
		return title;
	}

	public void setTitle(Label title) {
		this.title = title;
	}
	
	

}
