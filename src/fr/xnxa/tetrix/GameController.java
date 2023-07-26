package fr.xnxa.tetrix;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;

public class GameController {

	private GameLoop game;
	private CurrentScore sc;

	@FXML
	private Canvas canvas;

	@FXML
	private Label score;

	@FXML
	public void initialize() {
		// Get the graphics context of the canvas
		GraphicsContext gc = canvas.getGraphicsContext2D();
		game = new GameLoop(gc);
		game.startGame();

		sc = new CurrentScore(score);
		sc.raz();

		MusicPlayer.start();

		score.isFocused();

	}

	public void changeScore(long score) {
		this.score.setText("Score : " + score);
	}

	@FXML
	private void restart(ActionEvent e) {
		if (game != null) {
			game.stop();
		}
		initialize();
	}

	@FXML
	private void handleKey(KeyEvent event) {

		switch (event.getCode()) {

		case Q:
			game.left();
			break;

		case D:
			game.right();
			break;

		case E:
			game.rotateL();
			break;

		case A:
			game.rotateR();
			break;

		case S:
			game.down();
			break;

		case SPACE:
			game.pause();
			break;

		default:
			break;
		}
	}

	@FXML
	void onBack(ActionEvent event) {
		// go back : load initial menu stage
		MusicPlayer.stop();
		game.stop(); // stop the animation timer !

		try {
			Main.mainStage();
		} catch (IOException e) {
			System.out.println("Exception while loading file 'menu.fxml' after having played");
		}
	}

}
