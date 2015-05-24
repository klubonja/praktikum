package view;

public class GameFramePresenter {
	
	private GameFrameView gfv;
	
	public GameFramePresenter(GameFrameView gfv){
		
		this.gfv = gfv;
		
		startEvents();
		
	}
	
	public void startEvents(){
		gfv.rollDice.setOnAction(e -> rollTheDice());
	}
	
	public void rollTheDice(){
		
		
	}

}
