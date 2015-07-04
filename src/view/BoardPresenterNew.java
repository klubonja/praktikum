package view;

import javafx.scene.shape.Circle;

/**
 * @since 24.05.2015
 * @version 28.05.2015
 * @author Benedikt Mayer
 *
 * 
 */
public class BoardPresenterNew {

	private BoardView view;

	/**
	 * Hier wird die Stage gestartet und den Kacheln eine Methode f�r
	 * setOnMouseClicked zugewiesen.
	 * 
	 * @param primaryStage
	 * @throws Exception
	 */
	public BoardPresenterNew(BoardView view, Circle Playerdarstellung) {
		this.view = view;
	}
}
