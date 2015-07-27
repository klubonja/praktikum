package cluedoNetworkGUI;

import java.io.File;

import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class Winner extends StackPane{

	Stage parent;
	
	String path = "src/media/videos/winner.mp4";
	Media video = new Media(new File(path).toURI().toString());
	MediaPlayer mp = new MediaPlayer(video);
	MediaView view = new MediaView(mp);
	
	public Winner(Stage stage){
		super();
		
		this.parent = stage;
		this.getChildren().addAll(view);
		mp.setVolume(0.1);
		mp.play();
		mp.setOnEndOfMedia(new Runnable() {public void run(){stage.close();}});
	}
}
