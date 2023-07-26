package fr.xnxa.tetrix;

import javafx.scene.canvas.GraphicsContext;

/**
 * Central class which coordinates the whole game.
 */
public class Game implements Tickable {
	
	private Grid grid;
	private GraphicsContext gc;
	private GameTimer clk;

	/** Piece currently falling */
	private Piece currentPiece;
	
	/** Is the game paused ?*/
	private boolean isPaused = false;
	
	/**
	 * Constructor of the game
	 * @param gc Graphical Context linked to the Canva on which we want to draw
	 */
	public Game(GraphicsContext gc) {
		this.gc = gc;

		MusicPlayer.start();

		clk = new GameTimer(this);
	}
	
	/**
	 * Start the game, pieces are falling !
	 */
	public void start() {
		grid = new Grid();

		GameStats.raz();

		clk.setTickSpeed(1);
		clk.start();

		currentPiece = choosePiece();
		grid.add(currentPiece);
		grid.draw(gc);
	}

	/** Permanently stop the game. */
	public void stop() {
		clk.stop();
		MusicPlayer.stop();
	}

	/**
	 * Method called by the game timer.
	 */
	@Override
	public void tick() {
		movePieceDown();
		grid.draw(gc);
	}
	
	/**
	 * Move piece left by 1.
	 */
	public void left() {
		if (currentPiece != null)
			currentPiece.moveLeft();
		grid.draw(gc);
	}

	/**
	 * Move piece right by 1.
	 */
	public void right() {
		if (currentPiece != null)
			currentPiece.moveRight();
		grid.draw(gc);
	}

	/**
	 * Rotate piece.
	 */
	public void rotateL() {
		if (currentPiece != null)
			currentPiece.rotateLeft();
		grid.draw(gc);
	}

	/**
	 * Rotate piece.
	 */
	public void rotateR() {
		if (currentPiece != null)
			currentPiece.rotateRight();
		grid.draw(gc);
	}

	/**
	 * Make the piece go as down as possible.
	 */
	public void fullDown() {
		
		while (movePieceDown() && currentPiece != null) {
			// Doing nothing because the action is made 
			// in movePieceDown() ...
		}
		
		grid.draw(gc);
	}
	
	/**
	 * Move the piece down by 1.
	 */
	public void down() {
		movePieceDown();
		grid.draw(gc);
	}
	
	/**
	 * Pause or unpause the game.
	 */
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
	
	/**
	 * Tries to move the piece down by one block, 
	 * if it can't the lock the piece and add new one.
	 * @return true if the piece moved down.
	 */
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
