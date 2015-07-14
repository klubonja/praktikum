package view;

import java.util.ArrayList;

import staticClasses.auxx;
import model.Deck;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.Deck;
import cluedoNetworkLayer.CluedoGameClient;
import cluedoNetworkLayer.CluedoPlayer;

public class HandFrameView extends BorderPane {

	private Deck deck = new Deck(6);
	private ArrayList<ImageView> hand = new ArrayList<ImageView>();
	private ArrayList<String> handURI = new ArrayList<String>();
	private CluedoGameClient client;
	Label title;
	private Label happening;
	private Label text;
	private VBox top = new VBox();
	private StackPane stack = new StackPane();
	private HBox cards = new HBox();
	private Group selectedCard = new Group();
	private VBox accusationBox = new VBox();
	private HBox lists = new HBox();
	private Button accuse = new Button("Accuse!");
	private Button endTurn = new Button("End turn");

	private ComboBox<String> persons = new ComboBox<String>();
	private ComboBox<String> weapons = new ComboBox<String>();
	private ComboBox<String> rooms = new ComboBox<String>();

	public HandFrameView(CluedoGameClient client) {
		this.client = client;
		this.setMaxSize(350, 350);
		this.setPrefSize(350, 350);
		this.setMinSize(350, 350);
		
//		ArrayList<String> cardstmp = player.getCards();
//		for(String str : cardstmp){
//			auxx.logfine("imagestring :" + str);
//			
//			hand.add(new ImageView(new Image("media/" + str + ".png")));}
			
		deck.makeDeck();
		
		for (CluedoPlayer p : client.getPlayersConnected()) {
			if (p.getNick().equals(client.getMyNick())) {
				for (String str : p.getCards()) {
					handURI.add(str);
					hand.add(new ImageView(new Image("media/" + str + ".png")));
				}
			}
		}

		title = new Label("Cards in hand. Click on a card to view it.");
		happening = new Label("Currently happening: ");
		text = new Label();
		top.getChildren().addAll(happening, text);
		
		persons.getItems().addAll("red", "yellow", "white", "green", "blue", "purple");
		persons.setValue("Person");
		persons.setMinWidth(110);
		persons.setMaxWidth(110);
		weapons.getItems().addAll(deck.getWeapons());
		weapons.setValue("Weapon");
		weapons.setMinWidth(110);
		weapons.setMaxWidth(110);
		rooms.getItems().addAll(deck.getRooms());
		rooms.setValue("Room");
		rooms.setMinWidth(110);
		rooms.setMaxWidth(110);

		lists.getChildren().addAll(persons, weapons, rooms);
		accusationBox.getChildren().addAll(lists, accuse, endTurn);

		accusationBox.setAlignment(Pos.CENTER);
		lists.setAlignment(Pos.CENTER);

		this.setTop(top);
		this.setBottom(accusationBox);
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

	public Button getAccuse() {
		return accuse;
	}

	public void setAccuse(Button accuse) {
		this.accuse = accuse;
	}

	public ComboBox<String> getPersons() {
		return persons;
	}

	public ComboBox<String> getWeapons() {
		return weapons;
	}

	public ComboBox<String> getRooms() {
		return rooms;
	}

	public void setPersons(ComboBox<String> persons) {
		this.persons = persons;
	}

	public void setWeapons(ComboBox<String> weapons) {
		this.weapons = weapons;
	}

	public void setRooms(ComboBox<String> rooms) {
		this.rooms = rooms;
	}

	public Button getEndTurn() {
		return endTurn;
	}

	public void setEndTurn(Button endTurn) {
		this.endTurn = endTurn;
	}

	public ArrayList<String> getHandURI() {
		return handURI;
	}

	public void setHandURI(ArrayList<String> handURI) {
		this.handURI = handURI;
	}

	public Label getText() {
		return text;
	}

	public void setText(Label text) {
		this.text = text;
	}

}
