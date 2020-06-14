package golden_ratio.darkzek.com;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Golden Ratio Viewer");
        Scene scene = new Scene(root, 500, 500);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(Controller.class.getResource("styles.css").toExternalForm());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
