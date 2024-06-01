package game;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import player.Player;

import table.Box;
import table.Table;

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
    @FXML
    private GridPane gridPane;

    /* DEBUG TOOLS */
    @FXML
    private TextField debugField;
    @FXML
    private Button debugButton;


    public void tableScene(Monopoly monopoly, Player[] players) {

        try {

            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/game/TableScene.fxml")));
            root = loader.load();
            this.monopoly = monopoly;
            this.players = players;

            controller = loader.getController();
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

    public static void showAlert(String title, String text, Player p) { //show alert con doppio testo
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(text);
        alert.setTitle(title);
        alert.setHeaderText(p.getName());
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

    public static void showDices(int d1, int d2, Player player) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("TIRO DEI DADI");
        alert.setHeaderText(player.getName());
        alert.setContentText("Dado 1:  " + d1 + "\nDado 2:  " + d2 + "\nSomma:  " + (d1 + d2));
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

    public void showTable() {
        Platform.runLater(() -> {
            Table table = monopoly.getTable();

            gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
            gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
            gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
            gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);

            gridPane.setHgap(10);
            gridPane.setVgap(10);

            int cellSize = 100;

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {

                    Box currentBox = table.getBox(i);
                    Label l = new Label(currentBox.getBoxDetailsToString());
                    l.setPrefHeight(cellSize);
                    l.setPrefWidth(cellSize);
                    l.setTextAlignment(TextAlignment.LEFT);
                    l.setBackground(Background.fill(Paint.valueOf("white")));
                    l.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                    l.setAlignment(Pos.TOP_LEFT);
                    GridPane.setFillWidth(l, true);
                    GridPane.setFillHeight(l, true);

                    gridPane.add(l, i, j);

                }
            }
        });
    }


    /* DEBUG METHODS */

    public void debug() {
        String[] commands = debugField.getText().split(" ");
        switch (commands[0]) {
            case "move":
                Game.turn(monopoly, this, Integer.parseInt(commands[1]));
                break;
            case "addProperty":
                monopoly.givePlayerProperty(monopoly.getCurrentPlayer().getName(), commands[1]);
                break;
            case "addHouse":
                monopoly.addHouse(monopoly.getCurrentPlayer().getName(), commands[1]);
                break;
        }
    }
}