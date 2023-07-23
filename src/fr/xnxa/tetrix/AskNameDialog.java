package fr.xnxa.tetrix;

import javafx.application.Platform;
import javafx.scene.control.TextInputDialog;

public class AskNameDialog extends TextInputDialog {
	
	public AskNameDialog(Long points) {
		this.setTitle("What's yout name ?");
		this.setHeaderText("Enter your name :");
		
		Platform.runLater(()->{
			this.showAndWait();
			new ScoreSaver().appendScore(new Score(this.getResult(), points));
		});
	}
	
}
