package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Deck;

/**
 * Eine View Klasse die die erste Version des Spielerzueges repraesentiert.
 * 
 * @author NedkoChulev
 *
 */
public class AussergewohnlichesZugfenster extends BorderPane {
	private Deck deck = new Deck(6);

	private HBox buttons;
	private HBox bottom;
	private HBox vermuten;
	private VBox organizer;

	public ImageView YESvermutenImage = new ImageView(new Image(
			"media/YESanklage.png"));
	public ImageView NOvermutenImage = new ImageView(new Image(
			"media/NOanklage.png"));
	public ImageView YESgangImage = new ImageView(
			new Image("media/YESgang.png"));
	public ImageView NOgangImage = new ImageView(new Image("media/NOgang.png"));
	public ImageView YESwurfelImage = new ImageView(new Image(
			"media/YESwurfeln.png"));
	public ImageView NOwurfelImage = new ImageView(new Image(
			"media/NOwurfeln.png"));
	public ImageView OFFanklage = new ImageView(new Image(
			"media/OFFanklage.png"));
	public ImageView ONanklage = new ImageView(new Image("media/ONanklage.png"));
	public ImageView OFFvermuten = new ImageView(new Image(
			"media/OFFvermuten.png"));
	public ImageView ONvermuten = new ImageView(new Image(
			"media/ONvermuten.png"));

	private Button close = new Button("Better not click me, I close the window");

	private ComboBox<String> personen = new ComboBox<String>();
	private ComboBox<String> waffen = new ComboBox<String>();
	private Button zimmer = new Button("You're not in a room, Harry!");

	/**
	 * Constructor fuer die erste Version von einem Spielerzug
	 * 
	 */
	public AussergewohnlichesZugfenster() {
		setLayout();
	}

	/**
	 * Baut den Layout Manager auf und fuegt ide Buttons usw. ein
	 * 
	 */
	public void setLayout() {
		personen.getItems().addAll("red", "yellow", "white", "green", "blue", "purple");
		personen.setValue("Person");
		personen.setMaxWidth(110);
		waffen.getItems().addAll(deck.getWeapons());
		waffen.setValue("Weapon");
		waffen.setMaxWidth(110);
		zimmer.setMinWidth(110);

		
		vermuten = new HBox();
		vermuten.setSpacing(10);
		vermuten.setAlignment(Pos.CENTER);
		vermuten.getChildren().addAll(personen, waffen, zimmer);

		buttons = new HBox();
		buttons.setSpacing(10);
		buttons.setAlignment(Pos.CENTER);
		buttons.getChildren().addAll(NOvermutenImage, NOwurfelImage,NOgangImage);
		

		bottom = new HBox();
		bottom.setSpacing(150);
		bottom.setAlignment(Pos.CENTER);
		bottom.getChildren().add(close);

		organizer = new VBox();
		organizer.setSpacing(10);
		organizer.setAlignment(Pos.CENTER);
		organizer.getChildren().addAll(buttons, bottom, close);

		this.setStyle("-fx-background-image: url('media/ZugFensterResized3.png');");
		this.setCenter(organizer);
		this.setWidth(450);
		this.setHeight(300);
	}

	public void killEmAll() {
		buttons.getChildren().removeAll(personen, waffen, zimmer);
		bottom.getChildren().removeAll(ONanklage, ONvermuten, OFFanklage,
				OFFvermuten, close);
	}

	public void addInactiveButtons() {
		buttons.getChildren().addAll(NOvermutenImage, NOwurfelImage,
				NOgangImage);
	}

	public HBox getButtonsBox() {
		return this.buttons;
	}

	public HBox getBottomBox() {
		return bottom;
	}

	public ComboBox<String> getPersonenListe() {
		return this.personen;
	}

	public ComboBox<String> getWaffenListe() {
		return this.waffen;
	}

	public Button getZimmer() {
		return this.zimmer;
	}
	
	public void setZimmer(String room){
		this.zimmer.setText(room);
	}

	public Button getClose() {
		return close;
	}

	public void setClose(Button close) {
		this.close = close;
	}

	public HBox getVermuten() {
		return vermuten;
	}

	public void setVermuten(HBox vermuten) {
		this.vermuten = vermuten;
	}

	public VBox getOrganizer() {
		return organizer;
	}

	public void setOrganizer(VBox organizer) {
		this.organizer = organizer;
	}
}