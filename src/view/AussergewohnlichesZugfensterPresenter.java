package view;

import java.util.Iterator;

import cluedoNetworkLayer.CluedoPlayer;
import staticClasses.Sounds;
import finderOfPaths.PlayerCircleManager;

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
		gameView

		.getClose().setOnMouseClicked(e -> {
			removeButtons();
			gameView.killEmAll();
			gameView.getChildren().remove(gameView);
		});
	}

	@SuppressWarnings("unchecked")
	public void vermutungButtonManager() {
		gameView.ONanklage
				.setOnMouseClicked(e -> {
					Iterator<CluedoPlayer> iter = pcManager.playerManager
							.iterator();
					CluedoPlayer buffer = (CluedoPlayer) pcManager.getCurrentPlayer();
					CluedoPlayer current = iter.next();
					if (iter.hasNext()) {
						for (String card : current.getCards()) {
							//SPIELER HAT MOMENTAN KEINE KARTEN IN DER HAND?? SEARCH ME
							if (card.equals(gameView.getPersonenListe()
									.getValue())
									|| card.equals(gameView.getWaffenListe()
											.getValue())) {
								System.out.println(current.getCluedoPerson()
										+ " Hat was!");
							} else {
								System.out.println(current.getCluedoPerson()
										+ " Hat nix!");
							}
						}
						current = iter.next();
					} else {
						current = pcManager.playerManager
								.get(0);
						for (String card : current.getCards()) {
							if (card.equals(gameView.getPersonenListe()
									.getValue())
									|| card.equals(gameView.getWaffenListe()
											.getValue())) {
							}
						}
						current = iter.next();
						if (current == buffer) {
							System.out.println("Keiner hat die Karten.");
						}
					}
				});

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
}
