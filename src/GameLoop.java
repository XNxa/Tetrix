import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;

public class GameLoop extends AnimationTimer {
	
	private long previousTime = 0;
	private long interval = 1_000_000_000; //1s
	
	private Piece currentPiece;
	private Grid grid;
	private GraphicsContext gc;
	
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
            movePieceDown();
            previousTime = currentTime;
        }
	}
	
	/**
	 * Start the tetrix Game.
	 */
	public void startGame() {
		currentPiece = drawAPiece();
		grid.add(currentPiece);
		
		start(); // Start the timer
	}
	
	public void left() {
		currentPiece.moveLeft();
		grid.draw(gc);
	}
	
	public void right() {
		currentPiece.moveRight();
		grid.draw(gc);
	}
	
	public void rotateL() {
		currentPiece.rotateLeft();
		grid.draw(gc);
	}
	
	public void rotateR() {
		currentPiece.rotateRight();
		grid.draw(gc);
	}

	public void down() {
		movePieceDown();
	}

	private void movePieceDown() {
		// Move the active piece down by one unit
        if (!currentPiece.moveDown()) {
        	// Lock the piece in place and generate a new active piece
            grid.lockPiece(currentPiece);
            currentPiece = drawAPiece();
            grid.add(currentPiece);
        }

        grid.draw(gc);
	}

	/**
	 * Draw a random piece between all shapes available.
	 * @return Piece
	 */
	private Piece drawAPiece() {
		Shape[] shapes = Shape.values();
		
		Random random = new Random();
        int index = random.nextInt(shapes.length);
        return new Piece(4, 1, shapes[index], grid);
	}
	
	
}
