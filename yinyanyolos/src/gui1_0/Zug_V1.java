package gui1_0;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Eine View Klasse die die erste Version des Spielerzueges repraesentiert.
 * 
 * @author NedkoChulev
 *
 */
public class Zug_V1 extends Stage {
	private Deck deck = new Deck();

	private Scene scene;
	private BorderPane fenster;
	private HBox buttons;
	private HBox bottom;
	private Button anklage = new Button("Anklage" + "\n" + "äußern");

	private Button wurfel = new Button("Würfeln");
	private Button gang = new Button("Gang" + "\n" + "nehmen");
	public Button close = new Button("Close");
	private Button klagen = new Button("Anklage äußern");

	private final ComboBox personen = new ComboBox();
	private final ComboBox waffen = new ComboBox();
	private final ComboBox zimmer = new ComboBox();

	/**
	 * Constructor fuer die erste Version von einem Spielerzug
	 * 
	 */
	public Zug_V1() {
		setLayout();
		this.initStyle(StageStyle.UNDECORATED);
		this.scene = new Scene(fenster, 450, 300);
		this.setScene(scene);
	}

	/**
	 * Baut den Layout Manager auf und fuegt ide Buttons usw. ein
	 * 
	 */
	public void setLayout() {
		personen.getItems().addAll(deck.getPersonenOrdered());
		personen.setValue("Täter");
		waffen.getItems().addAll(deck.getWaffenOrdered());
		waffen.setValue("Waffe");
		// zimmer.getItems().addAll(deck.getZimmerOrdered());
		zimmer.setValue("Raum");

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

		buttons = new HBox();
		buttons.setSpacing(20);
		buttons.setAlignment(Pos.CENTER);
		buttons.getChildren().addAll(anklage, wurfel, gang);

		bottom = new HBox();
		bottom.setSpacing(150);
		bottom.setAlignment(Pos.CENTER);
		bottom.getChildren().addAll(close);

		fenster = new BorderPane();
		fenster.setStyle("-fx-background-color: #33CC66;");
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

	public HBox getBottomBox() {
		return bottom;
	}

	public ComboBox getPersonenListe() {
		return this.personen;
	}

	public ComboBox getWaffenListe() {
		return this.waffen;
	}

	public ComboBox getZimmerListe() {
		return this.zimmer;
	}
}