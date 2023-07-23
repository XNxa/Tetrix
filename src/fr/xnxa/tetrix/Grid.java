package fr.xnxa.tetrix;
import java.util.stream.IntStream;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Grid {

	public final static int cols = 10;
	public final static int rows = 20;

	private Piece movingPiece;

	private Color[][] fixedBlocks = new Color[cols][rows];

	private boolean endGame = false;
	private boolean pauseGame = false;
	

	/**
	 * Is the cell (x, y) occupied by a block ?
	 *
	 * @param x horizontal coordinates
	 * @param y vertical coordinates
	 * @return true if there is a block.
	 */
	public boolean isCellOccupied(int x, int y) {
		if (x >= cols || y >= rows || y < 0 || x < 0) {
			return true;
		}

		return (fixedBlocks[x][y] != null);
	}


	/**
	 * Add a new piece to the game.
	 *
	 * @param piece
	 */
	public void add(Piece piece) {
		movingPiece = piece;
	}


	/**
	 * Draw the current state of the game on a canva.
	 *
	 * @param GraphicsContext associated with the canva
	 */
	public void draw(GraphicsContext gc) {

		double width = gc.getCanvas().getWidth();
		double height = gc.getCanvas().getHeight();

		// Clear the canvas
		gc.clearRect(0, 0, width, height);
		gc.setFill(Color.web("#1a1947"));
		gc.fillRect(0, 0, width, height);

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
					gc.fillRect(xlen * i, ylen * j, xlen, ylen);
				}
				gc.strokeRect(xlen * i, ylen * j, xlen, ylen);
			}
		}

		if (endGame) {
			drawEndGame(gc);
		}
		if (pauseGame) {
			drawPauseGame(gc);
		}

	}

	


	/**
	 * When game is ended, this grid show an "end of game" message.
	 */
	public void endGame() {
		endGame = true;
	}


	public void lockPiece(Piece piece) {
		int[][] blockToLock = piece.getBlocksCoord();
		Color c = piece.getColor();


		for (int[] block : blockToLock) {
			fixedBlocks[block[0]][block[1]] = c;
		}


		movingPiece = null;
		int[] linesToDeletes = detectFullLines();
		CurrentScore.add(linesToDeletes.length);
		deleteLines(linesToDeletes);
	}



	private void deleteLines(int[] linesToDelete) {

		// Sort the linesToDelete array in descending order if it's not empty
		if (linesToDelete.length > 0) {

			// create a new detector and get the moves to do
			DetecteurContiguite detecteur = new DetecteurContiguite(linesToDelete);

			int nb_moves = detecteur.getNbMouvements();

			// Iterate over the moves
			for (int i = 0; i < nb_moves; i++) {
				DetecteurContiguite.CoupleContigu mvt = detecteur.getMouvements(i);
				int index_ligne_haut_du_bloc = mvt.index_depart;
				int nb_decalage_vers_le_bas = mvt.decaler_vers_le_bas_de_x_lignes;
				int combien_de_lignes_a_bouger = mvt.quantite_bougeable;

				// shift down each line, beginning with the last one

				for (int line = index_ligne_haut_du_bloc + combien_de_lignes_a_bouger - 1; line >= 0; line--) {
					for (int k = 0; k < cols; k++) {
						fixedBlocks[k][line + nb_decalage_vers_le_bas] = fixedBlocks[k][line];
					}
				}
			}

			// Clear the top rows (the total number of shifts down done)
			int nb_rows_cleared = detecteur.getNombreLignesASupprimer();
			for (int i = 0; i < nb_rows_cleared; i++) {
				for (int col = 0; col < cols; col++) {
					fixedBlocks[col][i] = null;
				}
			}

	     }
	}

	private int[] detectFullLines() {


		int[] fullLines = IntStream.range(0, rows)
				.filter(i -> IntStream.range(0, cols).allMatch(j -> fixedBlocks[j][i] != null)).toArray();

		/*
		 * int fullLinesLength = fullLines.length;
		 * if (fullLinesLength > 0)
		 * 	System.out.println(fullLinesLength + " lignes à détruire");
		 */

		return fullLines;
	}

	private void drawEndGame(GraphicsContext gc) {
		gc.save();
		gc.setFont(new Font("Arial", 35.0));
		gc.setFill(Color.rgb(194, 255, 179, 0.7));
		double hauteur = 39.0;
		double position_verticale = 168.0;
		gc.fillRect(10.0, position_verticale, 258.0, hauteur);

		gc.setFill(Color.DARKGREEN);

		gc.fillText("GAME ENDED", 20.0, 200.0);
		gc.restore();
	}
	
	private void drawPauseGame(GraphicsContext gc) {
		
		gc.save();
		gc.setFont(new Font("Arial", 35.0));
		gc.setFill(Color.rgb(194, 255, 179, 0.7));
		double hauteur = 39.0;
		double position_verticale = 168.0;
		gc.fillRect(10.0, position_verticale, 258.0, hauteur);

		gc.setFill(Color.DARKGREEN);

		gc.fillText("GAME PAUSED", 12.0, 200.0);
		gc.restore();
			
	}
	
	public void pause() {
		this.pauseGame = !this.pauseGame;
	}

}
