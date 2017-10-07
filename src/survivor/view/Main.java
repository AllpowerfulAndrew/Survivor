package survivor.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("elements.fxml"));
        primaryStage.setTitle("Survivor");
        primaryStage.setResizable(false);

        Scene scene = new Scene(root, 1100, 690);
        primaryStage.setScene(scene);
        scene.getStylesheets().add((Main.class.getResource("styles.css").toExternalForm()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}