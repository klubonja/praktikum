package view.spielfeld;

import view.AussergewohnlichesZugfenster;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Hier geht's richtig krass zur Sache (mit StackPanes)
 * @since ca. 15.06.2015
 * @version 21.07.2015
 * @author Benedikt Mayer
 *
 */
public class KrasserStack extends StackPane {

	private Stage stage;
	private Scene scene;
	
	private BallEbene ballEbene;
	private BoardView boardView;
	private AussergewohnlichesZugfenster zugView;
	
	/**
	 * Der Krasseste Stack aller Zeiten.
	 * Haelt ballEbene, boardView und zugView zusammen
	 * @param ballEbeneEingabe die ballEbene fuer den Stack
	 * @param boardViewEingabe die boardView fuer den Stack
	 * @param zugViewEingabe die zugView fuer den Stack
	 */
	public KrasserStack(BallEbene ballEbeneEingabe, BoardView boardViewEingabe, AussergewohnlichesZugfenster zugViewEingabe){
		this.ballEbene = ballEbeneEingabe;
		this.boardView = boardViewEingabe;
		this.zugView = zugViewEingabe;
		boardView.start();
		ballEbene.start();
		
		layoutStuff();
	}
	
	/**
	 * hier wird stage und scene erzeugt.
	 */
	public void start(){
		stage = new Stage();
		scene = new Scene(this, 700, 700);
		stage.setScene(scene);
	}
	
	/**
	 * Die Insets werden auf 0 gesetzt und die ebenen hinzugefuegt.
	 */
	public void layoutStuff(){
		StackPane.setMargin(boardView, new Insets(0,0,0,0));
		StackPane.setMargin(ballEbene, new Insets(0,0,0,0));
		this.getChildren().addAll(zugView, boardView, ballEbene);
	}
	
	public AussergewohnlichesZugfenster getZugView() {
		return zugView;
	}

	public void setZugView(AussergewohnlichesZugfenster zugView) {
		this.zugView = zugView;
	}

	public BallEbene getBallEbene() {
		return ballEbene;
	}

	public BoardView getBoardView() {
		return boardView;
	}

	public void setBallEbene(BallEbene ballEbene) {
		this.ballEbene = ballEbene;
	}

	public void setBoardView(BoardView boardView) {
		this.boardView = boardView;
	}
}
