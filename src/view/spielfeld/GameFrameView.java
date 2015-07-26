package view.spielfeld;


import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import kacheln.KachelContainer;
import kommunikation.PlayerCircleManager;
import staticClasses.Config;
import view.AussergewohnlichesZugfenster;
import view.ChatView;
import view.DiceView;
import view.HandFrameView;
import view.MenuBarView;
import view.NotesView;
import view.StatusView;
import cluedoNetworkLayer.CluedoGameClient;




public class GameFrameView extends GridPane{
	
	MenuBarView menu;
	GridPane rightGrid;
	NotesView notes;
	DiceView dice;
	ChatView chat;
	BoardView board;
	StatusView statusView;
	Slider volume;

	//private SpielfeldUndFiguren komplettesFeld;
	//private FigurenView figuren;
	private KrasserStack komplettesFeld;
	BallEbene ballEbene;
	AussergewohnlichesZugfenster zugView;

	private HandFrameView hand;
	
	private Stage stage;
	private Scene scene;
	public final PlayerCircleManager pcManager;
	

	
	public GameFrameView(PlayerCircleManager pcm, KachelContainer kacheln, CluedoGameClient client){
		pcManager = pcm;

		/*(2 Rows x 2 Columns). */
		
		RowConstraints row0 = new RowConstraints();
		row0.setPercentHeight(3.34);
		this.getRowConstraints().add(row0);
		
		RowConstraints row1 = new RowConstraints();
		row1.setPercentHeight(96.66);
		this.getRowConstraints().add(row1);
		
		ColumnConstraints column0 = new ColumnConstraints();
		column0.setPercentWidth(53.57);
		this.getColumnConstraints().add(column0);
		
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(46.43);
		this.getColumnConstraints().add(column1);
		
		this.setVgap(4);
		this.setHgap(2);
		this.setPadding(new Insets(0, 4, 2, 4));
		this.setGridLinesVisible(false);
		
		
		//Adds the MenuBar and Menus at the top of the screen.
		menu = new MenuBarView();
		
		
		//Adds the Game Board frame.
		board = new BoardView(kacheln);

		//Adds the Notes frame.
		notes = new NotesView();
		
		//Adds the Chat frame.
		chat = new ChatView();

		//Adds the Dices frame.
		dice = new DiceView();
		
		
		//Adds the Stackpane with the field 
		statusView = new StatusView();
		ballEbene = new BallEbene(pcm);
		zugView = new AussergewohnlichesZugfenster();
		komplettesFeld = new KrasserStack(ballEbene, board, zugView);
		
		//Adds the volume slider
		volume = new Slider(0, 1, 0.5);
		HBox volumeBox = new HBox(4);
		volumeBox.setPrefWidth(200);
		volumeBox.setMaxWidth(200);
		volumeBox.setPrefHeight(10);
		volumeBox.setMaxHeight(10);
		Label volumeLabel = new Label("Volume");
		volumeBox.getChildren().addAll(volumeLabel, volume);
		
		//Adds the frame for the Cards in hand.
		hand = new HandFrameView();

		/* Left part of the whole GameFrame,
		   only for a better organising of objects. */

		rightGrid = new GridPane();
		
		RowConstraints row00 = new RowConstraints();
		row00.setPercentHeight(50);
		rightGrid.getRowConstraints().add(row00);
		
		RowConstraints row01 = new RowConstraints();
		row01.setPercentHeight(50);
		rightGrid.getRowConstraints().add(row01);
		
		ColumnConstraints column00 = new ColumnConstraints();
		column00.setPercentWidth(53.846);
		rightGrid.getColumnConstraints().add(column00);
		
		ColumnConstraints column01 = new ColumnConstraints();
		column01.setPercentWidth(46.154);
		rightGrid.getColumnConstraints().add(column01);
		
//		rightGrid.getRowConstraints().add(new RowConstraints(450));
//		rightGrid.getColumnConstraints().add(new ColumnConstraints(350));
//		rightGrid.getColumnConstraints().add(new ColumnConstraints(300));
		rightGrid.setVgap(2);
		rightGrid.setHgap(4);

		// VBox and HBox for dividing elements in two columns
		VBox leftColumn = new VBox(10);
		leftColumn.setMaxWidth(300);
		leftColumn.setPrefWidth(300);
		VBox rightColumn = new VBox(10);
		rightColumn.setMaxWidth(300);
		rightColumn.setPrefWidth(300);
		rightColumn.getChildren().addAll(notes, dice);
		leftColumn.getChildren().addAll(statusView, hand, chat);
		
		GridPane.setConstraints(chat,0,1);
		GridPane.setConstraints(hand,0,0);
		
		//Add elements to rightGrid
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
		
		GridPane.setConstraints(volumeBox,1,0);
		GridPane.setValignment(volumeBox, VPos.BOTTOM);
		GridPane.setHalignment(volumeBox, HPos.RIGHT);
		

		GridPane.setConstraints(komplettesFeld,0,1);

		GridPane.setValignment(komplettesFeld, VPos.TOP);
		GridPane.setHalignment(komplettesFeld, HPos.LEFT);
		GridPane.setConstraints(rightGrid, 1,1);
		GridPane.setValignment(rightGrid, VPos.CENTER);
		GridPane.setHalignment(rightGrid, HPos.CENTER);
		this.getChildren().addAll(komplettesFeld, menu, volumeBox, rightGrid);
		
		}
	
	
	public void start(){
		
		scene = new Scene (this, Config.GAMEWINDOW_WIDTH, Config.GAMEWINDOW_HEIGHT);
		stage = new Stage();		
		stage.setScene(scene);
		stage.setResizable(false);
		//stage.setFullScreen(true);
		stage.setTitle("Cluedo");
		stage.setAlwaysOnTop(true);
		stage.show();
		
	}
	
	public void setWatchersMode(){
		rightGrid.getChildren().remove(0);
		stage.setWidth(1200);
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


	public BallEbene getBallEbene() {
		return ballEbene;
	}


	public void setBallEbene(BallEbene ballEbene) {
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
	
	public StatusView getStatusView() {
		return statusView;
	}


	public Slider getVolume() {
		return volume;
	}


	public void setVolume(Slider volume) {
		this.volume = volume;
	}
	
	
	
	
}


