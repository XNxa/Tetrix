import java.util.LinkedList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Piece {


	int[][][] shapeCoord = {
		// shapeI
	    {{0, -1}, {0, 0}, {0, 1}, {0, 2}},
	    // shapeL
	    {{0, -1}, {0, 0}, {0, 1}, {1, 1}},
	    // shapeJ
	    {{0, -1}, {0, 0}, {0, 1}, {-1, 1}},
	    // shapeT
	    {{0, -1}, {0, 0}, {0, 1}, {1, 0}},
	    // shapeO
	    {{0, 0}, {0, -1}, {1, 0}, {1, -1}},
	    // shapeS
	    {{0, 0}, {0, -1}, {-1, 0}, {1, -1}},
	    // shapeZ
	    {{0, 0}, {0, -1}, {1, 0}, {-1, -1}}
	};


	private static final Color[] colors = {
	    Color.CYAN,      // shapeI - Cyan color
	    Color.ORANGE,    // shapeL - Orange color
	    Color.BLUE,      // shapeJ - Blue color
	    Color.PURPLE,    // shapeT - Purple color
	    Color.YELLOW,    // shapeO - Yellow color
	    Color.GREEN,     // shapeS - Green color
	    Color.RED        // shapeZ - Red color
	};

	private int x;
	private int y;
	private Shape s;
	private final int shape_nb;
	private Grid grid;

	/**
	 * Initialize a new piece.
	 * @param x x_coordinates of the top left block
	 * @param y y_coordinates of the top left block
	 * @param shape Shape of the piece created
	 * @param grid Instance of the grid containing the piece
	 */
	public Piece(int x, int y, Shape shape, Grid grid) {
		this.x = x;
		this.y = y;
		this.s = shape;
		this.grid = grid;
		shape_nb = s.ordinal();

	}

	/**
	 * Draw the piece on a Canva.
	 * @param GraphicsContext associated with the canva
	 */
	public void draw(GraphicsContext gc) {

		double width = gc.getCanvas().getWidth();
		double height = gc.getCanvas().getHeight();

		int xlen = (int) (width / Grid.cols);
		int ylen = (int) (height / Grid.rows);

		gc.setFill(this.getColor());

		for (int i = 0; i < shapeCoord[shape_nb].length; i++) {
			int coordX = shapeCoord[shape_nb][i][0] + x;
			int coordY = shapeCoord[shape_nb][i][1] + y;
			gc.fillRect(xlen * coordX, ylen * coordY, xlen, ylen);
		}

	}

	/**
	 * Get the color of the piece.
	 * @return Color
	 */
	public Color getColor() {
		return colors[shape_nb];
	}

	/**
	 * Get the coordinates of each block in a piece.
	 * @return 2D-array of integer (int[][])
	 */
	public int[][] getBlocksCoord() {
		LinkedList<int[]> coords = new LinkedList<>();

		for (int i = 0; i < shapeCoord[shape_nb].length; i++) {
			int coordX = shapeCoord[shape_nb][i][0] + x;
			int coordY = shapeCoord[shape_nb][i][1] + y;
			coords.add(new int[] { coordX, coordY });
		}

		return coords.stream().toArray(int[][]::new);
	}

	/**
	 * Move a piece down.
	 * @return false if the piece can't go down
	 */
	public boolean moveDown() {
		if (checkCells(0, 1)) {
			this.y += 1;
			return true;
		}
		return false;
	}

	/**
	 * Move a piece left.
	 * @return false if the piece can't go down
	 */
	public boolean moveLeft() {
		if (checkCells(-1, 0)) {
			this.x -= 1;
			return true;
		}
		return false;
	}

	/**
	 * Move a piece right.
	 * @return false if the piece can't go right
	 */
	public boolean moveRight() {

		if (checkCells(1, 0)) {
			this.x += 1;
			return true;
		}
		return false;
	}

	/**
	 * Rotate a piece.
	 * @param clockwise true for clockwise rotation, false for counterclockwise rotation
	 * @return false if the piece can't rotate
	 */
	private boolean rotatePiece(boolean clockwise) {
		int[][] rotatedCoords = new int[shapeCoord[shape_nb].length][2];

	    for (int i = 0; i < shapeCoord[shape_nb].length; i++) {
	        int coordX = shapeCoord[shape_nb][i][0];
	        int coordY = shapeCoord[shape_nb][i][1];

	        if (clockwise) {
	            // Rotate clockwise: (x, y) -> (y, -x)
	            rotatedCoords[i][0] = coordY;
	            rotatedCoords[i][1] = -coordX;
	        } else {
	            // Rotate counterclockwise: (x, y) -> (-y, x)
	            rotatedCoords[i][0] = -coordY;
	            rotatedCoords[i][1] = coordX;
	        }
	    }

	    // Check if the rotated piece can fit into the grid
	    for (int[] rotatedCoord : rotatedCoords) {
	        int coordX = rotatedCoord[0] + x;
	        int coordY = rotatedCoord[1] + y;

	        if (grid.isCellOccupied(coordX, coordY)) {
	            return false; // Rotation is not possible
	        }
	    }

	    // Update the shape coordinates with the rotated coordinates
	    shapeCoord[shape_nb] = rotatedCoords;

	    return true; // Rotation successful
	}

	/**
	 * Rotate a piece clockwise.
	 * @return false if the piece can't rotate
	 */
	public boolean rotateLeft() {
	    return rotatePiece(true);
	}

	/**
	 * Rotate a piece counterclockwise.
	 * @return false if the piece can't rotate
	 */
	public boolean rotateRight() {
	    return rotatePiece(false);
	}

	private boolean checkCells(int dx, int dy) {
		for (int i = 0; i < shapeCoord[shape_nb].length; i++) {
			int coordX = shapeCoord[shape_nb][i][0] + x + dx;
			int coordY = shapeCoord[shape_nb][i][1] + y + dy;
			if (grid.isCellOccupied(coordX, coordY)) {
				return false;
			}
		}
		return true;
	}

}