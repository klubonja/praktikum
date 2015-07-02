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
	private AussergewohnlichesZugfenster zug;
	
	public KrasserStack(BallEbene2 ballEbeneEingabe, BoardView guiEingabe){
		
		this.ballEbene = ballEbeneEingabe;
		this.boardView = guiEingabe;
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
		zug = new AussergewohnlichesZugfenster();
		StackPane.setMargin(boardView, new Insets(0,0,0,0));
		StackPane.setMargin(ballEbene, new Insets(0,0,0,0));
		
		this.getChildren().addAll(boardView, ballEbene, zug);
		
		
	}
	
	
	public void doThatTitleThang(String title){
		this.stage.setTitle(title);
	}

	public AussergewohnlichesZugfenster getZug() {
		return zug;
	}

	public void setZug(AussergewohnlichesZugfenster zug) {
		this.zug = zug;
	}

	
}
