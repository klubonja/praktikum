package view;

import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import staticClasses.auxx;
import cluedoNetworkLayer.CluedoPlayer;
import finderOfPaths.Ausloeser;
import finderOfPaths.DerBeweger;
import finderOfPaths.GanzTolleSpielerliste;
import finderOfPaths.RaumBeweger;
import finderOfPaths.Sucher;
import finderOfPaths.Vorschlaege;
import finderOfPaths.WahnsinnigTollerPathfinder;

public class GameFramePresenter {
	
	private GameFrameView gfv;
	private CluedoPlayer currentPlayer;
	private int playerIndex;
	Circle playerCircle;
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
	
	public GameFramePresenter(GameFrameView gfv){
		
		this.gfv = gfv;
		this.currentPlayer = (CluedoPlayer) GanzTolleSpielerliste.playerManager.get(0);
		this.playerCircle = (Circle) GanzTolleSpielerliste.circleManager.get(0);
		
		startEvents();
		
	}
	
	@SuppressWarnings("unused")
	public void startEvents(){
		
		auxx.logsevere("null? : " +currentPlayer.getPosition().getY() +currentPlayer.getPosition().getX());
	
		notesPresenter = new NotesPresenter(gfv.notes);
		handFramePresenter = new HandFramePresenter(gfv.hand);
		menuBarPresenter = new MenuBarPresenter(gfv.menu, gfv);
		
		raumBeweger = new RaumBeweger(gfv.board);		
		beweger = new DerBeweger(gfv.board, gfv.ballEbene, raumBeweger);
		vorschlager = new Vorschlaege(gfv.board);
		pathfinder = new WahnsinnigTollerPathfinder(gfv.board, gfv.ballEbene);
		
		sucher = new Sucher(gfv.board, gfv.ballEbene, beweger, vorschlager, pathfinder,   anweisungen);
		ausloeser = new Ausloeser(gfv.board, beweger, gfv.ballEbene, pathfinder, sucher);
		
		dicePresenter = new DicePresenter(gfv.dice, ausloeser, gfv.board, sucher);
		
		test();
		
		System.out.println("test vorbei");

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
		ausloeser.zuweisung();
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
	
	
	
}
