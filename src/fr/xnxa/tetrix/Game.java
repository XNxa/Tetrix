package fr.xnxa.tetrix;

import javafx.scene.canvas.GraphicsContext;

public class Game {

	private Grid grid;
	private GraphicsContext gc;
	private GameTimer clk;

	private Piece currentPiece;

	private boolean isPaused = false;

	public Game(GraphicsContext gc) {
		this.gc = gc;

		MusicPlayer.start();

		clk = new GameTimer(this);
	}

	public void start() {
		grid = new Grid();

		GameStats.raz();

		clk.setTickSpeed(1);
		clk.start();

		currentPiece = choosePiece();
		grid.add(currentPiece);
		grid.draw(gc);
	}

	public void stop() {
		clk.stop();
		MusicPlayer.stop();
	}

	public void tick() {
		movePieceDown();
		grid.draw(gc);
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

	
	public void fullDown() {
		
		while (movePieceDown()) {
			
		}
		
		grid.draw(gc);
	}
	
	public void down() {
		movePieceDown();
		grid.draw(gc);
	}

	public void pause() {
		isPaused = !isPaused;

		if (isPaused) {
			clk.start();
		} else {
			clk.stop();
		}

		grid.pause();
		grid.draw(gc);
		MusicPlayer.pause();
	}

	private boolean movePieceDown() {
		// Move the active piece down by one unit
		if (currentPiece != null && !currentPiece.moveDown()) {
			// Lock the piece in place and generate a new active piece
			grid.lockPiece(currentPiece);
			currentPiece = choosePiece();
			
			clk.setTickSpeed(GameStats.getLevel());
			
			if (currentPiece == null) {
				// signal for end of the game !
				grid.endGame();

				new AskNameDialog(GameStats.getScore());

			} else {
				grid.add(currentPiece);
			}
			return false;
		} else {
			return true;
		}

	}


	/**
	 * Draw a random piece between all shapes available.
	 * @return Piece
	 */
	private Piece choosePiece() {
		Piece resu = ShapesDebug.next(grid);
		return resu;
	}

}
