package gui1_0;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Die Presenter Klasse, die fuer die Logik des Verteilen der Karten und des
 * Erscheinen der 2 Versionen des Spielerzueges sorgt.
 * 
 * @author NedkoChulev
 *
 */
public class PresenterMitKarten extends Application {

	private List<SpielerMitKarten> spieler = new ArrayList<SpielerMitKarten>();
	private Deck deck = new Deck();
	private ViewMitKarten view = new ViewMitKarten();
	private int kartenZaehler = 17; // wird bei dem Verteilen von Karten
	private Zug_V1 zug1 = new Zug_V1();
	private Zug_V2 zug2 = new Zug_V2();

	// vewendet

	public void start(Stage stage) throws Exception {
		initSpieler();
		deal();
		deck.getAntwort();
		buttonManager();
		stage = this.view;
		stage.show();
	}

	/**
	 * Initialisiert die Spieler.
	 * 
	 */
	public void initSpieler() {
		SpielerMitKarten gruen = new SpielerMitKarten("Gruen");
		SpielerMitKarten gatow = new SpielerMitKarten("Gatow");
		SpielerMitKarten porz = new SpielerMitKarten("Porz");
		SpielerMitKarten bloom = new SpielerMitKarten("Bloom");
		SpielerMitKarten gloria = new SpielerMitKarten("Gloria");
		SpielerMitKarten weiss = new SpielerMitKarten("Weiss");
		spieler.add(gruen);
		spieler.add(gatow);
		spieler.add(porz);
		spieler.add(bloom);
		spieler.add(gloria);
		spieler.add(weiss);

	}

	/**
	 * (Animation) Verteilt die Vierecke der Karten.
	 */
	public void verteile() {
		if (kartenZaehler >= 0) {
			RotateTransition rt = new RotateTransition(Duration.millis(500),
					view.rects[kartenZaehler]);
			rt.setByAngle(180);
			rt.setCycleCount(1);
			rt.play();
			Path path = new Path();
			path.getElements().add(new MoveTo(40, 60));
			PathTransition pathTransition = new PathTransition();
			if (kartenZaehler >= 14) {
				path.getElements().add(new LineTo(50, 350));
				pathTransition.stop();
			}
			if (kartenZaehler >= 11 && kartenZaehler < 14) {
				path.getElements().add(new LineTo(-350, 180));
				pathTransition.stop();
			}
			if (kartenZaehler >= 8 && kartenZaehler < 11) {
				path.getElements().add(new LineTo(-350, -140));
				pathTransition.stop();
			}
			if (kartenZaehler >= 5 && kartenZaehler < 8) {
				path.getElements().add(new LineTo(-20, -320));
				pathTransition.stop();
			}
			if (kartenZaehler >= 2 && kartenZaehler < 5) {
				path.getElements().add(new LineTo(400, -120));
				pathTransition.stop();
			}
			if (kartenZaehler >= 0 && kartenZaehler < 2) {
				path.getElements().add(new LineTo(400, 210));
				pathTransition.stop();
			}
			pathTransition.setDuration(Duration.millis(200));
			pathTransition.setPath(path);
			pathTransition.setNode(view.rects[kartenZaehler]);
			pathTransition.setCycleCount(1);
			pathTransition.setAutoReverse(true);
			cardSound();
			pathTransition.play();
			pathTransition.setOnFinished(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					kartenZaehler--;
					verteile();
				}
			});
		}
	}

	/**
	 * Verteilt die Karten unter 6 Spielern.
	 * 
	 */
	public void deal() {
		List<String> hand = new ArrayList<String>();
		int counter = 1;
		int player = 0;
		for (String str : deck.getDeck()) {
			hand.add(str);
			if (counter == 3 || str.equals(deck.getDeck().get(17))) {
				spieler.get(player).giveCards(hand);
				System.out.println(spieler.get(player).getName() + " hat: "
						+ spieler.get(player).showCards());
				hand.removeAll(hand);
				counter = 0;
				player++;
			}
			counter++;
		}
	}

	/**
	 * Spielt ein Geraeusch, waehrend die Karten verteilt werden.
	 */
	public void cardSound() {
		String sound3 = "media/card.wav";
		Media mediaFile3 = new Media(new File(sound3).toURI().toString());
		MediaPlayer mediaplayer3 = new MediaPlayer(mediaFile3);
		mediaplayer3.setAutoPlay(true);
		mediaplayer3.setVolume(0.2);
	}

	/**
	 * Arrangiert was jeder Button in dem Spiel machen soll.
	 */
	public void buttonManager() {
		view.getDealKartenButton().setOnMouseClicked(
				new EventHandler<MouseEvent>() {
					public void handle(MouseEvent e) {
						verteile();
					}
				});

		view.getZug1Button().setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				zug1.show();
			}
		});

		view.getZug2Button().setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				zug2.show();
			}
		});

		zug1.getAnklage().setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				zug1.getButtonsBox().getChildren().remove(zug1.getAnklage());
				zug1.getButtonsBox().getChildren().remove(zug1.getWurfel());
				zug1.getButtonsBox().getChildren().remove(zug1.getGang());
				zug1.getButtonsBox()
						.getChildren()
						.addAll(zug1.getPersonenListe(), zug1.getWaffenListe(),
								zug1.getZimmerListe());
				zug1.getBottomBox().getChildren().remove(zug1.close);
				zug1.getBottomBox().getChildren()
						.addAll(zug1.getAnklageButton(), zug1.close);
			}
		});

		zug1.close.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				zug1.close();
			}
		});

		zug2.getAnklage().setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				zug2.getButtonsBox().getChildren().remove(zug2.getAnklage());
				zug2.getButtonsBox().getChildren().remove(zug2.getWurfel());
				zug2.getButtonsBox().getChildren().remove(zug2.getGang());
				zug2.getButtonsBox().setSpacing(1);
				addImages(zug2.getTopBox(), zug2.getPersonImage(), 6);
				addImages(zug2.getButtonsBox(), zug2.getWaffeImage(), 6);
				zug2.getBottomBox().getChildren().remove(zug2.close);
				zug2.getBottomBox()
						.getChildren()
						.addAll(zug2.getAnklageButton(),
								new ImageView(zug2.getZimmerImage()),
								zug2.close);
				zug2.getBottomBox().setSpacing(50);
			}
		});

		zug2.close.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				zug2.close();
			}
		});
	}

	/**
	 * Eine Hilfsmethode, die fur die zweite Version des Zuges die Buttons mit
	 * den Images erzeugt.
	 * 
	 * @param box
	 *            die HBox, in der sich die Buttons befinden
	 * @param img
	 *            die Bilder fuer die Buttons
	 * @param amount
	 *            Anzahl der Buttons
	 */
	public void addImages(HBox box, Image img, int amount) {
		for (int i = 0; i < amount; i++) {
			box.getChildren().add(new Button("", new ImageView(img)));
		}
	}

	/**
	 * Spielstart.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
