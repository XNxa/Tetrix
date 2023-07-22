package fr.xnxa.tetrix;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SettingsControler implements Initializable  {

	 @FXML
	    private Button btnApplyClose;
	
    @FXML
    void onApplyClose(ActionEvent event) {
		Stage stage = (Stage) btnApplyClose.getScene().getWindow();
		
		stage.close();	
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}

}
