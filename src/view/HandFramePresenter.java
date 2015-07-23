package view;

import java.util.ArrayList;

import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;

public class HandFramePresenter {

	HandFrameView view;

	public HandFramePresenter(HandFrameView view) {
		this.view = view;
		activateEvents();
	}

	public void activateEvents() {
		ArrayList<ImageView> buffer = view.getHand();
		for (ImageView img : view.getHand()) {
//			img.setOnMousePressed(e -> {
//				setCardEffect();
//			});
			img.setOnMouseReleased(e -> {
				img.setEffect(null);
				view.getSelectedCard().getChildren().removeAll(buffer);
				view.getStack().getChildren().remove(view.getSelectedCard());
				view.getCards().getChildren().removeAll(buffer);
				view.getCards().getChildren().addAll(buffer);
			});
			img.setOnMouseEntered(e -> {
				img.setEffect(new Glow(0.8));
			});
			img.setOnMouseExited(e -> {
				img.setEffect(null);
			});
		}
	}

	public void setCardEffect() {
		for (ImageView img : view.getHand()) {
			view.getSelectedCard().setLayoutX(img.getLayoutX());
			view.getSelectedCard().setLayoutY(img.getLayoutY());
			view.getSelectedCard().getChildren().add(img);
			view.getStack().getChildren().add(view.getSelectedCard());
			img.setEffect(new Glow(0.8));
		}
	}
	
	public void removeEffects(){
		for (ImageView img : view.getHand()) {
			img.setEffect(null);
		}
	}
}
