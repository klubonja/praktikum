package view;


import javafx.geometry.VPos;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import figuren.FigurenView;



public class GameFrameView extends GridPane{
	
	MenuBar menu;
	GridPane rightGrid;
	NotesView notes;
	DiceView dice;
	ChatView chat;
	BoardView board;
	private SpielfeldUndFiguren komplettesFeld;
	private FigurenView figuren;
	
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
		board = new BoardView(24,25);
		
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
		
		figuren = new FigurenView();
		
		komplettesFeld = new SpielfeldUndFiguren(board, figuren);
		
		
		/**
		 * Left part of the whole GameFrame,
		 * Only for a better organising of objects.
		 */
		rightGrid = new GridPane();
		rightGrid.setGridLinesVisible(true);
		rightGrid.getRowConstraints().add(new RowConstraints(450));
		rightGrid.getRowConstraints().add(new RowConstraints(200));
		rightGrid.getColumnConstraints().add(new ColumnConstraints(350));
		rightGrid.getColumnConstraints().add(new ColumnConstraints(300));
		GridPane.setConstraints(notes,1,0);
		GridPane.setConstraints(dice,0,1);
		GridPane.setConstraints(chat,1,1);
		rightGrid.getChildren().addAll(notes,chat,dice);
		
		
		GridPane.setConstraints(menu,0,0);
		GridPane.setColumnSpan(menu, 2);
		GridPane.setValignment(menu, VPos.TOP);
		GridPane.setConstraints(komplettesFeld,0,1);
		GridPane.setConstraints(rightGrid, 1,1);
		this.getChildren().addAll(komplettesFeld, menu, rightGrid);
		this.setGridLinesVisible(true);
		
		}
	
	}


