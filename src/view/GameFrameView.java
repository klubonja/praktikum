package view;


import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import model.Player;



public class GameFrameView extends GridPane{
	
	MenuBarView menu;
	GridPane rightGrid;
	NotesView notes;
	DiceView dice;
	ChatView chat;
	BoardView board;
	HandFrameView hand;
	Player player;

	
	public GameFrameView(Player player){
		
		this.player = player;
		
		/**
		* Adds the size and number of the Rows and Columns of the main GridPane
		* (2 Rows x 2 Columns).
		*/
		this.getRowConstraints().add(new RowConstraints(25));
		this.getRowConstraints().add(new RowConstraints(725));
		this.getColumnConstraints().add(new ColumnConstraints(700));
		this.getColumnConstraints().add(new ColumnConstraints(650));
		this.setVgap(2);
		this.setHgap(2);
		
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
		
		hand = new HandFrameView();
		
		
		/**
		 * Left part of the whole GameFrame,
		 * Only for a better organising of objects.
		 */
		rightGrid = new GridPane();
		//rightGrid.setGridLinesVisible(true);
		rightGrid.getRowConstraints().add(new RowConstraints(450));
		//rightGrid.getRowConstraints().add(new RowConstraints(200));
		rightGrid.getColumnConstraints().add(new ColumnConstraints(350));
		rightGrid.getColumnConstraints().add(new ColumnConstraints(300));
		rightGrid.setVgap(2);
		rightGrid.setHgap(2);
		VBox leftColumn = new VBox(10);
		VBox rightColumn = new VBox(10);
		leftColumn.getChildren().addAll(notes, dice);
		rightColumn.getChildren().addAll(hand, chat);
		/*GridPane.setConstraints(notes,1,0);
		GridPane.setConstraints(dice,1,1);
		GridPane.setConstraints(chat,0,1);
		GridPane.setConstraints(hand,0,0);*/
		
		GridPane.setConstraints(leftColumn,1,0);
		GridPane.setRowSpan(leftColumn, 2);
		GridPane.setConstraints(rightColumn,0,0);
		GridPane.setRowSpan(rightColumn, 2);
		rightGrid.getChildren().addAll(leftColumn, rightColumn);
		
		
		GridPane.setConstraints(menu,0,0);
		GridPane.setColumnSpan(menu, 2);
		GridPane.setValignment(menu, VPos.TOP);
		GridPane.setConstraints(board,0,1);
		GridPane.setValignment(board, VPos.TOP);
		GridPane.setHalignment(board, HPos.LEFT);
		GridPane.setConstraints(rightGrid, 1,1);
		this.getChildren().addAll(menu, board, rightGrid);
		this.setGridLinesVisible(true);
		
		}
	
	}


