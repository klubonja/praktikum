package nedkosTestPackage;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Eine View Klasse die die zweite Version des Spielerzuges repraesentiert.
 * 
 * @author NedkoChulev
 *
 */
public class Zug_V2 extends Stage {
	private Deck deck = new Deck();

	public Image personImage = new Image("media/icon.jpg");
	public Image waffeImage = new Image("media/knife.jpg");
	public Image zimmerImage = new Image("media/house.jpg");

	private Scene scene;
	private BorderPane fenster;
	private HBox buttons;
	private HBox top;
	private HBox bottom;
	private Button anklage = new Button("Anklage" + "\n" + "äußern");

	private Button wurfel = new Button("Würfeln");
	private Button gang = new Button("Gang" + "\n" + "nehmen");
	public Button close = new Button("Close");
	private Button klagen = new Button("Anklage äußern");

	private final ComboBox personenListe = new ComboBox();
	private final ComboBox waffenListe = new ComboBox();
	private final ComboBox zimmerListe = new ComboBox();

	/**
	 * Constructor fuer die zweite Version des Spielzuges.
	 */
	public Zug_V2() {
		setLayout();
		this.initStyle(StageStyle.UNDECORATED);
		this.scene = new Scene(fenster, 500, 300);
		this.setScene(scene);
	}

	/**
	 * Baut den Layout Manager auf und fuegt jegliche Buttons und Unter-Layout
	 * Manager ein
	 */
	public void setLayout() {
		personenListe.getItems().addAll(deck.getPersonenOrdered());
		personenListe.setValue("Täter");
		waffenListe.getItems().addAll(deck.getWaffenOrdered());
		waffenListe.setValue("Waffe");
		// zimmerListe.getItems().addAll(deck.getZimmerOrdered());
		zimmerListe.setValue("Raum");

		anklage.setMinSize(80, 120);
		anklage.setMaxSize(80, 120);
		anklage.setTextFill(Color.BLACK);
		anklage.setStyle("-fx-background-color: #787878;");

		wurfel.setMinSize(80, 120);
		wurfel.setMaxSize(80, 120);
		wurfel.setTextFill(Color.BLACK);
		wurfel.setStyle("-fx-background-color: #787878;");

		gang.setMinSize(80, 120);
		gang.setMaxSize(80, 120);
		gang.setTextFill(Color.BLACK);
		gang.setStyle("-fx-background-color: #787878;");

		top = new HBox();
		top.setSpacing(1);
		top.setAlignment(Pos.CENTER);

		buttons = new HBox();
		buttons.setSpacing(20);
		buttons.setAlignment(Pos.CENTER);
		buttons.getChildren().addAll(anklage, wurfel, gang);

		bottom = new HBox();
		bottom.setSpacing(150);
		bottom.setAlignment(Pos.CENTER);
		bottom.getChildren().addAll(close);

		fenster = new BorderPane();
		fenster.setStyle("-fx-background-image: url('media/ZugFensterResized.png');");
		fenster.setTop(top);
		fenster.setCenter(buttons);
		fenster.setBottom(bottom);
	}

	public Button getAnklage() {
		return this.anklage;
	}

	public Button getWurfel() {
		return this.wurfel;
	}

	public Button getGang() {
		return this.gang;
	}

	public Button getAnklageButton() {
		return klagen;
	}

	public HBox getButtonsBox() {
		return this.buttons;
	}

	public HBox getTopBox() {
		return this.top;
	}

	public HBox getBottomBox() {
		return bottom;
	}

	public ComboBox getPersonenListe() {
		return this.personenListe;
	}

	public ComboBox getWaffenListe() {
		return this.waffenListe;
	}

	public ComboBox getZimmerListe() {
		return this.zimmerListe;
	}

	public Image getPersonImage() {
		return personImage;
	}

	public Image getWaffeImage() {
		return waffeImage;
	}

	public Image getZimmerImage() {
		return zimmerImage;
	}
}