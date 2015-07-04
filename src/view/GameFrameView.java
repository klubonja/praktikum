package view;


import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import finderOfPaths.BallEbene2;
import finderOfPaths.KrasserStack;
import finderOfPaths.PlayerCircleManager;




public class GameFrameView extends GridPane{
	
	MenuBarView menu;
	GridPane rightGrid;
	NotesView notes;
	DiceView dice;
	ChatView chat;
	BoardView board;

	//private SpielfeldUndFiguren komplettesFeld;
	//private FigurenView figuren;
	private KrasserStack komplettesFeld;
	BallEbene2 ballEbene;
	AussergewohnlichesZugfenster zugView;

	HandFrameView hand;
	
	private Stage stage;
	private Scene scene;
	public final PlayerCircleManager pcManager;


	
	public GameFrameView(PlayerCircleManager pcm){
		pcManager = pcm;

		/*(2 Rows x 2 Columns). */
		this.getRowConstraints().add(new RowConstraints(25));
		this.getRowConstraints().add(new RowConstraints(725));
		this.getColumnConstraints().add(new ColumnConstraints(700));
		this.getColumnConstraints().add(new ColumnConstraints(650));
		this.setVgap(4);
		this.setHgap(4);
		this.setPadding(new Insets(0, 4, 2, 4));
		//this.setGridLinesVisible(true);
		
		
		//Adds the MenuBar and Menus at the top of the screen.
		menu = new MenuBarView();
		
		/**
		 * Adds the Game Board frame.
		 */
		board = new BoardView(25,26);

		//Adds the Notes frame.
		notes = new NotesView();
		
		//Adds the Chat frame.
		chat = new ChatView();

		//Adds the Dices frame.
		dice = new DiceView();
		
		/**
		 *  Adds the Stackpane with the field
		 */
		
		ballEbene = new BallEbene2(pcm);
		zugView = new AussergewohnlichesZugfenster();
		komplettesFeld = new KrasserStack(ballEbene, board, zugView);
		
		//Adds the frame for the Cards in hand.
		hand = new HandFrameView();
		
		/* Left part of the whole GameFrame,
		   only for a better organising of objects. */

		rightGrid = new GridPane();
		rightGrid.getRowConstraints().add(new RowConstraints(450));
		rightGrid.getColumnConstraints().add(new ColumnConstraints(350));
		rightGrid.getColumnConstraints().add(new ColumnConstraints(300));
		rightGrid.setVgap(2);
		rightGrid.setHgap(4);

		// VBox and HBox for dividing elements in two columns
		VBox leftColumn = new VBox(10);
		VBox rightColumn = new VBox(10);
		rightColumn.getChildren().addAll(notes, dice);
		leftColumn.getChildren().addAll(hand, chat);
		
		//
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
	
	public void setStageTitle(String title){
		stage.setTitle(title);
	}
	
	public void close(){
		this.stage.close();
	}


	
	//Getter and Setter Methods
	public MenuBarView getMenu() {
		return menu;
	}


	public void setMenu(MenuBarView menu) {
		this.menu = menu;
	}


	public GridPane getRightGrid() {
		return rightGrid;
	}


	public void setRightGrid(GridPane rightGrid) {
		this.rightGrid = rightGrid;
	}


	public NotesView getNotes() {
		return notes;
	}


	public void setNotes(NotesView notes) {
		this.notes = notes;
	}


	public DiceView getDice() {
		return dice;
	}


	public void setDice(DiceView dice) {
		this.dice = dice;
	}


	public ChatView getChat() {
		return chat;
	}


	public void setChat(ChatView chat) {
		this.chat = chat;
	}


	public BoardView getBoard() {
		return board;
	}


	public void setBoard(BoardView board) {
		this.board = board;
	}


	public HandFrameView getHand() {
		return hand;
	}


	public void setHand(HandFrameView hand) {
		this.hand = hand;
	}

	public Stage getStage() {
		return stage;
	}


	public void setStage(Stage stage) {
		this.stage = stage;
	}


	public Scene getOurScene() {
		return scene;
	}


	public void setScene(Scene scene) {
		this.scene = scene;
	}


	public BallEbene2 getBallEbene() {
		return ballEbene;
	}


	public void setBallEbene(BallEbene2 ballEbene) {
		this.ballEbene = ballEbene;
	}
	
	public KrasserStack getKomplettesFeld() {
		return komplettesFeld;
	}


	public void setKomplettesFeld(KrasserStack komplettesFeld) {
		this.komplettesFeld = komplettesFeld;
	}


	public AussergewohnlichesZugfenster getZugView() {
		return zugView;
	}


	public void setZugView(AussergewohnlichesZugfenster zugView) {
		this.zugView = zugView;
	}
	
	
	
	
}


