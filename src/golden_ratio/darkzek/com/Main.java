package golden_ratio.darkzek.com;

import golden_ratio.darkzek.com.ui.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    FXMLLoader loader;

    @Override
    public void start(Stage primaryStage) throws Exception{
        loader = new FXMLLoader(getClass().getResource("ui/sample.fxml"));

        Parent root = loader.load();
        primaryStage.setTitle("Golden Ratio Viewer");
        Scene scene = new Scene(root, 500, 500);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(Controller.class.getResource("styles.css").toExternalForm());
        primaryStage.show();



    }

    @Override
    public void stop() throws Exception {
        super.stop();

        ((Controller) loader.getController()).stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
