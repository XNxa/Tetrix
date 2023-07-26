package fr.xnxa.tetrix;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;

public class GameController {

	private Game game;

	@FXML
	private Canvas canvas;

	@FXML
	private Label score;
	
	@FXML
	private Label level;

	@FXML
	public void initialize() {
		// Get the graphics context of the canvas
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		GameStats.setScoreLabel(score, level);
		
		game = new Game(gc);
		game.start();

		score.isFocused();
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
			
		case F:
			game.fullDown();

		default:
			break;
		}
	}

	@FXML
	void onBack(ActionEvent event) {
		game.stop();
		
		try {
			Main.mainStage();
		} catch (IOException e) {
			System.out.println("Exception while loading file 'menu.fxml' after having played");
		}
	}

}
