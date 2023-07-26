package fr.xnxa.tetrix;

import java.io.File;
import java.net.URL;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public final class MusicPlayer {

	final static private String path = "assets/";
	final static private String extension = ".mp3";

	private static MediaPlayer mp;
	private static boolean paused;

	private int current_index = 0;

	String music_urls[] = { "tetris" };

	private Runnable onEndofMedia = new Runnable() {

		@Override
		public void run() {
			
			if (current_index >= music_urls.length - 1) {
				current_index = 0;
			} else {
				current_index++;
			}
			
			String chemin = MusicPlayer.path + music_urls[current_index] + extension;
			System.out.println("Player loading next music : " + chemin);
			URL res = getClass().getResource(chemin);
			if (res != null) {
				String path = res.getPath();
				Media media = new Media(new File(path).toURI().toString());
				mp = new MediaPlayer(media);
				mp.setVolume(1.0);
				mp.setOnEndOfMedia(onEndofMedia);
				mp.setAutoPlay(true);
				mp.play();
				paused = false;
			} else {
				System.out.println("erreur ressource " + chemin + " est inaccessible");
			}
		}
	};

	private MusicPlayer() {

		String chemin = MusicPlayer.path + music_urls[current_index] + extension;
		URL res = getClass().getResource(chemin);
		if (res != null) {
			String path = res.getPath();
			Media media = new Media(new File(path).toURI().toString());
			mp = new MediaPlayer(media);
			mp.setVolume(1.0);
			mp.setAutoPlay(true);
			mp.setOnEndOfMedia(onEndofMedia);
			mp.play();
			paused = false;
		} else {
			System.out.println("MediaPlayer init error  : " + chemin + " is unreachable");
		}
	}

	/**
	 * Starts to play the music list.
	 */
	public static void start() {
		new MusicPlayer();
	}

	/**
	 * Pauses the music player.
	 */
	public static void pause() {
		if (mp != null) {
			if (paused) {
				mp.play();
			} else {
				mp.pause();
			}
			paused = !paused;
		}
	}

	/**
	 * Stops the music player.
	 */
	public static void stop() {
		if (mp != null) {
			mp.stop();
		}
	}
}
