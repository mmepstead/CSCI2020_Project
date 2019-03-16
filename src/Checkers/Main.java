

package Checkers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application{
    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        Pane pane = new GridPane();
        Scene scene = new Scene(pane);
        primaryStage.setTitle("Checkers"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }

    public static void main(String[] args) {
        launch(args);
    }
}


