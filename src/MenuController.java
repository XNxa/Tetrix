import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
			root = FXMLLoader.load(getClass().getResource("tetrix.fxml"));
			Scene scene = new Scene(root);
	        stage.setScene(scene);
	        stage.show();
    
		} catch (IOException e) {
			e.printStackTrace();
		}
        
     }   
}
