package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Deck;
import enums.Persons;
import enums.Weapons;

public class ZugFenster extends Stage{

	/**
	 * Eine View Klasse die die erste Version des Spielerzueges repraesentiert.
	 * 
	 * @author NedkoChulev
	 *
	 */
		private Deck deck = new Deck(5);

		private Scene scene;
		private BorderPane fenster;
		private HBox buttons;
		private HBox bottom;
		private Button anklage = new Button("Anklage" + "\n" + "aeussern");

		private Button wurfel = new Button("Wuerfeln");
		private Button gang = new Button("Gang" + "\n" + "nehmen");
		private Button close = new Button("Close");
		private Button klagen = new Button("Anklage aeussern");

		private final ComboBox personen = new ComboBox();
		private final ComboBox waffen = new ComboBox();
		private final ComboBox zimmer = new ComboBox();

		/**
		 * Constructor fuer die erste Version von einem Spielerzug
		 * 
		 */
		public ZugFenster() {
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
			personen.getItems().addAll(Persons.getPersonsString());
			personen.setValue("Taeter");
			waffen.getItems().addAll(Weapons.getWeaponsString());
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

			fenster = new BorderPane();
			fenster.setStyle("-fx-background-image: url('media/ZugFensterResized.png');");
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
		
		public Button getClose(){
			return this.close;
		}
	}