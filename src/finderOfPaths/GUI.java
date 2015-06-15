package finderOfPaths;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import kacheln.FeldKachel;
import kacheln.Kachel;
import kacheln.RaumKachel;
import kacheln.TuerKachel;
import enums.Orientation;

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
	
	private char [] keineMoeglichkeiten;
	
	
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
	
	public void layoutSachen(){
		
		kachelArray = new Kachel [höhe][breite];
		
		feld1 = new FeldKachel("    Feld    ", 0, 0, false, null, false, keineMoeglichkeiten);
		feld2 = new FeldKachel("    Feld    ", 0, 1, false, null, false, keineMoeglichkeiten);
		tuer1 = new TuerKachel("      O      ", 0, 2, true, Orientation.O , "", true, keineMoeglichkeiten);
		feld3 = new FeldKachel("    Feld    ", 1, 0, false, null, false, keineMoeglichkeiten);
		feld4 = new FeldKachel("    Feld    ", 1, 1, false, null, false, keineMoeglichkeiten);
		feld5 = new FeldKachel("    Feld    ", 1, 2, false, null, false, keineMoeglichkeiten);
		feld6 = new FeldKachel("    Feld    ", 2, 0, false, null, false, keineMoeglichkeiten);
		feld6 = new FeldKachel("    Feld    ", 2, 0, false, null, false, keineMoeglichkeiten);
		feld7 = new FeldKachel("    Feld    ", 0, 3, false, null, false, keineMoeglichkeiten);
		feld8 = new FeldKachel("    Feld    ", 1, 3, false, null, false, keineMoeglichkeiten);
		feld9 = new FeldKachel("    Feld    ", 2, 3, false, null, false, keineMoeglichkeiten);
		feld10 = new FeldKachel("    Feld    ", 0, 4, false, null, false, keineMoeglichkeiten);
		feld11 = new FeldKachel("    Feld    ", 1, 4, false, null, false, keineMoeglichkeiten);
		feld12 = new FeldKachel("    Feld    ", 2, 4, false, null, false, keineMoeglichkeiten);
		raum1 = new RaumKachel("    Raum    ", 2, 1, true, null,"",  false, keineMoeglichkeiten);
		raum2 = new RaumKachel("    Raum    ", 2, 2, true, null, "", false, keineMoeglichkeiten);
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
		
		
		
		feld1.setMaxWidth(80);
		feld2.setMaxWidth(80);
		feld3.setMaxWidth(80);
		feld4.setMaxWidth(80);
		feld5.setMaxWidth(80);
		feld6.setMaxWidth(80);
		feld7.setMaxWidth(80);
		feld8.setMaxWidth(80);
		feld9.setMaxWidth(80);
		feld10.setMaxWidth(80);
		feld11.setMaxWidth(80);
		feld12.setMaxWidth(80);
		raum1.setMaxWidth(80);
		raum2.setMaxWidth(80);
		tuer1.setMaxWidth(80);
		
		feld1.setMaxHeight(80);
		feld2.setMaxHeight(80);
		feld3.setMaxHeight(80);
		feld4.setMaxHeight(80);
		feld5.setMaxHeight(80);
		feld6.setMaxHeight(80);
		feld7.setMaxHeight(80);
		feld8.setMaxHeight(80);
		feld9.setMaxHeight(80);
		feld10.setMaxHeight(80);
		feld11.setMaxHeight(80);
		feld12.setMaxHeight(80);
		raum1.setMaxHeight(80);
		raum2.setMaxHeight(80);
		tuer1.setMaxHeight(80);
		
	}
		public Kachel[][] getKachelArray() {
		return kachelArray;
	}

	public void setKachelArray(Kachel[][] kachelArray) {
		this.kachelArray = kachelArray;
	}
	
	
}
