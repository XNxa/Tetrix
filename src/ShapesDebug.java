import java.util.Random;

/**
 *
 * A shape generator that can be adjusted to serve up an identical set of shapes
 * at the start of each game. After that, shapes will be drawn randomly.
 *
 */
public final class ShapesDebug {

	/**
	 * put here the shapes in the order you want to play them at the beginning of
	 * each game. At the end of the array, shapes will be drawn randomly.
	 *
	 * Set this array to an empty array if you only want a random draw. <br />
	 * <br />
	 * <i>Example :</i> <code>{Shape.shapeO, Shape.shapeO, Shape.shapeO}</code>
	 * <br />
	 */
	private static Shape myShapes[] = {

	};

	public static Piece next(Grid grid) {
		Piece resu = null;
		int counter = 0;
		int maxCount = myShapes.length - 1;
		// check if there's space left in the first row
		if (grid.isCellOccupied(4, 1)) {
			// this is the signal for end of a game !
			return null;
		}

		// otherwise, serve a new Piece
		if (counter <= maxCount) {
			// serve the next piece with a shape taken from my array
			Shape nextShape = myShapes[counter++];
			resu = new Piece(4, 1, nextShape, grid);
		} else {
			// serve a random shape piece
			Shape[] shapes = Shape.values();
			Random random = new Random();
			int index = random.nextInt(shapes.length);
			resu = new Piece(4, 1, shapes[index], grid);
		}
		return resu;
	}

}
