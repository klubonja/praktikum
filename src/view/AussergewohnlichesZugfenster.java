package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Deck;
import staticClasses.Images;

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

	private Button close = new Button("Back");

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
		buttons.getChildren().addAll(
				Images.NOanklageImage,
				Images.NOwurfelImage,
				Images.NOgangImage);
		

		bottom = new HBox();
		bottom.setSpacing(150);
		bottom.setAlignment(Pos.CENTER);

		organizer = new VBox();
		organizer.setSpacing(10);
		organizer.setAlignment(Pos.CENTER);
		organizer.getChildren().addAll(buttons);

		this.setBackground(
				new Background(
						new BackgroundImage(
								Images.zug.getImage(),
								BackgroundRepeat.NO_REPEAT,
								BackgroundRepeat.NO_REPEAT,
								BackgroundPosition.DEFAULT,
								BackgroundSize.DEFAULT)
						)
				);
		this.setCenter(organizer);
		this.setWidth(450);
		this.setHeight(300);
	}

	public void killEmAll() {
		buttons.getChildren().removeAll(personen, waffen, zimmer);
		bottom.getChildren().removeAll(
				Images.ONanklage,
				Images.OFFanklage,
				close);
	}

	public void addInactiveButtons() {
		buttons.getChildren().addAll(
				Images.NOanklageImage,
				Images.NOwurfelImage,
				Images.NOgangImage);
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