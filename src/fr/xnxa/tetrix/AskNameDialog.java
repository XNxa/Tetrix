package fr.xnxa.tetrix;

import javafx.application.Platform;
import javafx.scene.control.TextInputDialog;

/**
 * Class used to open a window to ask the player's name
 */
public class AskNameDialog extends TextInputDialog {
	
	public AskNameDialog(Long points) {
		this.setTitle("What's yout name ?");
		this.setHeaderText("Enter your name :");
		
		Platform.runLater(()->{
			this.showAndWait();
			
			String playername = this.getResult();
			if (playername != null || playername == "") {
				new ScoreSaver().appendScore(new Score(playername, points));				
			}
		});
	}
	
}
