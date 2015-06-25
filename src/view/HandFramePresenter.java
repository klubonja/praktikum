package view;

import javafx.scene.effect.Glow;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.image.ImageView;

public class HandFramePresenter {
	
	HandFrameView view;
	Glow cardEffect = new Glow(0.5);
	
	PerspectiveTransform perspectiveTrasform = new PerspectiveTransform();
	 
	
	HandFramePresenter(HandFrameView view){
		
		this.view = view;
		
		perspectiveTrasform.setUlx(200.0);
		perspectiveTrasform.setUly(100.0);
		perspectiveTrasform.setUrx(300.0);
		perspectiveTrasform.setUry(200.0);
		perspectiveTrasform.setLrx(100.0);
		perspectiveTrasform.setLry(60.0);
		perspectiveTrasform.setLlx(10.0);
		perspectiveTrasform.setLly(20.0);
		 
		// cardEffect.setInput(perspectiveTrasform);
		
		activateEvents();
		
	}
	
	public void activateEvents(){
		view.firstCard.setOnMouseEntered(e -> showFirstCard());
		view.secondCard.setOnMouseEntered(e -> showSecondCard());
		view.thirdCard.setOnMouseEntered(e -> showThirdCard());
		view.firstCard.setOnMouseExited(e -> removeEffect(view.firstCard));
		view.secondCard.setOnMouseExited(e -> removeEffect(view.secondCard));
		view.thirdCard.setOnMouseExited(e -> removeEffect(view.thirdCard));
		
	}
	
	public void showFirstCard(){
		view.getChildren().removeAll(view.firstCard, view.secondCard, view.thirdCard);
		view.getChildren().addAll(view.thirdCard, view.secondCard, view.firstCard);
		view.firstCard.setEffect(cardEffect);
		
	}
	
	public void showSecondCard(){
		view.getChildren().removeAll(view.firstCard, view.secondCard, view.thirdCard);
		view.getChildren().addAll(view.thirdCard, view.firstCard, view.secondCard);
		view.secondCard.setEffect(cardEffect);
		
	}
	
	public void showThirdCard(){
		view.getChildren().removeAll(view.firstCard, view.secondCard, view.thirdCard);
		view.getChildren().addAll(view.firstCard, view.secondCard, view.thirdCard);
		view.thirdCard.setEffect(cardEffect);
		
	}
	
	public void removeEffect(ImageView card){
		card.setEffect(null);
		
	}

}
