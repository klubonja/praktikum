package vielCoolererPathfinder;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import kacheln.FeldKachel;
import kacheln.Kachel;
import kacheln.RaumKachel;
import kacheln.TuerKachel;

public class GUI extends GridPane {

	
	private int höhe;
	private int breite;
	
	private Stage stage;
	private Scene scene;
	
	private FeldKachel feld1;
	private FeldKachel feld2;
	private FeldKachel feld3;
	private FeldKachel feld4;
	private FeldKachel feld5;
	private FeldKachel feld6;
	
	private FeldKachel feld7;
	private FeldKachel feld8;
	private FeldKachel feld9;
	private FeldKachel feld10;
	private FeldKachel feld11;
	private FeldKachel feld12;
	
	
	private RaumKachel raum1;
	private RaumKachel raum2;

	
	private TuerKachel tuer1;
	
	private TuerKachel tuer2;
	
	private Kachel [][] kachelArray;
	
	public GUI(int höhe, int breite){
		this.höhe = höhe;
		this.breite = breite;
		
		layoutSachen();
	}
	
	public void start(){
		this.setGridLinesVisible(true);
		stage = new Stage();
		scene = new Scene(this, 500, 500);
		stage.setScene(scene);
		
	}
	
	public void stop(){
		
	}
	
	public void layoutSachen(){
		
		kachelArray = new Kachel [5][5];
		
		feld1 = new FeldKachel("    Feld    ", 0, 0, false, false);
		feld2 = new FeldKachel("    Feld    ", 0, 1, false, false);
		tuer1 = new TuerKachel("      O      ", 0, 2, true, "O" , "", true);
		feld3 = new FeldKachel("    Feld    ", 1, 0, false, false);
		feld4 = new FeldKachel("    Feld    ", 1, 1, false, false);
		feld5 = new FeldKachel("    Feld    ", 1, 2, false, false);
		feld6 = new FeldKachel("    Feld    ", 2, 0, false, false);
		feld6 = new FeldKachel("    Feld    ", 2, 0, false, false);
		feld7 = new FeldKachel("    Feld    ", 0, 3, false, false);
		feld8 = new FeldKachel("    Feld    ", 1, 3, false, false);
		feld9 = new FeldKachel("    Feld    ", 2, 3, false, false);
		feld10 = new FeldKachel("    Feld    ", 0, 4, false, false);
		feld11 = new FeldKachel("    Feld    ", 1, 4, false, false);
		feld12 = new FeldKachel("    Feld    ", 2, 4, false, false);
		raum1 = new RaumKachel("    Raum    ", 2, 1, true, "",  false);
		raum2 = new RaumKachel("    Raum    ", 2, 2, true, "", false);
		this.add(feld1, 0, 0);
		this.add(feld2, 0, 1);
		this.add(tuer1, 0, 2);
		this.add(feld3, 1, 0);
		this.add(feld4, 1, 1);
		this.add(feld5, 1, 2);
		this.add(feld6, 2, 0);
		this.add(raum1, 2, 1);
		this.add(raum2, 2, 2);
		this.add(feld7, 0, 3);
		this.add(feld8, 1, 3);
		this.add(feld9, 2, 3);
		this.add(feld10, 0, 4);
		this.add(feld11, 1, 4);
		this.add(feld12, 2, 4);
		
		kachelArray [0][0] = feld1;
		kachelArray [0][1] = feld2;
		kachelArray [0][2] = tuer1;
		kachelArray [1][0] = feld3;
		kachelArray [1][1] = feld4;
		kachelArray [1][2] = feld5;
		kachelArray [2][0] = feld6;
		kachelArray [2][1] = raum1;
		kachelArray [2][2] = raum2;
		kachelArray [0][3] = feld7;
		kachelArray [1][3] = feld8;
		kachelArray [2][3] = feld9;
		kachelArray [0][4] = feld10;
		kachelArray [1][4] = feld11;
		kachelArray [2][4] = feld12;
		
		feld1.setMinWidth(80);
		feld2.setMinWidth(80);
		feld3.setMinWidth(80);
		feld4.setMinWidth(80);
		feld5.setMinWidth(80);
		feld6.setMinWidth(80);
		feld7.setMinWidth(80);
		feld8.setMinWidth(80);
		feld9.setMinWidth(80);
		feld10.setMinWidth(80);
		feld11.setMinWidth(80);
		feld12.setMinWidth(80);
		raum1.setMinWidth(80);
		raum2.setMinWidth(80);
		tuer1.setMinWidth(80);
		
		feld1.setMinHeight(80);
		feld2.setMinHeight(80);
		feld3.setMinHeight(80);
		feld4.setMinHeight(80);
		feld5.setMinHeight(80);
		feld6.setMinHeight(80);
		feld7.setMinHeight(80);
		feld8.setMinHeight(80);
		feld9.setMinHeight(80);
		feld10.setMinHeight(80);
		feld11.setMinHeight(80);
		feld12.setMinHeight(80);
		raum1.setMinHeight(80);
		raum2.setMinHeight(80);
		tuer1.setMinHeight(80);
		
		
	}

	public FeldKachel getFeld1() {
		return feld1;
	}

	public void setFeld1(FeldKachel feld1) {
		this.feld1 = feld1;
	}

	public FeldKachel getFeld2() {
		return feld2;
	}

	public void setFeld2(FeldKachel feld2) {
		this.feld2 = feld2;
	}

	public FeldKachel getFeld3() {
		return feld3;
	}

	public void setFeld3(FeldKachel feld3) {
		this.feld3 = feld3;
	}

	public FeldKachel getFeld4() {
		return feld4;
	}

	public void setFeld4(FeldKachel feld4) {
		this.feld4 = feld4;
	}

	public FeldKachel getFeld5() {
		return feld5;
	}

	public void setFeld5(FeldKachel feld5) {
		this.feld5 = feld5;
	}

	public FeldKachel getFeld6() {
		return feld6;
	}

	public void setFeld6(FeldKachel feld6) {
		this.feld6 = feld6;
	}

	public RaumKachel getRaum1() {
		return raum1;
	}

	public void setRaum1(RaumKachel raum1) {
		this.raum1 = raum1;
	}

	public RaumKachel getRaum2() {
		return raum2;
	}

	public void setRaum2(RaumKachel raum2) {
		this.raum2 = raum2;
	}

	public TuerKachel getTuer1() {
		return tuer1;
	}

	public void setTuer1(TuerKachel tuer1) {
		this.tuer1 = tuer1;
	}

	public Kachel[][] getKachelArray() {
		return kachelArray;
	}

	public void setKachelArray(Kachel[][] kachelArray) {
		this.kachelArray = kachelArray;
	}
	
	
}
