package finderOfPaths;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import view.BoardView;
import view.AussergewohnlichesZugfenster;

public class KrasserStack extends StackPane {

	private Stage stage;
	private Scene scene;
	
	private BallEbene2 ballEbene;
	private BoardView boardView;
	private AussergewohnlichesZugfenster zugView;
	
	public KrasserStack(BallEbene2 ballEbeneEingabe, BoardView guiEingabe, AussergewohnlichesZugfenster zug){
		this.ballEbene = ballEbeneEingabe;
		this.boardView = guiEingabe;
		this.zugView = zug;
		boardView.start();
		ballEbene.start();
		
		
		layoutStuff();
	}
	
	public void start(){
		stage = new Stage();
		scene = new Scene(this, 700, 700);
		stage.setScene(scene);
		
	//	stage.show();
		
	//	doThatTitleThang("hans");
	}
	
	public void layoutStuff(){
		StackPane.setMargin(boardView, new Insets(0,0,0,0));
		StackPane.setMargin(ballEbene, new Insets(0,0,0,0));
		
		this.getChildren().addAll(boardView, ballEbene, zugView);
		
		
	}
	
	
	public void doThatTitleThang(String title){
		this.stage.setTitle(title);
	}

	public AussergewohnlichesZugfenster getZugView() {
		return zugView;
	}

	public void setZugView(AussergewohnlichesZugfenster zugView) {
		this.zugView = zugView;
	}

	public BallEbene2 getBallEbene() {
		return ballEbene;
	}

	public BoardView getBoardView() {
		return boardView;
	}

	public void setBallEbene(BallEbene2 ballEbene) {
		this.ballEbene = ballEbene;
	}

	public void setBoardView(BoardView boardView) {
		this.boardView = boardView;
	}
}
