package view;

import javafx.scene.effect.Glow;
import javafx.scene.effect.PerspectiveTransform;

public class HandFramePresenter {
	
	private HandFrameView view;
	
	private PerspectiveTransform perspectiveTrasform = new PerspectiveTransform();
	 
	
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
		view.getFirstCard().setOnMouseEntered(e -> showFirstCard());
		view.getSecondCard().setOnMouseEntered(e -> showSecondCard());
		view.getThirdCard().setOnMouseEntered(e -> showThirdCard());
		view.getFirstCard().setOnMouseExited(e -> removeEffectFirst());
		view.getSecondCard().setOnMouseExited(e -> removeEffectSecond());
		view.getThirdCard().setOnMouseExited(e -> removeEffectThird());
		
	}
	
	public void showFirstCard(){
		view.getChildren().removeAll(view.getFirstCard(), view.getSecondCard(), view.getThirdCard());
		view.getChildren().addAll(view.getThirdCard(), view.getSecondCard(), view.getFirstCard());
		Glow cardEffect = new Glow(0.3);
		view.getFirstCard().setEffect(cardEffect);
		
	}
	
	public void showSecondCard(){
		view.getChildren().removeAll(view.getFirstCard(), view.getSecondCard(), view.getThirdCard());
		view.getChildren().addAll(view.getThirdCard(), view.getFirstCard(), view.getSecondCard());
		Glow cardEffect = new Glow(0.3);
		view.getSecondCard().setEffect(cardEffect);
		
	}
	
	public void showThirdCard(){
		view.getChildren().removeAll(view.getFirstCard(), view.getSecondCard(), view.getThirdCard());
		view.getChildren().addAll(view.getFirstCard(), view.getSecondCard(), view.getThirdCard());
		Glow cardEffect = new Glow(0.3);
		view.getThirdCard().setEffect(cardEffect);
		
	}
	
	public void removeEffectFirst(){
		
		view.getFirstCard().setEffect(null);
	}
	
	public void removeEffectSecond(){
		
		view.getSecondCard().setEffect(null);
	}
	
	public void removeEffectThird(){
	
		view.getThirdCard().setEffect(null);
	}

	//Getters and Setters
	public HandFrameView getView() {
		return view;
	}

	public void setView(HandFrameView view) {
		this.view = view;
	}

}
