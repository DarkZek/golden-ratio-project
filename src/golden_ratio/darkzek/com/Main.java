package golden_ratio.darkzek.com;

import golden_ratio.darkzek.com.ui.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    FXMLLoader loader;

    /**
     * Loads the window
     *
     * @param primaryStage Internal JavaFX
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        loader = new FXMLLoader(getClass().getResource("ui/window.fxml"));

        Parent root = loader.load();
        primaryStage.setTitle("Golden Ratio Viewer");
        Scene scene = new Scene(root, 1200, 1000);

        primaryStage.setMinHeight(800);
        primaryStage.setMinWidth(800);

        primaryStage.setScene(scene);
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
