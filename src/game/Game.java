package game;

//librerie javaFX

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.Objects;

public class Game extends Application {
    public static final int NUMBER_OF_PLAYERS = 4;

    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Resources/game/StartScreen.fxml")));
            Scene startScreen = new Scene(root);
            stage.setScene(startScreen);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }


}