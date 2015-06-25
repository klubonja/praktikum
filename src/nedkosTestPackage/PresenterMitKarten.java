package nedkosTestPackage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
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
	private int kartenZaehler = 17; // verwendet bei dem Verteilen von Karten
	private Zug_V1 zug1 = new Zug_V1();
	private Zug_V1 zugAnklage = new Zug_V1();

	public void start(Stage stage) throws Exception {
		initSpieler();
		deck.getAntwort();
		buttonManager();
		buttonManagerAnklage();
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

	public int checkAnzahlSpieler() {
		if (view.p1.isSelected() == true) {
			return 1;
		}
		if (view.p2.isSelected() == true) {
			return 2;
		}
		if (view.p3.isSelected() == true) {
			return 3;
		}
		if (view.p4.isSelected() == true) {
			return 4;
		}
		if (view.p5.isSelected() == true) {
			return 5;
		}
		if (view.p6.isSelected() == true) {
			return 6;
		} else
			return -1;
	}

	/**
	 * (Animation) Verteilt die Vierecke der Karten.
	 */
	public void verteile() {
		if (kartenZaehler >= 0) {
			RotateTransition rt = new RotateTransition(Duration.millis(250),
					view.rects[kartenZaehler]);
			rt.setByAngle(180);
			rt.setCycleCount(1);
			rt.play();
			Path path = new Path();
			path.getElements().add(new MoveTo(40, 60));
			PathTransition pathTransition = new PathTransition();
			switch (checkAnzahlSpieler()) {
			case 1:
				if (kartenZaehler >= 0) {
					path.getElements().add(new LineTo(50, 450));
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
				break;
			case 2:
				if (kartenZaehler >= 9) {
					path.getElements().add(new LineTo(50, 450));
					pathTransition.stop();
				}
				if (kartenZaehler >= 0 && kartenZaehler < 9) {
					path.getElements().add(new LineTo(-350, 180));
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
				break;
			case 3:
				if (kartenZaehler >= 13) {
					path.getElements().add(new LineTo(50, 450));
					pathTransition.stop();
				}
				if (kartenZaehler >= 8 && kartenZaehler < 13) {
					path.getElements().add(new LineTo(-350, 180));
					pathTransition.stop();
				}
				if (kartenZaehler >= 3 && kartenZaehler < 8) {
					path.getElements().add(new LineTo(-350, -140));
					pathTransition.stop();
				}
				if (kartenZaehler >= 0 && kartenZaehler < 3) {
					path.getElements().add(new LineTo(200, 200));
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
				break;
			case 4:
				if (kartenZaehler >= 14) {
					path.getElements().add(new LineTo(50, 450));
					pathTransition.stop();
				}
				if (kartenZaehler >= 10 && kartenZaehler < 14) {
					path.getElements().add(new LineTo(-350, 180));
					pathTransition.stop();
				}
				if (kartenZaehler >= 6 && kartenZaehler < 10) {
					path.getElements().add(new LineTo(-350, -140));
					pathTransition.stop();
				}
				if (kartenZaehler >= 2 && kartenZaehler < 6) {
					path.getElements().add(new LineTo(-20, -320));
					pathTransition.stop();
				}
				if (kartenZaehler >= 0 && kartenZaehler < 2) {
					path.getElements().add(new LineTo(200, 200));
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
				break;
			case 5:
				if (kartenZaehler >= 15) {
					path.getElements().add(new LineTo(50, 450));
					pathTransition.stop();
				}
				if (kartenZaehler >= 12 && kartenZaehler < 15) {
					path.getElements().add(new LineTo(-350, 180));
					pathTransition.stop();
				}
				if (kartenZaehler >= 9 && kartenZaehler < 12) {
					path.getElements().add(new LineTo(-350, -140));
					pathTransition.stop();
				}
				if (kartenZaehler >= 6 && kartenZaehler < 9) {
					path.getElements().add(new LineTo(-20, -320));
					pathTransition.stop();
				}
				if (kartenZaehler >= 3 && kartenZaehler < 6) {
					path.getElements().add(new LineTo(400, -120));
					pathTransition.stop();
				}
				if (kartenZaehler >= 0 && kartenZaehler < 3) {
					path.getElements().add(new LineTo(200, 200));
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
				break;
			case 6:
				if (kartenZaehler >= 14) {
					path.getElements().add(new LineTo(50, 450));
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
				break;
			default:
				System.out.println("Keine Spieleranzahl gewaehlt.");
				break;
			}
		}
	}

	/**
	 * Verteilt die Karten unter 6 Spielern.
	 * 
	 */
	@SuppressWarnings("unused")
	public void deal() {
		List<String> hand = new ArrayList<String>();
		int counter = 1;
		int player = 0;

		switch (checkAnzahlSpieler()) {
		case 1:
			for (String str : deck.getDeck()) {
				hand.add(str);
				if (counter == 18) {
					spieler.get(player).giveCards(hand);
					System.out.println(spieler.get(player).getName() + " hat: "
							+ spieler.get(player).showCards());
					hand.removeAll(hand);
					counter = 0;
					player++;
				}
				if (str.equals(deck.getDeck().get(17))) {
					if (hand == null) {
						System.out.println("Keine Karten im Schwimmbad.");
					} else {
						System.out.println("Karten im Schwimmbad: " + hand);
					}
				}
				counter++;
			}
			break;
		case 2:
			for (String str : deck.getDeck()) {
				hand.add(str);
				if (counter == 9) {
					spieler.get(player).giveCards(hand);
					System.out.println(spieler.get(player).getName() + " hat: "
							+ spieler.get(player).showCards());
					hand.removeAll(hand);
					counter = 0;
					player++;
				}
				if (str.equals(deck.getDeck().get(17))) {
					if (hand == null) {
						System.out.println("Keine Karten im Schwimmbad.");
					} else {
						System.out.println("Karten im Schwimmbad: " + hand);
					}
				}
				counter++;
			}
			break;
		case 3:
			for (String str : deck.getDeck()) {
				hand.add(str);
				if (counter == 6) {
					spieler.get(player).giveCards(hand);
					System.out.println(spieler.get(player).getName() + " hat: "
							+ spieler.get(player).showCards());
					hand.removeAll(hand);
					counter = 0;
					player++;
				}
				if (str.equals(deck.getDeck().get(17))) {
					if (hand == null) {
						System.out.println("Keine Karten im Schwimmbad.");
					} else {
						System.out.println("Karten im Schwimmbad: " + hand);
					}
				}
				counter++;
			}
			break;
		case 4:
			for (String str : deck.getDeck()) {
				hand.add(str);
				if (counter == 4) {
					spieler.get(player).giveCards(hand);
					System.out.println(spieler.get(player).getName() + " hat: "
							+ spieler.get(player).showCards());
					hand.removeAll(hand);
					counter = 0;
					player++;
				}
				if (str.equals(deck.getDeck().get(17))) {
					if (hand == null) {
						System.out.println("Keine Karten im Schwimmbad.");
					} else {
						System.out.println("Karten im Schwimmbad: " + hand);
					}
				}
				counter++;
			}
			break;
		case 5:
			for (String str : deck.getDeck()) {
				hand.add(str);
				if (counter == 3 && player < 5) {
					spieler.get(player).giveCards(hand);
					System.out.println(spieler.get(player).getName() + " hat: "
							+ spieler.get(player).showCards());
					hand.removeAll(hand);
					counter = 0;
					player++;
				}
				if (str.equals(deck.getDeck().get(17))) {
					if (hand == null) {
						System.out.println("Keine Karten im Schwimmbad.");
					} else {
						System.out.println("Karten im Schwimmbad: " + hand);
					}
				}
				counter++;
			}
			break;
		case 6:
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
				if (str.equals(deck.getDeck().get(17))) {
					if (hand == null) {
						System.out.println("Keine Karten im Schwimmbad.");
					} else {
						System.out.println("Karten im Schwimmbad: " + hand);
					}
				}
				counter++;
			}
			break;
		default:
			System.out.println("Keine Spieleranzahl gewaehlt.");
			break;
		}

	}

	/**
	 * Spielt ein Geraeusch, waehrend die Karten verteilt werden.
	 */
	public void cardSound() {
		String sound = "media/card.wav";
		Media mediaFile = new Media(new File(sound).toURI().toString());
		MediaPlayer mediaplayer = new MediaPlayer(mediaFile);
		mediaplayer.setAutoPlay(true);
		mediaplayer.setVolume(1);
	}

	/**
	 * Sound fur den Gang nehmen Button
	 */
	public void gangSound() {
		String sound = "media/gang.mp3";
		Media mediaFile = new Media(new File(sound).toURI().toString());
		MediaPlayer mediaplayer = new MediaPlayer(mediaFile);
		mediaplayer.setAutoPlay(true);
		mediaplayer.setVolume(1);
	}

	/**
	 * Sound fur die Turkacheln
	 */
	public void doorSound() {
		String sound = "media/door.mp3";
		Media mediaFile = new Media(new File(sound).toURI().toString());
		MediaPlayer mediaplayer = new MediaPlayer(mediaFile);
		mediaplayer.setAutoPlay(true);
		mediaplayer.setVolume(1);
	}

	/**
	 * Sound fur das Wurfeln
	 */
	public void diceSound() {
		String sound = "media/dice.wav";
		Media mediaFile = new Media(new File(sound).toURI().toString());
		MediaPlayer mediaplayer = new MediaPlayer(mediaFile);
		mediaplayer.setAutoPlay(true);
		mediaplayer.setVolume(1);
	}

	/**
	 * Arrangiert was jeder Button in dem Spiel machen soll.
	 */
	public void buttonManager() {
		view.getDealKartenButton().setOnMouseClicked(
				new EventHandler<MouseEvent>() {
					public void handle(MouseEvent e) {
						verteile();
						deal();
					}
				});

		view.getZug1Button().setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				zug1.getButtonsBox()
						.getChildren()
						.addAll(zug1.NOanklageImage, zug1.NOwurfelImage,
								zug1.NOgangImage);
				view.root.getChildren().add(zug1);
				zug1.gangControl = true;
			}
		});

		view.onRoomWithPassage
				.setOnMouseClicked(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent e) {
						zug1.getButtonsBox()
								.getChildren()
								.addAll(zug1.NOanklageImage,
										zug1.NOwurfelImage, zug1.NOgangImage);
						view.root.getChildren().add(zug1);
						zug1.gangControl = false;
					}
				});

		// Wurfeln
		zug1.YESwurfelImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				zug1.wurfelControl = true;
				diceSound();
				System.out.println("Der Spieler hat "
						+ (int) ((Math.random() * 6) + 1) + " und "
						+ (int) ((Math.random() * 6) + 1) + " gewuerfelt.");
				zug1.getButtonsBox().getChildren().remove(zug1.NOanklageImage);
				zug1.getButtonsBox().getChildren().remove(zug1.YESanklageImage);
				zug1.getButtonsBox().getChildren().remove(zug1.NOwurfelImage);
				zug1.getButtonsBox().getChildren().remove(zug1.YESwurfelImage);
				zug1.getButtonsBox().getChildren().remove(zug1.NOgangImage);
				zug1.getButtonsBox().getChildren().remove(zug1.YESgangImage);
				zug1.getButtonsBox()
						.getChildren()
						.addAll(zug1.NOanklageImage, zug1.NOwurfelImage,
								zug1.NOgangImage);
				zug1.wurfelControl = false;
			}
		});

		// Anklagen
		zug1.YESanklageImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				zug1.anklageControl = true;
				zug1.getButtonsBox().getChildren().remove(zug1.NOanklageImage);
				zug1.getButtonsBox().getChildren().remove(zug1.YESanklageImage);
				zug1.getButtonsBox().getChildren().remove(zug1.NOwurfelImage);
				zug1.getButtonsBox().getChildren().remove(zug1.YESwurfelImage);
				zug1.getButtonsBox().getChildren().remove(zug1.NOgangImage);
				zug1.getButtonsBox().getChildren().remove(zug1.YESgangImage);
				zug1.getButtonsBox()
						.getChildren()
						.addAll(zug1.getPersonenListe(), zug1.getWaffenListe(),
								zug1.getZimmerListe());
				zug1.getBottomBox().getChildren().remove(zug1.OFFanklage);
				zug1.getBottomBox().getChildren().addAll(zug1.OFFanklage);
			}
		});

		// Gang nehmen
		zug1.YESgangImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				zug1.gangControl = true;
				gangSound();
				zug1.getButtonsBox().getChildren().remove(zug1.NOanklageImage);
				zug1.getButtonsBox().getChildren().remove(zug1.YESanklageImage);
				zug1.getButtonsBox().getChildren().remove(zug1.NOwurfelImage);
				zug1.getButtonsBox().getChildren().remove(zug1.YESwurfelImage);
				zug1.getButtonsBox().getChildren().remove(zug1.NOgangImage);
				zug1.getButtonsBox().getChildren().remove(zug1.YESgangImage);
				zug1.getButtonsBox()
						.getChildren()
						.addAll(zug1.NOanklageImage, zug1.NOwurfelImage,
								zug1.NOgangImage);
				zug1.gangControl = false;
			}
		});

		// Button Animation
		zug1.NOanklageImage.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if (!zug1.anklageControl) {
					zug1.getButtonsBox().getChildren()
							.remove(zug1.NOanklageImage);
					zug1.getButtonsBox().getChildren()
							.remove(zug1.NOwurfelImage);
					zug1.getButtonsBox().getChildren().remove(zug1.NOgangImage);
					zug1.getButtonsBox()
							.getChildren()
							.addAll(zug1.YESanklageImage, zug1.NOwurfelImage,
									zug1.NOgangImage);
				}
			}
		});

		// Button Animation
		zug1.YESanklageImage.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if (!zug1.anklageControl) {
					zug1.getButtonsBox().getChildren()
							.remove(zug1.YESanklageImage);
					zug1.getButtonsBox().getChildren()
							.remove(zug1.NOwurfelImage);
					zug1.getButtonsBox().getChildren().remove(zug1.NOgangImage);
					zug1.getButtonsBox()
							.getChildren()
							.addAll(zug1.NOanklageImage, zug1.NOwurfelImage,
									zug1.NOgangImage);
				}
			}
		});

		// Button Animation
		zug1.NOwurfelImage.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if (!zug1.wurfelControl) {
					zug1.getButtonsBox().getChildren()
							.remove(zug1.NOanklageImage);
					zug1.getButtonsBox().getChildren()
							.remove(zug1.NOwurfelImage);
					zug1.getButtonsBox().getChildren().remove(zug1.NOgangImage);
					zug1.getButtonsBox()
							.getChildren()
							.addAll(zug1.NOanklageImage, zug1.YESwurfelImage,
									zug1.NOgangImage);
				}
			}
		});

		// Button Animation
		zug1.YESwurfelImage.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if (!zug1.wurfelControl) {
					zug1.getButtonsBox().getChildren()
							.remove(zug1.NOanklageImage);
					zug1.getButtonsBox().getChildren()
							.remove(zug1.YESwurfelImage);
					zug1.getButtonsBox().getChildren().remove(zug1.NOgangImage);
					zug1.getButtonsBox()
							.getChildren()
							.addAll(zug1.NOanklageImage, zug1.NOwurfelImage,
									zug1.NOgangImage);
				}
			}
		});

		// Button Animation
		zug1.NOgangImage.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if (!zug1.gangControl) {
					zug1.getButtonsBox().getChildren()
							.remove(zug1.NOanklageImage);
					zug1.getButtonsBox().getChildren()
							.remove(zug1.NOwurfelImage);
					zug1.getButtonsBox().getChildren().remove(zug1.NOgangImage);
					zug1.getButtonsBox()
							.getChildren()
							.addAll(zug1.NOanklageImage, zug1.NOwurfelImage,
									zug1.YESgangImage);
				}
			}
		});

		// Button Animation
		zug1.YESgangImage.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if (!zug1.gangControl) {
					zug1.getButtonsBox().getChildren()
							.remove(zug1.NOanklageImage);
					zug1.getButtonsBox().getChildren()
							.remove(zug1.NOwurfelImage);
					zug1.getButtonsBox().getChildren()
							.remove(zug1.YESgangImage);
					zug1.getButtonsBox()
							.getChildren()
							.addAll(zug1.NOanklageImage, zug1.NOwurfelImage,
									zug1.NOgangImage);
				}
			}
		});

		// Der Button fuer die aeusserung der Anklage
		zug1.ONanklage.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				zug1.klagenControl = true;
				System.out.println("Der Spieler entschied sich fuer:");
				System.out.println(zug1.getPersonenListe().getValue());
				System.out.println(zug1.getWaffenListe().getValue());
				System.out.println(zug1.getZimmerListe().getValue());

				zug1.getButtonsBox().getChildren().remove(zug1.NOanklageImage);
				zug1.getButtonsBox().getChildren().remove(zug1.YESanklageImage);
				zug1.getButtonsBox().getChildren().remove(zug1.NOwurfelImage);
				zug1.getButtonsBox().getChildren().remove(zug1.YESwurfelImage);
				zug1.getButtonsBox().getChildren().remove(zug1.NOgangImage);
				zug1.getButtonsBox().getChildren().remove(zug1.YESgangImage);
				zug1.getButtonsBox().getChildren()
						.remove(zug1.getPersonenListe());
				zug1.getButtonsBox().getChildren()
						.remove(zug1.getWaffenListe());
				zug1.getButtonsBox().getChildren()
						.remove(zug1.getZimmerListe());
				zug1.getBottomBox().getChildren().remove(zug1.OFFanklage);
				zug1.getBottomBox().getChildren().remove(zug1.ONanklage);
				zug1.anklageControl = false;
				zug1.gangControl = false;
				zug1.wurfelControl = false;
				zug1.klagenControl = false;
				view.root.getChildren().remove(zug1);
			}
		});

		// Der Button fuer die aeusserung der Anklage
		zug1.OFFanklage.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if (!zug1.klagenControl) {
					zug1.getBottomBox().getChildren().remove(zug1.OFFanklage);
					zug1.getBottomBox().getChildren().remove(zug1.ONanklage);
					zug1.getBottomBox().getChildren().addAll(zug1.ONanklage);
				}
			}
		});

		// Der Button fuer die aeusserung der Anklage
		zug1.ONanklage.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if (!zug1.klagenControl) {
					zug1.getBottomBox().getChildren().remove(zug1.OFFanklage);
					zug1.getBottomBox().getChildren().remove(zug1.ONanklage);
					zug1.getBottomBox().getChildren().addAll(zug1.OFFanklage);
				}
			}
		});

		// Schliesst das Zugfenster (nur fuer Developing gedacht
		// nicht fuer das eigentliche Spiel)
		zug1.close.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				zug1.getButtonsBox().getChildren().remove(zug1.NOanklageImage);
				zug1.getButtonsBox().getChildren().remove(zug1.YESanklageImage);
				zug1.getButtonsBox().getChildren().remove(zug1.NOwurfelImage);
				zug1.getButtonsBox().getChildren().remove(zug1.YESwurfelImage);
				zug1.getButtonsBox().getChildren().remove(zug1.NOgangImage);
				zug1.getButtonsBox().getChildren().remove(zug1.YESgangImage);
				zug1.getButtonsBox().getChildren()
						.remove(zug1.getPersonenListe());
				zug1.getButtonsBox().getChildren()
						.remove(zug1.getWaffenListe());
				zug1.getButtonsBox().getChildren()
						.remove(zug1.getZimmerListe());
				zug1.getBottomBox().getChildren().remove(zug1.OFFanklage);
				zug1.getBottomBox().getChildren().remove(zug1.ONanklage);
				zug1.anklageControl = false;
				zug1.gangControl = false;
				zug1.wurfelControl = false;
				view.root.getChildren().remove(zug1);
			}
		});
	}

	/**
	 * Arrangiert was jeder Button in dem Spiel machen soll.
	 */
	public void buttonManagerAnklage() {
		view.vermuten.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@SuppressWarnings("unchecked")
			public void handle(MouseEvent e) {
				zugAnklage.getButtonsBox().getChildren()
						.add(zugAnklage.OFFvermuten);
				zugAnklage.gangControl = true;

				zugAnklage.anklageControl = true;
				zugAnklage.getButtonsBox().getChildren()
						.remove(zugAnklage.NOanklageImage);
				zugAnklage.getButtonsBox().getChildren()
						.remove(zugAnklage.YESanklageImage);
				zugAnklage.getButtonsBox().getChildren()
						.remove(zugAnklage.NOwurfelImage);
				zugAnklage.getButtonsBox().getChildren()
						.remove(zugAnklage.YESwurfelImage);
				zugAnklage.getButtonsBox().getChildren()
						.remove(zugAnklage.NOgangImage);
				zugAnklage.getButtonsBox().getChildren()
						.remove(zugAnklage.YESgangImage);
				zugAnklage.zimmer.getItems().addAll(
						zugAnklage.deck.getZimmerOrdered());
				zugAnklage
						.getButtonsBox()
						.getChildren()
						.addAll(zugAnklage.getPersonenListe(),
								zugAnklage.getWaffenListe(),
								zugAnklage.getZimmerListe());
				zugAnklage.getBottomBox().getChildren()
						.remove(zugAnklage.OFFvermuten);
				zugAnklage.getBottomBox().getChildren()
						.addAll(zugAnklage.OFFvermuten);
				view.root.getChildren().add(zugAnklage);
			}
		});

		// Der Button fuer die aeusserung der Anklage
		zugAnklage.ONvermuten.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				zugAnklage.vermutenControl = true;
				System.out.println("Der Spieler vermutet folgendes:");
				System.out.println("Es war: "
						+ zugAnklage.getPersonenListe().getValue());
				System.out.println("Mit der Waffe: "
						+ zugAnklage.getWaffenListe().getValue());
				System.out.println("In dem Zimmer: "
						+ zugAnklage.getZimmerListe().getValue());

				if (zugAnklage.getPersonenListe().getValue() == deck.antwort[0]
						&& zugAnklage.getWaffenListe().getValue() == deck.antwort[1]
						&& zugAnklage.getZimmerListe().getValue() == deck.antwort[2]) {
					System.out.println("Der Spieler hat gewonnen!");
				} else {
					System.out.println("Das war leider eine falsche Antwort!");
				}

				zugAnklage.getButtonsBox().getChildren()
						.remove(zugAnklage.getPersonenListe());
				zugAnklage.getButtonsBox().getChildren()
						.remove(zugAnklage.getWaffenListe());
				zugAnklage.getButtonsBox().getChildren()
						.remove(zugAnklage.getZimmerListe());
				zugAnklage.getBottomBox().getChildren()
						.remove(zugAnklage.OFFanklage);
				zugAnklage.getBottomBox().getChildren()
						.remove(zugAnklage.ONanklage);
				zugAnklage.vermutenControl = false;
				view.root.getChildren().remove(zugAnklage);
			}
		});

		// Der Button fuer die aeusserung der Anklage
		zugAnklage.OFFvermuten
				.setOnMouseEntered(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent e) {
						if (!zugAnklage.vermutenControl) {
							zugAnklage.getBottomBox().getChildren()
									.remove(zugAnklage.OFFvermuten);
							zugAnklage.getBottomBox().getChildren()
									.remove(zugAnklage.ONvermuten);
							zugAnklage.getBottomBox().getChildren()
									.addAll(zugAnklage.ONvermuten);
						}
					}
				});

		// Der Button fuer die aeusserung der Anklage
		zugAnklage.ONvermuten.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if (!zugAnklage.vermutenControl) {
					zugAnklage.getBottomBox().getChildren()
							.remove(zugAnklage.ONvermuten);
					zugAnklage.getBottomBox().getChildren()
							.remove(zugAnklage.OFFvermuten);
					zugAnklage.getBottomBox().getChildren()
							.add(zugAnklage.OFFvermuten);
				}
			}
		});

		// Schliesst das Zugfenster (nur fuer Developing gedacht
		// nicht fuer das eigentliche Spiel)
		zugAnklage.close.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				zugAnklage.getButtonsBox().getChildren()
						.remove(zugAnklage.getPersonenListe());
				zugAnklage.getButtonsBox().getChildren()
						.remove(zugAnklage.getWaffenListe());
				zugAnklage.getButtonsBox().getChildren()
						.remove(zugAnklage.getZimmerListe());
				zugAnklage.getBottomBox().getChildren()
						.remove(zugAnklage.OFFvermuten);
				zugAnklage.getBottomBox().getChildren()
						.remove(zugAnklage.ONvermuten);
				zugAnklage.vermutenControl = false;
				view.root.getChildren().remove(zugAnklage);
			}
		});
	}

	public static void main(String[] args) {
		launch(args);
	}
}
