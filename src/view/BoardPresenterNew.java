package view;

import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.util.Duration;
import kacheln.Kachel;
import model.Player;

/**
 * @since 24.05.2015
 * @version 28.05.2015
 * @author Benedikt Mayer
 *
 * 
 */
public class BoardPresenterNew {

	private BoardView view;
	private ZugFenster viewImRaum = new ZugFenster();

	/**
	 * Hier wird die Stage gestartet und den Kacheln eine Methode f�r
	 * setOnMouseClicked zugewiesen.
	 * 
	 * @param primaryStage
	 * @throws Exception
	 */
	public BoardPresenterNew(BoardView view, Circle Playerdarstellung) {
		buttonManager();
		this.view = view;
	}
	/**
	 * Arrangiert was jeder Button in dem Spiel machen soll.
	 */
	public void buttonManager() {
		viewImRaum.getAnklage().setOnMouseClicked(
				new EventHandler<MouseEvent>() {
					public void handle(MouseEvent e) {
						viewImRaum.getButtonsBox().getChildren()
								.remove(viewImRaum.getAnklage());
						viewImRaum.getButtonsBox().getChildren()
								.remove(viewImRaum.getWurfel());
						viewImRaum.getButtonsBox().getChildren()
								.remove(viewImRaum.getGang());
						viewImRaum
								.getButtonsBox()
								.getChildren()
								.addAll(viewImRaum.getPersonenListe(),
										viewImRaum.getWaffenListe(),
										viewImRaum.getZimmerListe());
						viewImRaum.getBottomBox().getChildren()
								.add(viewImRaum.getAnklageButton());
					}
				});
		viewImRaum.getAnklageButton().setOnMouseClicked(
				new EventHandler<MouseEvent>() {
					public void handle(MouseEvent e) {
						System.out.println("Der Spieler entschied sich fuer: "
								+ viewImRaum.getPersonenListe().getValue()
								+ ", " + viewImRaum.getWaffenListe().getValue()
								+ " und Raum.");
						viewImRaum.close();
					}
				});
	}

}