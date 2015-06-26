package view;


import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Player;
import figuren.FigurenView;
import finderOfPaths.BallEbene2;
import finderOfPaths.KrasserStack;




public class GameFrameView extends GridPane{
	
	MenuBarView menu;
	GridPane rightGrid;
	NotesView notes;
	DiceView dice;
	ChatView chat;
	BoardView board;

	//private SpielfeldUndFiguren komplettesFeld;
	//private FigurenView figuren;
	KrasserStack komplettesFeld;
	BallEbene2 ballEbene;

	HandFrameView hand;
	Player player;
	Button view1;
	Button view2;
	Button view3;
	Button view4;
	Button view5;
	Button view6;
	
	private Stage stage;
	private Scene scene;


	
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
		board = new BoardView(25,26);
		
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
		 *  Adds the Stackpane with the field
		 */
		
		ballEbene = new BallEbene2();
		komplettesFeld = new KrasserStack(ballEbene, board);
		
		hand = new HandFrameView();
		
		// Adds the frame for the cards in hand.
		hand = new HandFrameView();
		
		view1 = new Button("View 1");
		view2 = new Button("View 2");
		view3 = new Button("View 3");
		view4 = new Button("View 4");
		view5 = new Button("View 5");
		view6 = new Button("View 6");
		
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
		rightColumn.getChildren().addAll(notes, dice);
		leftColumn.getChildren().addAll(hand, chat, view1, view2, view3, view4, view5, view6);
		/*GridPane.setConstraints(notes,1,0);
		GridPane.setConstraints(dice,1,1);
		GridPane.setConstraints(chat,0,1);
		GridPane.setConstraints(hand,0,0);*/
		
		GridPane.setConstraints(rightColumn,1,0);
		GridPane.setRowSpan(rightColumn, 2);
		GridPane.setConstraints(leftColumn,0,0);
		GridPane.setRowSpan(leftColumn, 2);
		rightGrid.getChildren().addAll(rightColumn, leftColumn);
		
		
		GridPane.setConstraints(menu,0,0);
		GridPane.setColumnSpan(menu, 2);
		GridPane.setValignment(menu, VPos.TOP);

		GridPane.setConstraints(komplettesFeld,0,1);

		GridPane.setValignment(komplettesFeld, VPos.TOP);
		GridPane.setHalignment(komplettesFeld, HPos.LEFT);
		GridPane.setConstraints(rightGrid, 1,1);
		this.getChildren().addAll(komplettesFeld, menu, rightGrid);

		this.setGridLinesVisible(true);
		
		}
	
	public void start(){
		scene = new Scene (this, 1300,700);
		stage = new Stage();
		stage.setScene(scene);
		stage.setResizable(true);
		//stage.setFullScreen(true);
		stage.setTitle("Cluedo");
		stage.show();
		
	}
	
	public void close(){
		this.stage.close();
	}
	
	}


