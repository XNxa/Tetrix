package fr.xnxa.tetrix;
import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;



public class Main extends Application {
	
	private static URL mainUI;
	private static Stage primaryStage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		mainUI = getClass().getResource("fxml/menu.fxml");
		Main.primaryStage = primaryStage;
		mainStage();
	}
	
	public static void mainStage() throws IOException {
		Parent root = FXMLLoader.load(mainUI);
        primaryStage.setTitle("Tetrix Menu");
        primaryStage.setScene(new Scene(root, 600.0, 400.0));
        primaryStage.setMinWidth(600.0);
        primaryStage.setMinHeight(400.0);
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("assets/rubik.png")));

        primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
