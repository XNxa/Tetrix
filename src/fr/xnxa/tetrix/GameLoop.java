package fr.xnxa.tetrix;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Window;

public class GameLoop extends AnimationTimer {


	private long previousTime = 0;
	private long interval = 1_000_000_000; // 1s
    private boolean isPaused = false;
	private boolean endGame = false;
	private Piece currentPiece;
	private Grid grid;
	private GraphicsContext gc;
	private ScoreSaver sc = new ScoreSaver();


	public GameLoop(GraphicsContext gc) {
		grid = new Grid();
		this.gc = gc;
		grid.draw(gc);

	}

	@Override
	public void handle(long currentTime) {
		
		
		
		if (previousTime == 0) {
			previousTime = currentTime;
			return;
		}

		if (currentTime - previousTime >= interval) {
			if (!isPaused && !endGame) {
				movePieceDown();
			}
			previousTime = currentTime;
		}
		grid.draw(gc);
	}


	/**
	 * Start the tetrix Game.
	 */
	public void startGame() {
		grid.resetGame();
		currentPiece = drawAPiece();
		grid.add(currentPiece);


		start(); // Start the timer
	}


	public void left() {
		if (currentPiece != null)
			currentPiece.moveLeft();
		grid.draw(gc);
	}


	public void right() {
		if (currentPiece != null)
			currentPiece.moveRight();
		grid.draw(gc);
	}


	public void rotateL() {
		if (currentPiece != null)
			currentPiece.rotateLeft();
		grid.draw(gc);
	}


	public void rotateR() {
		if (currentPiece != null)
			currentPiece.rotateRight();
		grid.draw(gc);
	}

	public void down() {
		movePieceDown();
	}

	public void pause() {
		isPaused = !isPaused;
		grid.pause();
		MusicPlayer.pause();
	}

	private void movePieceDown() {
		// Move the active piece down by one unit
		if (currentPiece != null && !currentPiece.moveDown()) {
			// Lock the piece in place and generate a new active piece
			grid.lockPiece(currentPiece);
			currentPiece = drawAPiece();
			if (currentPiece == null) {
				// signal for end of the game !
				endGame = true;
				grid.endGame();
				Platform.runLater(()->{
					sc.appendScore(new Score(askPlayerName(), new CurrentScore().getScore()));
				});
			} else {
				grid.add(currentPiece);
			}
		}

	}

	private String askPlayerName() {
		
		TextInputDialog window = new TextInputDialog();
		window.setTitle("What's yout name ?");
		window.setHeaderText("Enter your name :");
		window.showAndWait();
			
		return window.getResult();
	}

	/**
	 * Draw a random piece between all shapes available.
	 *
	 * @return Piece
	 */
	private Piece drawAPiece() {
		/*
		 * Shape[] shapes = Shape.values();
		 *
		 * Random random = new Random(); int index = random.nextInt(shapes.length);
		 * return new Piece(4, 1, shapes[index], grid);
		 */

		Piece resu = ShapesDebug.next(grid);
		return resu;
	}

}
