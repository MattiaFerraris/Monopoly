package game;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import player.Player;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.Semaphore;

public class TableController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    private Monopoly monopoly;
    private Player[] players;

    @FXML
    private Label name1;
    @FXML
    private Label name2;
    @FXML
    private Label name3;
    @FXML
    private Label name4;
    @FXML
    private Label currentPlayerLabel;
    @FXML
    private Label balance1;
    @FXML
    private Label balance2;
    @FXML
    private Label balance3;
    @FXML
    private Label balance4;
    @FXML
    private Label balanceBank;
    @FXML
    TableController controller;

    private static Boolean choice = false;


    public void tableScene(Monopoly monopoly, Player[] players) {

        try {

            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/game/TableScene.fxml")));
            root = loader.load();
            this.monopoly = monopoly;
            this.players = players;

            name1.setText(players[0].getName() + " (" + players[0].getSymbol() + ")");
            name2.setText(players[1].getName() + " (" + players[1].getSymbol() + ")");
            name3.setText(players[2].getName() + " (" + players[2].getSymbol() + ")");
            name4.setText(players[3].getName() + " (" + players[3].getSymbol() + ")");

            new Thread(() -> Game.playGame(monopoly, players, this)).start();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void updateBalances() {
        Platform.runLater(() -> {
            balance1.setText(players[0].getBalance() + "");
            balance2.setText(players[1].getBalance() + "");
            balance3.setText(players[2].getBalance() + "");
            balance4.setText(players[3].getBalance() + "");
            balanceBank.setText(monopoly.getBankBalance() + "");
        });
    }

    public void showTurn(String nome) {
        currentPlayerLabel.setText(nome);
    }

    public static void showAlert(String text, String type) { //show alert con doppio testo
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(text);
        alert.setTitle(type);
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.showAndWait();
    }
    public static void showAlert(String text) { //show alert con singolo testo
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(text);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.showAndWait();
    }

    public static boolean alertChoice(String s, Player currentPlayer) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setGraphic(null);
        alert.setContentText(s);

        ButtonType siButton = new ButtonType("SI", ButtonBar.ButtonData.OK_DONE);
        ButtonType noButton = new ButtonType("NO", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(siButton, noButton);
        alert.setHeaderText(currentPlayer.getName());
        return alert.showAndWait().get() == siButton;

    }

    public void launchDice() {
        Game.turn(monopoly, this);
    }

    public void saveGame() {
        Game.saveGame(monopoly);
    }
}
