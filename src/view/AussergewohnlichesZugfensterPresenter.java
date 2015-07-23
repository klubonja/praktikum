package view;

import java.util.Iterator;

import kommunikation.PlayerCircleManager;
import cluedoNetworkLayer.CluedoPlayer;
import staticClasses.Sounds;

public class AussergewohnlichesZugfensterPresenter {

	private AussergewohnlichesZugfenster gameView;
	private PlayerCircleManager pcManager;

	public AussergewohnlichesZugfensterPresenter(
			AussergewohnlichesZugfenster gameView, PlayerCircleManager pcManager) {
		this.gameView = gameView;
		this.pcManager = pcManager;

		zugFensterButtonManager();
		vermutungButtonManager();
	}

	public void zugFensterButtonManager() {

		// Vermuten
		gameView.YESvermutenImage.setOnMouseClicked(e -> {
			gameView.getOrganizer().getChildren()
					.remove(gameView.getButtonsBox());
			gameView.getOrganizer().getChildren()
					.remove(gameView.getBottomBox());
			gameView.getOrganizer().getChildren().add(gameView.getVermuten());
			gameView.getOrganizer().getChildren().add(gameView.getBottomBox());
			gameView.getBottomBox().getChildren().remove(gameView.getClose());
			gameView.getBottomBox().getChildren().add(gameView.OFFanklage);
			gameView.getBottomBox().getChildren().add(gameView.getClose());
		});

		// Gang nehmen
		gameView.YESgangImage.setOnMouseClicked(e -> {
			Sounds.gangSound();
			removeButtons();
			gameView.addInactiveButtons();
		});

		// Button Animation
		gameView.NOvermutenImage.setOnMouseEntered(e -> {
			removeButtons();
			gameView.getButtonsBox()
					.getChildren()
					.addAll(gameView.YESvermutenImage, gameView.NOwurfelImage,
							gameView.NOgangImage);
		});

		// Button Animation
		gameView.YESvermutenImage.setOnMouseExited(e -> {
			removeButtons();
			gameView.addInactiveButtons();
		});

		// Button Animation
		gameView.NOwurfelImage.setOnMouseEntered(e -> {
			removeButtons();
			gameView.getButtonsBox()
					.getChildren()
					.addAll(gameView.NOvermutenImage, gameView.YESwurfelImage,
							gameView.NOgangImage);
		});

		// Button Animation
		gameView.YESwurfelImage.setOnMouseExited(e -> {
			removeButtons();
			gameView.addInactiveButtons();
		});

		// Button Animation
		gameView.NOgangImage.setOnMouseEntered(e -> {
			removeButtons();
			gameView

			.getButtonsBox()
					.getChildren()
					.addAll(gameView.NOvermutenImage, gameView.NOwurfelImage,
							gameView.YESgangImage);
		});

		// Button Animation
		gameView.YESgangImage.setOnMouseExited(e -> {
			removeButtons();
			gameView.addInactiveButtons();
		});

		// Der Button fuer die aeusserung der Vermutung
		gameView.ONvermuten.setOnMouseClicked(e -> {
			removeButtons();
		});

		// Der Button fuer die aeusserung der Vermutung
		gameView.OFFvermuten.setOnMouseEntered(e -> {
			removeButtons();
			gameView.getBottomBox().getChildren().addAll(gameView.ONvermuten);
		});

		// Der Button fuer die aeusserung der Anklage
		gameView.ONanklage.setOnMouseExited(e -> {
			gameView.getBottomBox().getChildren()
					.removeAll(gameView.OFFanklage, gameView.ONanklage);
			gameView.getBottomBox().getChildren().addAll(gameView.OFFanklage);
		});

		// Schliesst das Zugfenster (nur fuer Developing gedacht
		// nicht fuer das eigentliche Spiel)
		gameView.getClose().setOnMouseClicked(e -> {
			removeButtons();
			gameView.killEmAll();
			gameView.getChildren().remove(gameView);
			gameView.visibleProperty().set(false);
		});
	}

	public void vermutungButtonManager() {
//		gameView.ONanklage
//				.setOnMouseClicked(e -> {
//					
//					if (!gameView.getPersonenListe().getValue()
//							.equals("Taeter")
//							&& !gameView.getWaffenListe().getValue()
//									.equals("Waffe")) {
//						vermutung(gameView.getPersonenListe().getValue(),
//								gameView.getWaffenListe().getValue(), "room");
//					}
//					
//					Iterator<CluedoPlayer> iter = pcManager.playerManager
//							.iterator();
//					CluedoPlayer buffer = (CluedoPlayer) pcManager
//							.getCurrentPlayer();
//					CluedoPlayer current = iter.next();
//
//					System.out.println(buffer.getCluedoPerson() + " vermutet");
//					System.out.println("TÄTER "
//							+ gameView.getPersonenListe().getValue());
//					System.out.println("WAFFE "
//							+ gameView.getWaffenListe().getValue());
//					System.out.println("RAUM ");
//
//					if (iter.hasNext() && (!iter.next().getNick().equals(""))) {
//						for (String card : current.getCards()) {
//							// SPIELER HAT MOMENTAN KEINE KARTEN IN DER HAND??
//							// SEARCH ME
//				if (card.equals(gameView.getPersonenListe().getValue())
//						|| card.equals(gameView.getWaffenListe().getValue())) {
//					System.out.println(current.getCluedoPerson() + " Hat was!");
//				} else {
//					System.out.println(current.getCluedoPerson() + " Hat nix!");
//				}
//			}
//			current = iter.next();
//		} else {
//			current = pcManager.playerManager.get(0);
//			for (String card : current.getCards()) {
//				if (card.equals(gameView.getPersonenListe().getValue())
//						|| card.equals(gameView.getWaffenListe().getValue())) {
//				}
//			}
//			current = iter.next();
//			if (current == buffer) {
//				System.out.println("Keiner hat die Karten.");
//			}
//		}
//	})	;

		// Der Button fuer die aeusserung der Vermutung
		gameView.OFFanklage.setOnMouseEntered(e -> {
			gameView.getBottomBox().getChildren().remove(gameView.OFFanklage);
			gameView.getBottomBox().getChildren().remove(gameView.ONanklage);
			gameView.getBottomBox().getChildren().remove(gameView.getClose());
			gameView.getBottomBox().getChildren()
					.addAll(gameView.ONanklage, gameView.getClose());
		});

		// Der Button fuer die aeusserung der Anklage
		gameView.ONanklage.setOnMouseExited(e -> {
			gameView.getBottomBox().getChildren().remove(gameView.ONanklage);
			gameView.getBottomBox().getChildren().remove(gameView.OFFanklage);
			gameView.getBottomBox().getChildren().remove(gameView.getClose());
			gameView.getBottomBox().getChildren()
					.addAll(gameView.OFFanklage, gameView.getClose());
		});
	}

	public void removeButtons() {
		gameView.getButtonsBox()
				.getChildren()
				.removeAll(gameView.NOvermutenImage, gameView.YESvermutenImage,
						gameView.NOwurfelImage, gameView.YESwurfelImage,
						gameView.NOgangImage, gameView.YESgangImage);
	}

	public String[] vermutung(String person, String weapon, String room) {
		String[] vermutung = new String[3];
		vermutung[0] = person;
		vermutung[1] = weapon;
		vermutung[2] = room;
		return vermutung;
	}

	public AussergewohnlichesZugfenster getGameView() {
		return gameView;
	}

	public void setGameView(AussergewohnlichesZugfenster gameView) {
		this.gameView = gameView;
	}
}
