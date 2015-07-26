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
		Images.suspect.setOnMouseClicked(e -> {
			gameView.getOrganizer().getChildren().remove(gameView.getButtonsBox());
			gameView.getBottomBox().getChildren().add(Images.suspectLATER);
			gameView.getOrganizer().getChildren().add(gameView.getVermuten());
			gameView.getOrganizer().getChildren().add(gameView.getBottomBox());
			gameView.getOrganizer().getChildren().add(gameView.getBackButton());
		});

		// Gang nehmen
		Images.passage.setOnMouseClicked(e -> {
			Sounds.gangSound();
			removeButtons();
			gameView.addInactiveButtons();
		});

		// Button Animation
		Images.enabledSuspect.setOnMouseEntered(e -> {
			removeButtons();
			gameView.getButtonsBox()
					.getChildren()
					.addAll(Images.suspect,
							Images.enabledRoll,
							Images.enabledPassage);
		});

		// Button Animation
		Images.suspect.setOnMouseExited(e -> {
			removeButtons();
			gameView.addInactiveButtons();
		});

		// Button Animation
		Images.enabledRoll.setOnMouseEntered(e -> {
			removeButtons();
			gameView.getButtonsBox().getChildren().addAll(
					Images.enabledSuspect,
					Images.roll,
					Images.enabledPassage);
		});

		// Button Animation
		Images.roll.setOnMouseExited(e -> {
			removeButtons();
			gameView.addInactiveButtons();
		});

		// Button Animation
		Images.enabledPassage.setOnMouseEntered(e -> {
			removeButtons();
			gameView.getButtonsBox().getChildren().addAll(
					Images.enabledSuspect,
					Images.enabledRoll,
					Images.passage);
		});

		// Button Animation
		Images.passage.setOnMouseExited(e -> {
			removeButtons();
			gameView.addInactiveButtons();
		});

		// Der Button fuer die aeusserung der Anklage
		Images.suspectNOW.setOnMouseExited(e -> {
			gameView.getBottomBox().getChildren().removeAll(
					Images.suspectLATER,
					Images.suspectNOW);
			gameView.getBottomBox().getChildren().addAll(
					Images.suspectLATER);
		});

		//WE HAVE TO GO BACK!
		gameView.getBackButton().setOnMouseClicked(e -> {
			gameView.getOrganizer().getChildren().remove(gameView.getVermuten());
			gameView.getBottomBox().getChildren().remove(Images.suspectNOW);
			gameView.getBottomBox().getChildren().remove(Images.suspectLATER);
			gameView.getOrganizer().getChildren().remove(gameView.getBottomBox());
			gameView.getOrganizer().getChildren().remove(gameView.getBackButton());
			gameView.getOrganizer().getChildren().add(gameView.getButtonsBox());
		});
	}

	public void vermutungButtonManager() {
		// Der Button fuer die aeusserung der Vermutung
		Images.suspectLATER.setOnMouseEntered(e -> {
			gameView.getBottomBox().getChildren().remove(Images.suspectLATER);
			gameView.getBottomBox().getChildren().remove(Images.suspectNOW);
			gameView.getBottomBox().getChildren()
					.addAll(Images.suspectNOW);
		});

		// Der Button fuer die aeusserung der Anklage
		Images.suspectNOW.setOnMouseExited(e -> {
			gameView.getBottomBox().getChildren().remove(Images.suspectNOW);
			gameView.getBottomBox().getChildren().remove(Images.suspectLATER);
			gameView.getBottomBox().getChildren()
					.addAll(Images.suspectLATER);
		});
	}

	public void removeButtons() {
		gameView.getButtonsBox()
				.getChildren()
				.removeAll(Images.enabledSuspect, Images.suspect,
						Images.enabledRoll, Images.roll,
						Images.enabledPassage, Images.passage);
	}

	public AussergewohnlichesZugfenster getGameView() {
		return gameView;
	}

	public void setGameView(AussergewohnlichesZugfenster gameView) {
		this.gameView = gameView;
	}
}
