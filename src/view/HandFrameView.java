package view;

import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import cluedoNetworkLayer.CluedoPlayer;



////CURRENTLY NOT PROPERLY WORKING SINCE PLAYERS HAVE NO CARDS YET

public class HandFrameView extends BorderPane{
	
	private ArrayList<ImageView> hand = new ArrayList<ImageView>();
	Label title;
	private StackPane stack = new StackPane();
	private HBox cards = new HBox();
	private Group selectedCard = new Group();
	
	String media = "media/";
	String type = ".png";
	
	public HandFrameView(){
		this.setMaxSize(350, 350);
		this.setPrefSize(350, 350);
		this.setMinSize(350, 350);
		
		hand.add(new ImageView(new Image(media + "red" + type)));
		hand.add(new ImageView(new Image(media + "blue" + type)));
		hand.add(new ImageView(new Image(media + "green" + type)));
		hand.add(new ImageView(new Image(media + "white" + type)));
		hand.add(new ImageView(new Image(media + "purple" + type)));
		
		title = new Label("Cards in hand:");
		
		this.setTop(title);
		this.cards.setSpacing(-30);
		this.cards.setAlignment(Pos.CENTER);
		this.cards.getChildren().addAll(hand);
		stack.getChildren().add(cards);
		this.setCenter(stack);
		
		
	}

	public ArrayList<ImageView> getHand() {
		return hand;
	}

	public void setHand(ArrayList<ImageView> hand) {
		this.hand = hand;
	}

	public HBox getCards() {
		return cards;
	}

	public void setCards(HBox cards) {
		this.cards = cards;
	}

	public Group getSelectedCard() {
		return selectedCard;
	}

	public void setSelectedCard(Group selectedCard) {
		this.selectedCard = selectedCard;
	}

	public StackPane getStack() {
		return stack;
	}

	public void setStack(StackPane stack) {
		this.stack = stack;
	}
	
	

}
