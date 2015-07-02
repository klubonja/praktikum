package view;

import staticClasses.Sounds;

public class AussergewohnlichesZugfensterPresenter {

	private GameFrameView gameView;

	public AussergewohnlichesZugfensterPresenter(GameFrameView gameView) {
		this.gameView = gameView;

		zugFensterButtonManager();
		vermutungButtonManager();
	}

	public void zugFensterButtonManager() {

		// Vermuten
		gameView.getKomplettesFeld().getZug().YESvermutenImage
				.setOnMouseClicked(e -> {
					gameView.getKomplettesFeld().getZug().removeButtons();
					gameView.getKomplettesFeld().getZug().killEmAll();
					// gameView.getKomplettesFeld()
					// .getZug()
					// .getButtonsBox()
					// .getChildren()
					// .addAll(gameView.getKomplettesFeld().getZug()
					// .getPersonenListe(),
					// gameView.getKomplettesFeld().getZug()
					// .getWaffenListe(),
					// gameView.getKomplettesFeld().getZug()
					// .getZimmerListe());
					gameView.getKomplettesFeld()
							.getZug()
							.getBottomBox()
							.getChildren()
							.removeAll(
									gameView.getKomplettesFeld().getZug().OFFanklage,
									gameView.getKomplettesFeld().getZug().ONanklage);
					gameView.getKomplettesFeld()
							.getZug()
							.getBottomBox()
							.getChildren()
							.add(gameView.getKomplettesFeld().getZug().OFFanklage);
				});

		// Gang nehmen
		gameView.getKomplettesFeld().getZug().YESgangImage
				.setOnMouseClicked(e -> {
					Sounds.gangSound();
					gameView.getKomplettesFeld().getZug().removeButtons();
					gameView.getKomplettesFeld().getZug().addInactiveButtons();
				});

		// Button Animation
		gameView.getKomplettesFeld().getZug().NOvermutenImage
				.setOnMouseEntered(e -> {
					gameView.getKomplettesFeld().getZug().removeButtons();
					gameView.getKomplettesFeld()
							.getZug()
							.getButtonsBox()
							.getChildren()
							.addAll(gameView.getKomplettesFeld().getZug().YESvermutenImage,
									gameView.getKomplettesFeld().getZug().NOwurfelImage,
									gameView.getKomplettesFeld().getZug().NOgangImage);
				});

		// Button Animation
		gameView.getKomplettesFeld().getZug().YESvermutenImage
				.setOnMouseExited(e -> {
					gameView.getKomplettesFeld().getZug().removeButtons();
					gameView.getKomplettesFeld().getZug().addInactiveButtons();
				});

		// Button Animation
		gameView.getKomplettesFeld().getZug().NOwurfelImage
				.setOnMouseEntered(e -> {
					gameView.getKomplettesFeld().getZug().removeButtons();
					gameView.getKomplettesFeld()
							.getZug()
							.getButtonsBox()
							.getChildren()
							.addAll(gameView.getKomplettesFeld().getZug().NOvermutenImage,
									gameView.getKomplettesFeld().getZug().YESwurfelImage,
									gameView.getKomplettesFeld().getZug().NOgangImage);
				});

		// Button Animation
		gameView.getKomplettesFeld().getZug().YESwurfelImage
				.setOnMouseExited(e -> {
					gameView.getKomplettesFeld().getZug().removeButtons();
					gameView.getKomplettesFeld().getZug().addInactiveButtons();
				});

		// Button Animation
		gameView.getKomplettesFeld().getZug().NOgangImage
				.setOnMouseEntered(e -> {
					gameView.getKomplettesFeld().getZug().removeButtons();
					gameView.getKomplettesFeld()
							.getZug()
							.getButtonsBox()
							.getChildren()
							.addAll(gameView.getKomplettesFeld().getZug().NOvermutenImage,
									gameView.getKomplettesFeld().getZug().NOwurfelImage,
									gameView.getKomplettesFeld().getZug().YESgangImage);
				});

		// Button Animation
		gameView.getKomplettesFeld().getZug().YESgangImage
				.setOnMouseExited(e -> {
					gameView.getKomplettesFeld().getZug().removeButtons();
					gameView.getKomplettesFeld().getZug().addInactiveButtons();
				});

		// Der Button fuer die aeusserung der Vermutung
		gameView.getKomplettesFeld().getZug().ONvermuten
				.setOnMouseClicked(e -> {
					// ////////////////////////////////
					// /Implementierung von Vermutung//
					// ////////////////////////////////
					gameView.getKomplettesFeld().getZug().removeButtons();
				});

		// Der Button fuer die aeusserung der Vermutung
		gameView.getKomplettesFeld().getZug().OFFvermuten
				.setOnMouseEntered(e -> {
					gameView.getKomplettesFeld().getZug().removeButtons();
					gameView.getKomplettesFeld()
							.getZug()
							.getBottomBox()
							.getChildren()
							.addAll(gameView.getKomplettesFeld().getZug().ONvermuten);
				});

		// Der Button fuer die aeusserung der Anklage
		gameView.getKomplettesFeld().getZug().ONanklage.setOnMouseExited(e -> {
			gameView.getKomplettesFeld()
					.getZug()
					.getBottomBox()
					.getChildren()
					.removeAll(
							gameView.getKomplettesFeld().getZug().OFFanklage,
							gameView.getKomplettesFeld().getZug().ONanklage);
			gameView.getKomplettesFeld().getZug().getBottomBox().getChildren()
					.addAll(gameView.getKomplettesFeld().getZug().OFFanklage);
		});

		// Schliesst das Zugfenster (nur fuer Developing gedacht
		// nicht fuer das eigentliche Spiel)
		gameView.getKomplettesFeld()
				.getZug()
				.getClose()
				.setOnMouseClicked(
						e -> {
							gameView.getKomplettesFeld().getZug().killEmAll();
							gameView.getKomplettesFeld()
									.getChildren()
									.remove(gameView.getKomplettesFeld()
											.getZug());
						});
	}

	public void vermutungButtonManager() {
		// Der Button fuer die aeusserung der Vermutung
		gameView.getKomplettesFeld().getZug().OFFanklage
				.setOnMouseEntered(e -> {
					gameView.getKomplettesFeld()
							.getZug()
							.getBottomBox()
							.getChildren()
							.remove(gameView.getKomplettesFeld().getZug().OFFanklage);
					gameView.getKomplettesFeld()
							.getZug()
							.getBottomBox()
							.getChildren()
							.remove(gameView.getKomplettesFeld().getZug().ONanklage);
					gameView.getKomplettesFeld()
							.getZug()
							.getBottomBox()
							.getChildren()
							.addAll(gameView.getKomplettesFeld().getZug().ONanklage);
				});

		// Der Button fuer die aeusserung der Anklage
		gameView.getKomplettesFeld().getZug().ONanklage
				.setOnMouseExited(e -> {
					gameView.getKomplettesFeld()
							.getZug()
							.getBottomBox()
							.getChildren()
							.remove(gameView.getKomplettesFeld().getZug().ONanklage);
					gameView.getKomplettesFeld()
							.getZug()
							.getBottomBox()
							.getChildren()
							.remove(gameView.getKomplettesFeld().getZug().OFFanklage);
					gameView.getKomplettesFeld()
							.getZug()
							.getBottomBox()
							.getChildren()
							.add(gameView.getKomplettesFeld().getZug().OFFanklage);
				});
	}
}
