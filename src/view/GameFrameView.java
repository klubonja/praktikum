package view;


import javafx.geometry.VPos;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;



/**
 * @since 26.05.2015
 * @version 2.0
 * @author Kristi Lubonja
 * 
 * 
 *
 */

public class GameFrameView extends GridPane{
	
	MenuBar menu;
	GridPane leftGrid;
	NotesView notes;
	DiceView dice;
	ChatView chat;
	BoardView board;

	
	public GameFrameView(){
		
		/**
		* Adds the size and number of the Rows and Columns of the main GridPane
		* (2 Rows x 2 Columns).
		*/
		this.getRowConstraints().add(new RowConstraints(25));
		this.getRowConstraints().add(new RowConstraints(700));
		this.getColumnConstraints().add(new ColumnConstraints(700));
		this.getColumnConstraints().add(new ColumnConstraints(650));
		
		/**
		 * Adds the MenuBar and Menus at the top of the screen.
		 */
		menu = new MenuBarView();
		
		/**
		 * Adds the Game Board frame.
		 */
		board = new BoardView(25,25);
		
		/**
		 * Adds the Notes frame.
		 */
		notes = new NotesView();
		
		/**
		 * Adds the Chat frame.
		 */
		chat = new ChatView();
		
		/**
		 * Adds the Dices frame.
		 */
		dice = new DiceView();
		
		
		/**
		 * Left part of the whole GameFrame,
		 * Only for a better organising of objects.
		 */
		leftGrid = new GridPane();
		leftGrid.setGridLinesVisible(true);
		leftGrid.getRowConstraints().add(new RowConstraints(450));
		leftGrid.getRowConstraints().add(new RowConstraints(200));
		leftGrid.getColumnConstraints().add(new ColumnConstraints(325));
		leftGrid.getColumnConstraints().add(new ColumnConstraints(325));
		GridPane.setConstraints(notes,1,0);
		GridPane.setConstraints(dice,0,1);
		GridPane.setConstraints(chat,1,1);
		leftGrid.getChildren().addAll(notes,chat,dice);
		
		
		GridPane.setConstraints(menu,0,0);
		GridPane.setColumnSpan(menu, 2);
		GridPane.setValignment(menu, VPos.TOP);
		GridPane.setConstraints(board,0,1);
		GridPane.setConstraints(leftGrid, 1,1);
		this.getChildren().addAll(board, menu, leftGrid);
		this.setGridLinesVisible(true);
		
		}
	
	}


