package fr.xnxa.tetrix;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MenuController {
	
	@FXML
    private Button startButton;

    @FXML
    private void startGame(ActionEvent event) {
    	Stage stage;
        Parent root;

        stage = (Stage) startButton.getScene().getWindow();
        try {
			root = FXMLLoader.load(getClass().getResource("fxml/tetrix.fxml"));
			Scene scene = new Scene(root);
	        stage.setScene(scene);
	        stage.setMinHeight(620.0);
	        stage.setMinWidth(320.0);
	        stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}

     }
    
    @FXML
    private void scoreButton(ActionEvent event) {
    	
        try {
			
			Pane pane = new FXMLLoader(getClass().getResource("fxml/scoreboard.fxml")).load();
			
			Stage scoreStage = new Stage();
			Scene scoreScene = new Scene(pane);
			
			scoreStage.setScene(scoreScene);
			scoreStage.setTitle("ScoreBoard");
			scoreStage.showAndWait();
			
		} catch (IOException e) {
			e.printStackTrace();
		}	
    	
    }
    
}
