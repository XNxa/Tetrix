
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;



public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("scene.fxml"));
        primaryStage.setTitle("JavaFX App");
        primaryStage.setScene(new Scene(root, 350.0, 650.0));
        primaryStage.setMinWidth(330.0);
        primaryStage.setMinHeight(650.0);
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("rubik.png")));
        primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
