package game;

import javafx.application.Platform;
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
    private Label currentPlayer;
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



    public void tableScene(Monopoly monopoly, Player[] players) {

        try {

            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/game/TableScene.fxml")));
            root = loader.load();
            TableController controller = loader.getController();

            this.monopoly = monopoly;
            this.players = players;

            controller.name1.setText(players[0].getName());
            controller.name2.setText(players[1].getName());
            controller.name3.setText(players[2].getName());
            controller.name4.setText(players[3].getName());

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

    public void mostraTurno(String nome){
        currentPlayer.setText(nome);
        return;
    }


}
