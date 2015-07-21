package view;

import view.spielfeld.GameFrameView;


public class MenuBarPresenter {
	
	private MenuBarView view;
	
	private GameFrameView gameFrameView;
	
	private InGameMainMenuView menu;
	@SuppressWarnings("unused")
	private InGameMainMenuPresenter inGameMainMenuPresenter;
	
	private InGameQuitView quitMenu;
	@SuppressWarnings("unused")
	private InGameQuitPresenter inGameQuitPresenter;
	
	
	public MenuBarPresenter(MenuBarView view, GameFrameView gameFrameView){
		 
		this.view = view;
		this.gameFrameView = gameFrameView;
		
		activateEvents();
	}
	
	public void activateEvents(){
		view.quit.setOnAction(e -> exitGame());
		view.main.setOnAction(e -> goToMain());
		
	}
	

	public void goToMain(){
		
		menu = new InGameMainMenuView(gameFrameView);
		inGameMainMenuPresenter = new InGameMainMenuPresenter(menu);
		
		gameFrameView.getStage().toFront();
		menu.start();
		}
	
	
	public void exitGame(){
		
		quitMenu = new InGameQuitView(gameFrameView);
		inGameQuitPresenter = new InGameQuitPresenter(quitMenu);
		
		gameFrameView.getStage().toFront();
		quitMenu.start();
		}

	
	//Getter and Setters
	public MenuBarView getView() {
		return view;
	}

	public void setView(MenuBarView view) {
		this.view = view;
	}

	public GameFrameView getGameFrameView() {
		return gameFrameView;
	}

	public void setGameFrameView(GameFrameView gameFrameView) {
		this.gameFrameView = gameFrameView;
	}

	public InGameMainMenuView getMenu() {
		return menu;
	}

	public void setMenu(InGameMainMenuView menu) {
		this.menu = menu;
	}

}
