package game;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.DoubleBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import player.Player;

import java.io.IOException;
import java.util.*;

public class NameController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label correctIn;
    @FXML
    private Button conferma;
    @FXML
    private TextField nameG1;
    @FXML
    private TextField symG1;
    @FXML
    private TextField nameG2;
    @FXML
    private TextField symG2;
    @FXML
    private TextField nameG3;
    @FXML
    private TextField symG3;
    @FXML
    private TextField nameG4;
    @FXML
    private TextField symG4;
    @FXML
    private ImageView avantiIMG;

    Player[] players = new Player[Monopoly.NUMBER_OF_PLAYERS];
    Monopoly monopoly;
    private boolean confermaClicked = false;

    public void goToStartScene(ActionEvent event) throws IOException {
        StartController start = new StartController();
        start.startScene(event);
    }

    public void goToTableScene(ActionEvent event) throws IOException {
        try {
            if (confermaClicked) {
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/game/TableScene.fxml")));
                Parent root = loader.load();

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                TableController controller = loader.getController();
                controller.tableScene(monopoly, players);
                stage.setScene(scene);
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void nameScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/game/NameScene.fxml")));
        root = loader.load();
        NameController controller = loader.getController();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void generatePlayersCall(ActionEvent event) throws IOException {
        players = generatePlayers();
        ArrayList<Player> playersList = new ArrayList<>(List.of(players));
        monopoly = new Monopoly(playersList);
        confermaClicked = true;

        goToTableScene(event);
    }

    @FXML
    private void initialize() {
        BooleanBinding disableBinding = Bindings.createBooleanBinding(
                () -> !enableButton(),
                nameG1.textProperty(),
                nameG2.textProperty(),
                nameG3.textProperty(),
                nameG4.textProperty(),
                symG1.textProperty(),
                symG2.textProperty(),
                symG3.textProperty(),
                symG4.textProperty()
        );

        conferma.disableProperty().bind(disableBinding);

        DoubleBinding opacityBinding = Bindings.createDoubleBinding(
                () -> conferma.isDisabled() ? 0.5 : 1.0,
                conferma.disableProperty()
        );

        avantiIMG.opacityProperty().bind(opacityBinding);
    }

    private boolean enableButton() {
        return notEmptyFields() && checkPlayers();
    }

    private boolean notEmptyFields() {
        return !nameG1.getText().trim().isEmpty() &&
                !nameG2.getText().trim().isEmpty() &&
                !nameG3.getText().trim().isEmpty() &&
                !nameG4.getText().trim().isEmpty() &&
                !symG1.getText().trim().isEmpty() &&
                !symG2.getText().trim().isEmpty() &&
                !symG3.getText().trim().isEmpty() &&
                !symG4.getText().trim().isEmpty();
    }

    private boolean checkPlayers() {
        Set<String> nameSet = new HashSet<>();
        Set<String> symbolSet = new HashSet<>();

        nameSet.add(nameG1.getText());
        nameSet.add(nameG2.getText());
        nameSet.add(nameG3.getText());
        nameSet.add(nameG4.getText());

        symbolSet.add(symG1.getText().substring(0,1));
        symbolSet.add(symG2.getText().substring(0,1));
        symbolSet.add(symG3.getText().substring(0,1));
        symbolSet.add(symG4.getText().substring(0,1));

        return nameSet.size() == Monopoly.NUMBER_OF_PLAYERS && symbolSet.size() == Monopoly.NUMBER_OF_PLAYERS;
    }

    private Player newPlayer(String name, String symbol) {
        return new Player(name, symbol.substring(0, 1), 0);
    }

    public Player[] generatePlayers() {
        players[0] = newPlayer(nameG1.getText(), symG1.getText());
        players[1] = newPlayer(nameG2.getText(), symG2.getText());
        players[2] = newPlayer(nameG3.getText(), symG3.getText());
        players[3] = newPlayer(nameG4.getText(), symG4.getText());

        return players;
    }

    public void generateDefaultPlayers() {
        nameG1.setText("Alice");
        nameG2.setText("Bob");
        nameG3.setText("Charlie");
        nameG4.setText("Dave");
        symG1.setText("A");
        symG2.setText("B");
        symG3.setText("C");
        symG4.setText("D");
    }
}
