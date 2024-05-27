package game;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
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
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class NameController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label correctIn;
    @FXML
    private Button conferma;
    @FXML
    private Button tastoAvanti;
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

    Player[] players = new Player[Game.NUMBER_OF_PLAYERS];
    Monopoly monopoly = new Monopoly();
    private boolean confermaClicked = false;

    public void goToStartScene(ActionEvent event) throws IOException {
        StartController start = new StartController();
        start.startScene(event);
    }

    public void goToTableScene(ActionEvent event) throws IOException {
        if (confermaClicked) {
            TableController table = new TableController();
            table.tableScene(event, monopoly, players);
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

    public void generatePlayersCall() {
        if (enableButton()) {
            correctIn.setText("Tutti i giocatori sono validi!");
            players = generatePlayers(monopoly);
            confermaClicked = true;
            tastoAvanti.setDisable(false);
            avantiIMG.setOpacity(1.0);
        } else {
            correctIn.setText("Tasto non schiacciabile");
        }
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
        tastoAvanti.setDisable(true);
        avantiIMG.setOpacity(0.5);
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

        symbolSet.add(symG1.getText());
        symbolSet.add(symG2.getText());
        symbolSet.add(symG3.getText());
        symbolSet.add(symG4.getText());

        return nameSet.size() == Game.NUMBER_OF_PLAYERS && symbolSet.size() == Game.NUMBER_OF_PLAYERS;
    }

    private Player newPlayer(String name, String symbol) {
        return new Player(name, symbol.substring(0, 1), 0);
    }

    public Player[] generatePlayers(Monopoly monopoly) {
        players[0] = newPlayer(nameG1.getText(), symG1.getText());
        players[1] = newPlayer(nameG2.getText(), symG2.getText());
        players[2] = newPlayer(nameG3.getText(), symG3.getText());
        players[3] = newPlayer(nameG4.getText(), symG4.getText());

        for (Player player : players) {
            monopoly.addPlayerToBox(player);
            System.out.println(player.getName() + " " + player.getSymbol());
        }

        return players;
    }
}
