package view;

import kommunikation.PlayerCircleManager;
import staticClasses.Images;
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
		Images.YESanklageImage.setOnMouseClicked(e -> {
			gameView.getOrganizer().getChildren().remove(gameView.getButtonsBox());
			gameView.getBottomBox().getChildren().add(Images.OFFanklage);
			gameView.getOrganizer().getChildren().add(gameView.getVermuten());
			gameView.getOrganizer().getChildren().add(gameView.getBottomBox());
			gameView.getOrganizer().getChildren().add(gameView.getClose());
		});

		// Gang nehmen
		Images.YESgangImage.setOnMouseClicked(e -> {
			Sounds.gangSound();
			removeButtons();
			gameView.addInactiveButtons();
		});

		// Button Animation
		Images.NOanklageImage.setOnMouseEntered(e -> {
			removeButtons();
			gameView.getButtonsBox()
					.getChildren()
					.addAll(Images.YESanklageImage,
							Images.NOwurfelImage,
							Images.NOgangImage);
		});

		// Button Animation
		Images.YESanklageImage.setOnMouseExited(e -> {
			removeButtons();
			gameView.addInactiveButtons();
		});

		// Button Animation
		Images.NOwurfelImage.setOnMouseEntered(e -> {
			removeButtons();
			gameView.getButtonsBox()
					.getChildren()
					.addAll(Images.NOanklageImage, Images.YESwurfelImage,
							Images.NOgangImage);
		});

		// Button Animation
		Images.YESwurfelImage.setOnMouseExited(e -> {
			removeButtons();
			gameView.addInactiveButtons();
		});

		// Button Animation
		Images.NOgangImage.setOnMouseEntered(e -> {
			removeButtons();
			gameView

			.getButtonsBox()
					.getChildren()
					.addAll(Images.NOanklageImage, Images.NOwurfelImage,
							Images.YESgangImage);
		});

		// Button Animation
		Images.YESgangImage.setOnMouseExited(e -> {
			removeButtons();
			gameView.addInactiveButtons();
		});

		// Der Button fuer die aeusserung der Anklage
		Images.ONanklage.setOnMouseExited(e -> {
			gameView.getBottomBox().getChildren()
					.removeAll(Images.OFFanklage, Images.ONanklage);
			gameView.getBottomBox().getChildren().addAll(Images.OFFanklage);
		});

		// Schliesst das Zugfenster (nur fuer Developing gedacht
		// nicht fuer das eigentliche Spiel)
		gameView.getClose().setOnMouseClicked(e -> {
			gameView.getOrganizer().getChildren().remove(gameView.getVermuten());
			gameView.getBottomBox().getChildren().remove(Images.ONanklage);
			gameView.getBottomBox().getChildren().remove(Images.OFFanklage);
			gameView.getOrganizer().getChildren().remove(gameView.getBottomBox());
			gameView.getOrganizer().getChildren().remove(gameView.getClose());
			gameView.getOrganizer().getChildren().add(gameView.getButtonsBox());
		});
	}

	public void vermutungButtonManager() {
		// Der Button fuer die aeusserung der Vermutung
		Images.OFFanklage.setOnMouseEntered(e -> {
			gameView.getBottomBox().getChildren().remove(Images.OFFanklage);
			gameView.getBottomBox().getChildren().remove(Images.ONanklage);
			gameView.getBottomBox().getChildren()
					.addAll(Images.ONanklage);
		});

		// Der Button fuer die aeusserung der Anklage
		Images.ONanklage.setOnMouseExited(e -> {
			gameView.getBottomBox().getChildren().remove(Images.ONanklage);
			gameView.getBottomBox().getChildren().remove(Images.OFFanklage);
			gameView.getBottomBox().getChildren()
					.addAll(Images.OFFanklage);
		});
	}

	public void removeButtons() {
		gameView.getButtonsBox()
				.getChildren()
				.removeAll(Images.NOanklageImage, Images.YESanklageImage,
						Images.NOwurfelImage, Images.YESwurfelImage,
						Images.NOgangImage, Images.YESgangImage);
	}

	public AussergewohnlichesZugfenster getGameView() {
		return gameView;
	}

	public void setGameView(AussergewohnlichesZugfenster gameView) {
		this.gameView = gameView;
	}
}
