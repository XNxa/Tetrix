package fr.xnxa.tetrix;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class ScoreboardController {
	
	@FXML
	private Button closeButton;
	
	@FXML
	private ListView<String> list;
	
	@FXML
	public void initialize() {
		
		List<String> scores = (new ScoreManager()).getScores();
		
		ObservableList<String> content = FXCollections.observableArrayList(scores);
		list.setItems(content);
	}	
	
	@FXML
	public void onClose(ActionEvent event) {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		
		stage.close();		
	}
	
}
