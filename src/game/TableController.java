package game;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import player.Player;

import java.io.IOException;
import java.util.Objects;

public class TableController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label name1;
    @FXML
    private Label name2;
    @FXML
    private Label name3;
    @FXML
    private Label name4;


    public void tableScene(ActionEvent event, Monopoly monopoly, Player[] players) throws IOException {

        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/game/TableScene.fxml")));
        root = loader.load();
        TableController controller = loader.getController();

        controller.name1.setText(players[0].getName());
        controller.name2.setText(players[1].getName());
        controller.name3.setText(players[2].getName());
        controller.name4.setText(players[3].getName());

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        Game.turns(monopoly, players);
    }
}
