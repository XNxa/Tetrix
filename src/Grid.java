import java.util.Arrays;
import java.util.stream.IntStream;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Grid {
	
	public final static int cols = 10;
	public final static int rows = 20;
	
	private Piece movingPiece;
	
	private Color[][] fixedBlocks = new Color[cols][rows];

	/**
	 * Is the cell (x, y) occupied by a block ?
	 * @param x horizontal coordinates
	 * @param y vertical coordinates
	 * @return true if there is a block.
	 */
	public boolean isCellOccupied(int x, int y) {
		if (x >= cols || y >= rows | y < 0 | x < 0) {
			return true;
		}
		
		return (fixedBlocks[x][y]!=null);
	}
	
	/**
	 * Add a new piece to the game.
	 * @param piece
	 */
	public void add(Piece piece) {
		movingPiece = piece;
	}
	
	/**
	 * Draw the current state of the game on a canva.
	 * @param GraphicsContext associated with the canva
	 */
	public void draw(GraphicsContext gc) {

		double width = gc.getCanvas().getWidth();
		double height = gc.getCanvas().getHeight();

		// Clear the canvas
        gc.clearRect(0, 0, width, height);
        gc.setFill(Color.web("#1a1947"));
        gc.fillRect(0, 0, width, height);
		
		
		// Draw the moving piece
		if (movingPiece != null) {
			movingPiece.draw(gc);
		}
		
		// Draw the grid
		int xlen = (int) (width / cols);
		int ylen = (int) (height / rows);
		
		gc.setStroke(Color.WHITE);
		gc.setLineWidth(1);
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				if (fixedBlocks[i][j] != null) {
					gc.setFill(fixedBlocks[i][j]);
					gc.fillRect(xlen*i, ylen*j, xlen, ylen);
				}
				gc.strokeRect(xlen*i, ylen*j, xlen, ylen);
			}
		}
		
	}

	public void lockPiece(Piece piece) {
		int[][] blockToLock = piece.getBlocksCoord();
		Color c = piece.getColor();
		
		for (int[] block : blockToLock) {
			fixedBlocks[block[0]][block[1]] = c;
		}
		
		movingPiece = null;
		int[] linesToDeletes = detectFullLines();
		new Score().add(linesToDeletes.length);
		deleteLines(linesToDeletes);
	}
	
	
	private void deleteLines(int[] linesToDelete) {
		// Sort the linesToDelete array in descending order if it's not empty
	    if (linesToDelete.length > 0) {
	        Arrays.sort(linesToDelete);

	        // Iterate over the linesToDelete array in reverse order
	        for (int i = linesToDelete.length - 1; i >= 0; i--) {
	            int currentLine = linesToDelete[i];

	            // Shift all rows above the current line downwards
	            for (int j = currentLine - 1; j >= 0; j--) {
	                for (int k = 0; k < cols; k++) {
	                    fixedBlocks[k][j + 1] = fixedBlocks[k][j];
	                }
	            }
	            
	            // Clear the top row (since it has shifted down)
	            Arrays.fill(fixedBlocks[0], null);
	        }
	    }
	}

	private int[] detectFullLines() {
		
		int[] fullLines = IntStream.range(0, rows)
		        .filter(i -> IntStream.range(0, cols)
		        .allMatch(j -> fixedBlocks[j][i] != null))
		        .toArray();

		return fullLines;
	}
	
}
