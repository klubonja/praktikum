package nedkosTestPackage;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
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
	StackPane root = new StackPane();
	Group arranger = new Group();
	private BorderPane spielfeld;
	private HBox deck;
	private HBox unterMenu;
	private Button dealKarten = new Button("Deal");
	private Button zug = new Button("Zug_V1 (ohne Gang)");
	private Button zug2 = new Button("Zug_V2");
	Button onRoomWithPassage = new Button("Mit Gang");
	RadioButton p1 = new RadioButton("1 Spieler");
	RadioButton p2 = new RadioButton("2 Spieler");
	RadioButton p3 = new RadioButton("3 Spieler");
	RadioButton p4 = new RadioButton("4 Spieler");
	RadioButton p5 = new RadioButton("5 Spieler");
	RadioButton p6 = new RadioButton("6 Spieler");
	HBox top = new HBox();
	Button vermuten = new Button("Vermuten");
	
	List<Rectangle> karten = new ArrayList<Rectangle>();
	Rectangle[] rects;

	/**
	 * Constructor fuer die View.
	 */
	public ViewMitKarten() {
		addCards();
		rects = karten.toArray(new Rectangle[0]);
		setLayout();
		this.root.getChildren().add(spielfeld);
		this.scene = new Scene(root, 600, 600);
		this.setScene(scene);
	}

	/**
	 * Initialisiert den LayoutManager und fuegt die Karten ein.
	 */
	@SuppressWarnings("static-access")
	public void setLayout() {
		root.setMargin(arranger, new Insets(0, 0, 0, 0));
		
		spielfeld = new BorderPane();
		unterMenu = new HBox();
		unterMenu.getChildren().addAll(dealKarten, zug, onRoomWithPassage, vermuten);
		
		deck = new HBox();
		deck.setSpacing(-83);
		deck.setAlignment(Pos.CENTER);
		
		top.getChildren().addAll(p1, p2, p3, p4, p5, p6);
		top.setSpacing(10);
		top.setAlignment(Pos.CENTER);
		
		spielfeld.setTop(top);
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
