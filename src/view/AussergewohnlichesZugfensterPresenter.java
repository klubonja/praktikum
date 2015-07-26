package view;

import javafx.scene.image.Image;
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

	public void disableSuspect(){
		Images.suspect.setImage(Images.disabled);
		Images.suspect.setOnMouseClicked(e -> {});
		Images.suspect.setOnMouseEntered(e -> {});
		Images.suspect.setOnMouseExited(e -> {});
	}
	
	public void disableRoll(){
		Images.roll.setImage(Images.disabled);
		Images.roll.setOnMouseClicked(e -> {});
		Images.roll.setOnMouseEntered(e -> {});
		Images.roll.setOnMouseExited(e -> {});
	}
	
	public void disablePassage(){
		Images.passage.setImage(Images.disabled);
		Images.passage.setOnMouseClicked(e -> {});
		Images.passage.setOnMouseEntered(e -> {});
		Images.passage.setOnMouseExited(e -> {});
	}
	
	public void enableSuspect(){
		Images.suspect.setImage(Images.enabled);
		
		// Vermuten
				Images.suspect.setOnMouseClicked(e -> {
					gameView.getOrganizer().getChildren().remove(gameView.getButtonsBox());
					gameView.getBottomBox().getChildren().add(Images.suspectLATER);
					gameView.getOrganizer().getChildren().add(gameView.getVermuten());
					gameView.getOrganizer().getChildren().add(gameView.getBottomBox());
					gameView.getOrganizer().getChildren().add(gameView.getBackButton());
				});
				
		// Button Animation
		Images.suspect.setOnMouseEntered(e -> {
			Images.suspect.setImage(Images.suspectImage);
		});
		
		Images.suspect.setOnMouseExited(e -> {
			Images.suspect.setImage(Images.enabled);
		});
	}
	
	public void enableRoll(){
		Images.roll.setImage(Images.enabled);
		
		// Button Animation
		Images.roll.setOnMouseEntered(e -> {
			Images.roll.setImage(Images.rollImage);
		});
			Images.roll.setOnMouseExited(e -> {
			Images.roll.setImage(Images.enabled);
		});
	}
	
	public void enablePassage(){
		Images.passage.setImage(Images.enabled);
		
		// Gang nehmen
		Images.passage.setOnMouseClicked(e -> {
			Sounds.gangSound();
		});
		
		// Button Animation
		Images.passage.setOnMouseEntered(e -> {
			Images.passage.setImage(Images.passageImage);
		});
			Images.passage.setOnMouseExited(e -> {
			Images.passage.setImage(Images.enabled);
		});
	}

	public AussergewohnlichesZugfenster getGameView() {
		return gameView;
	}

	public void setGameView(AussergewohnlichesZugfenster gameView) {
		this.gameView = gameView;
	}
}
