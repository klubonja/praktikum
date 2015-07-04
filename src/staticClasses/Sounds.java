package staticClasses;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public abstract class Sounds {
	/**
	 * Spielt ein Geraeusch, waehrend die Karten verteilt werden.
	 */
	public static void cardSound() {
		String sound = "media/card.wav";
		Media mediaFile = new Media(new File(sound).toURI().toString());
		MediaPlayer mediaplayer = new MediaPlayer(mediaFile);
		mediaplayer.setAutoPlay(true);
		mediaplayer.setVolume(1);
	}

	/**
	 * Sound fur den Gang nehmen Button
	 */
	public static void gangSound() {
		String sound = "media/gang.mp3";
		Media mediaFile = new Media(new File(sound).toURI().toString());
		MediaPlayer mediaplayer = new MediaPlayer(mediaFile);
		mediaplayer.setAutoPlay(true);
		mediaplayer.setVolume(1);
	}

	/**
	 * Sound fur die Turkacheln
	 */
	public static void doorSound() {
		String sound = "media/door.mp3";
		Media mediaFile = new Media(new File(sound).toURI().toString());
		MediaPlayer mediaplayer = new MediaPlayer(mediaFile);
		mediaplayer.setAutoPlay(true);
		mediaplayer.setVolume(1);
	}

	/**
	 * Sound fur das Wurfeln
	 */
	public static void diceSound() {
		String sound = "media/dice.wav";
		Media mediaFile = new Media(new File(sound).toURI().toString());
		MediaPlayer mediaplayer = new MediaPlayer(mediaFile);
		mediaplayer.setAutoPlay(true);
		mediaplayer.setVolume(1);
	}
}
