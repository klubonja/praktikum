package view;



import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import kacheln.KachelContainer;
import staticClasses.NetworkMessages;
import staticClasses.auxx;
import cluedoNetworkLayer.CluedoGameClient;
import cluedoNetworkLayer.CluedoPlayer;
import finderOfPaths.Ausloeser;
import finderOfPaths.DerBeweger;
import finderOfPaths.PlayerCircleManager;
import finderOfPaths.RaumBeweger;
import finderOfPaths.Sucher;
import finderOfPaths.Vorschlaege;
import finderOfPaths.WahnsinnigTollerPathfinder;

public class GameFramePresenter {
	
	
	private CluedoGameClient networkGame;
	private CluedoPlayer currentPlayer;
	private GameFrameView gfv;
	private Stage stage;
	
	private Ausloeser ausloeser;
	private Sucher sucher;
	private DerBeweger beweger;
	private RaumBeweger raumBeweger;
	private Vorschlaege vorschlager;
	private WahnsinnigTollerPathfinder pathfinder;
	private char [][] anweisungen;
	
	private NotesPresenter notesPresenter;
	private HandFramePresenter handFramePresenter;
	private MenuBarPresenter menuBarPresenter;
	private DicePresenter dicePresenter;
	private AussergewohnlichesZugfensterPresenter zugPresenter;
	public final PlayerCircleManager pcManager;
	private KachelContainer kacheln;
	private int gameid;

	public GameFramePresenter(GameFrameView gfv, CluedoGameClient game,PlayerCircleManager pcm, int gameid, KachelContainer kacheln){
		this.kacheln = kacheln;
		this.gfv = gfv;
		networkGame = game;
		this.gameid = gameid;
		pcManager = pcm;
		this.currentPlayer = pcManager.getCurrentPlayer();
		
		startEvents();
		setHandler();
		
	}
	
	@SuppressWarnings("unused")
	public void startEvents(){
		
		auxx.logsevere("null? : " +currentPlayer.getPosition().getY() +currentPlayer.getPosition().getX());
	
		notesPresenter = new NotesPresenter(gfv.notes, currentPlayer);
		handFramePresenter = new HandFramePresenter(gfv.getHand());
		menuBarPresenter = new MenuBarPresenter(gfv.menu, gfv);
		
		zugPresenter = new AussergewohnlichesZugfensterPresenter(gfv.getZugView(), pcManager);

		raumBeweger = new RaumBeweger(pcManager, kacheln);		
		beweger = new DerBeweger(gfv.getKomplettesFeld(), gfv.getZugView(), gfv.board, gfv.ballEbene, raumBeweger,pcManager, kacheln);
		vorschlager = new Vorschlaege(gfv.board,pcManager, kacheln);
		pathfinder = new WahnsinnigTollerPathfinder(pcManager, kacheln);
		
		sucher = new Sucher(vorschlager, pathfinder, pcManager);
		ausloeser = new Ausloeser(gfv.getKomplettesFeld(), gfv.getZugView(), gfv.board, beweger, gfv.ballEbene, pathfinder, pcManager, gameid, networkGame, kacheln);

		dicePresenter = new DicePresenter(gfv.dice, gfv,ausloeser, gfv.board, sucher, pcManager, gameid, networkGame, kacheln);
		
		
		test();
	}
	
	public void setHandler(){
		setStyleChatField(false);
		EventHandler<KeyEvent> listenForEnter = new EventHandler<KeyEvent> (){
			@Override
			public void handle(KeyEvent e) {
			        if (e.getCode() == KeyCode.ENTER){
			        	networkGame.sendMsgToServer(
			        			NetworkMessages.chatMsg(
			        					gfv.chat.chatField.getText(), 
			        					networkGame.getGameId(), 
			        					networkGame.getMyNick(),
			        					auxx.now()
			        			)
			        	);	
			        	setStyleChatField(true);
			        }
			    }
			};	
			gfv.chat.chatField.focusedProperty().addListener(new ChangeListener<Boolean>(){				
			    @Override
			    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean hasFocus){		    			
			    	if (hasFocus){ 
			    		gfv.chat.chatField.addEventHandler(KeyEvent.KEY_PRESSED,listenForEnter );  
			    	}
			    	else {
			    		gfv.chat.chatField.removeEventHandler(KeyEvent.KEY_PRESSED,listenForEnter );  
			    	}
			    	setStyleChatField(hasFocus);
			    }
			});
	}
	
	public void setStyleChatField(boolean focused){
		if (focused){
			gfv.chat.chatField.setText("");
    		gfv.chat.chatField.setStyle("-fx-text-fill: #000000;-fx-font-style: normal;");
		}
		else {
			gfv.chat.chatField.setText("confirm with enter");
	    	gfv.chat.chatField.setStyle("-fx-text-fill: #999999;-fx-font-style: italic;");
		}
		
	}
	

	//Getter and Setters
	public GameFrameView getGfv() {
		return gfv;
	}

	public void setGfv(GameFrameView gfv) {
		this.gfv = gfv;
	}
		
	public void test(){
		beweger.anfangsPositionSetzen(0);
		System.out.println("test");
		ausloeser.zuweisung(pcManager);
	}
	

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Ausloeser getAusloeser() {
		return ausloeser;
	}

	public void setAusloeser(Ausloeser ausloeser) {
		this.ausloeser = ausloeser;
	}

	public Sucher getSucher() {
		return sucher;
	}

	public void setSucher(Sucher sucher) {
		this.sucher = sucher;
	}

	public DerBeweger getBeweger() {
		return beweger;
	}

	public void setBeweger(DerBeweger beweger) {
		this.beweger = beweger;
	}

	public Vorschlaege getVorschlager() {
		return vorschlager;
	}

	public void setVorschlager(Vorschlaege vorschlager) {
		this.vorschlager = vorschlager;
	}

	public WahnsinnigTollerPathfinder getPathfinder() {
		return pathfinder;
	}

	public void setPathfinder(WahnsinnigTollerPathfinder pathfinder) {
		this.pathfinder = pathfinder;
	}

	public NotesPresenter getNotesPresenter() {
		return notesPresenter;
	}

	public void setNotesPresenter(NotesPresenter notesPresenter) {
		this.notesPresenter = notesPresenter;
	}

	public HandFramePresenter getHandFramePresenter() {
		return handFramePresenter;
	}

	public void setHandFramePresenter(HandFramePresenter handFramePresenter) {
		this.handFramePresenter = handFramePresenter;
	}

	public MenuBarPresenter getMenuBarPresenter() {
		return menuBarPresenter;
	}

	public void setMenuBarPresenter(MenuBarPresenter menuBarPresenter) {
		this.menuBarPresenter = menuBarPresenter;
	}

	public DicePresenter getDicePresenter() {
		return dicePresenter;
	}

	public void setDicePresenter(DicePresenter dicePresenter) {
		this.dicePresenter = dicePresenter;
	}

	public RaumBeweger getRaumBeweger() {
		return raumBeweger;
	}

	public void setRaumBeweger(RaumBeweger raumBeweger) {
		this.raumBeweger = raumBeweger;
	}
	
	public AussergewohnlichesZugfensterPresenter getZugPresenter() {
		return zugPresenter;
	}

	public void setZugPresenter(AussergewohnlichesZugfensterPresenter zugPresenter) {
		this.zugPresenter = zugPresenter;
	}
	
	
	
}
