import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;


public class Controller {
	
	private GameLoop game;
	
	@FXML
	private Canvas canvas;
	
	@FXML
	public void initialize() {
		// Get the graphics context of the canvas
        GraphicsContext gc = canvas.getGraphicsContext2D();
        game = new GameLoop(gc);
		game.startGame();
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
			
		default:
			break;	
		}
	}
	
}
