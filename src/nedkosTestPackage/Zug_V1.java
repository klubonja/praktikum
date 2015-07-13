package nedkosTestPackage;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Eine View Klasse die die erste Version des Spielerzueges repraesentiert.
 * 
 * @author NedkoChulev
 *
 */
public class Zug_V1 extends BorderPane {
	Deck deck = new Deck();

	private HBox buttons;
	private HBox bottom;
	VBox organizer;

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

	private Button close = new Button("Close");
	private Button klagen = new Button("Anklage Aeussern");

	boolean anklageControl = false;
	boolean wurfelControl = false;
	boolean gangControl = false;
	boolean klagenControl = false;
	boolean vermutenControl = false;

	@SuppressWarnings("rawtypes")
	private final ComboBox personen = new ComboBox();
	@SuppressWarnings("rawtypes")
	private final ComboBox waffen = new ComboBox();
	@SuppressWarnings("rawtypes")
	final ComboBox zimmer = new ComboBox();

	/**
	 * Constructor fuer die erste Version von einem Spielerzug
	 * 
	 */
	public Zug_V1() {
		setLayout();
	}

	/**
	 * Baut den Layout Manager auf und fuegt ide Buttons usw. ein
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void setLayout() {
		personen.getItems().addAll(deck.getPersonenOrdered());
		personen.setValue("Taeter");
		personen.setMaxWidth(110);
		waffen.getItems().addAll(deck.getWaffenOrdered());
		waffen.setValue("Waffe");
		waffen.setMaxWidth(110);
		// zimmer.getItems().addAll(deck.getZimmerOrdered());
		zimmer.setValue("Raum");
		zimmer.setMaxWidth(110);

		buttons = new HBox();
		buttons.setSpacing(10);
		buttons.setAlignment(Pos.CENTER);
		buttons.getChildren().addAll(NOvermutenImage, NOwurfelImage,
				NOgangImage);

		bottom = new HBox();
		bottom.setSpacing(150);
		bottom.setAlignment(Pos.CENTER);
		bottom.getChildren().add(close);

		organizer = new VBox();
		organizer.setSpacing(10);
		organizer.setAlignment(Pos.CENTER);
		organizer.getChildren().addAll(buttons, bottom, close);

		this.setStyle("-fx-background-image: url('media/ZugFensterResized2.png');");
		this.setCenter(organizer);
		this.setWidth(450);
		this.setHeight(300);
	}

	public void killEmAll() {
		removeButtons();
		buttons.getChildren().removeAll(ONanklage, ONvermuten, OFFanklage,
				OFFvermuten, personen, waffen, zimmer);
	}

	public void removeButtons() {
		buttons.getChildren().removeAll(NOvermutenImage, YESvermutenImage,
				NOwurfelImage, YESwurfelImage, NOgangImage, YESgangImage);
	}

	public void addInactiveButtons() {
		buttons.getChildren().addAll(NOvermutenImage, NOwurfelImage,
				NOgangImage);
	}

	public Button getAnklageButton() {
		return klagen;
	}

	public HBox getButtonsBox() {
		return this.buttons;
	}

	public HBox getBottomBox() {
		return bottom;
	}

	@SuppressWarnings("rawtypes")
	public ComboBox getPersonenListe() {
		return this.personen;
	}

	@SuppressWarnings("rawtypes")
	public ComboBox getWaffenListe() {
		return this.waffen;
	}

	@SuppressWarnings("rawtypes")
	public ComboBox getZimmerListe() {
		return this.zimmer;
	}

	public Button getClose() {
		return close;
	}

	public void setClose(Button close) {
		this.close = close;
	}
}