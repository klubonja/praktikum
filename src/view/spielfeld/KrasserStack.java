package view.spielfeld;

import staticClasses.Images;
import view.AussergewohnlichesZugfenster;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
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
		scene = new Scene(this, 696, 725);
		stage.setScene(scene);
	}
	
	/**
	 * Die Insets werden auf 0 gesetzt und die ebenen hinzugefuegt.
	 */
	public void layoutStuff(){
		StackPane.setMargin(Images.bg, new Insets(-25,0,0,-75));
		StackPane.setMargin(boardView, new Insets(0,0,0,0));
		StackPane.setMargin(ballEbene, new Insets(0,0,0,0));
		boardView.setOpacity(0.5);
		this.getChildren().addAll(zugView, Images.bg, boardView, ballEbene);
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
