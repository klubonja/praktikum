package gui1_0;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Die erste View von der man zu der ersten oder zweiten Version des
 * Spielerzueges navigieren kann oder das Deck verteilen kann)
 * 
 * @author NedkoChulev
 *
 */
public class ViewMitKarten extends Stage {

	private Scene scene;
	private BorderPane spielfeld;
	private HBox deck;
	private HBox unterMenu;
	private Button dealKarten = new Button("Deal");
	private Button zug = new Button("Zug_V1");
	private Button zug2 = new Button("Zug_V2");

	List<Rectangle> karten = new ArrayList<Rectangle>();
	Rectangle[] rects;

	/**
	 * Constructor fuer die View.
	 */
	public ViewMitKarten() {
		addCards();
		rects = karten.toArray(new Rectangle[0]);
		setLayout();
		this.scene = new Scene(spielfeld, 600, 600);
		this.setScene(scene);
	}

	/**
	 * Initialisiert den LayoutManager und fuegt die Karten ein.
	 */
	public void setLayout() {
		spielfeld = new BorderPane();
		unterMenu = new HBox();
		unterMenu.getChildren().addAll(dealKarten, zug, zug2);
		deck = new HBox();
		deck.setSpacing(-83);
		deck.setAlignment(Pos.CENTER);
		spielfeld.setCenter(deck);
		spielfeld.setBottom(unterMenu);
		for (Rectangle r : karten) {
			deck.getChildren().add(r);
		}
	}

	/**
	 * Erstellt die Vierecke fuer die Karten und bildet ein Deck.
	 */
	public void addCards() {
		for (int i = 0; i < 18; i++) {
			karten.add(new Rectangle(80, 120, Color.BLACK));
		}
		for (Rectangle r : karten) {
			r.setStroke(Color.WHITE);
		}
	}

	public Button getDealKartenButton() {
		return this.dealKarten;
	}

	public Button getZug1Button() {
		return this.zug;
	}

	public Button getZug2Button() {
		return this.zug2;
	}
}
