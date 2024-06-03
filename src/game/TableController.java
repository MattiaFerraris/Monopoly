package game;

import javafx.event.ActionEvent;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import player.Player;

import table.*;

import java.io.File;
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
    @FXML
    private ImageView dice1Image;
    @FXML
    private ImageView dice2Image;

    /* DEBUG TOOLS */
    @FXML
    private TextField debugField;
    @FXML
    private Button debugButton;


    public void tableScene(Monopoly monopoly, Player[] players) {

        try {

            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/game/TableScene.fxml")));
            stage = new Stage();
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

    //DA RIVEDERE!!
    public void goToStartScene(ActionEvent event) throws IOException {

        Thread.currentThread().interrupt();
        StartController start = new StartController();
        start.startScene(event);
    }


    public void updateBalances() {
        Platform.runLater(() -> {
            balance1.setText(players[0].getBalance()>=0?players[0].getBalance()+" CHF":"ELIMINATO");
            balance2.setText(players[1].getBalance()>=0?players[1].getBalance()+" CHF":"ELIMINATO");
            balance3.setText(players[2].getBalance()>=0?players[2].getBalance()+" CHF":"ELIMINATO");
            balance4.setText(players[3].getBalance()>=0?players[3].getBalance()+" CHF":"ELIMINATO");
            balanceBank.setText(monopoly.getBankBalance() + " CHF");
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
        alert.setX(900);
        alert.setY(150);
        alert.showAndWait();
    }

    public static void showAlert(String text) { //show alert con singolo testo
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(text);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.setX(900);
        alert.setY(150);
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
        alert.setX(900);
        alert.setY(150);
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
            gridPane.getChildren().clear();

            Table table = monopoly.getTable();

            gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
            gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
            gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
            gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);

            gridPane.setHgap(2);
            gridPane.setVgap(2);

            int cellHeight = 73;
            int cellWidth = 85;

            gridPane.getColumnConstraints().clear();
            gridPane.getRowConstraints().clear();

            for (int i = 0; i < table.getX(); i++) {
                ColumnConstraints colConstraints = new ColumnConstraints(cellWidth);
                gridPane.getColumnConstraints().add(colConstraints);
            }

            for (int j = 0; j < table.getY(); j++) {
                RowConstraints rowConstraints = new RowConstraints(cellHeight);
                gridPane.getRowConstraints().add(rowConstraints);
            }

            for (int i = 0; i < table.getX(); i++) {
                for (int j = 0; j < table.getY(); j++) {
                    Box currentBox = table.getBox(i, j);
                    if (currentBox != null) {
                        Label l = new Label(currentBox.getBoxDetailsToString());
                        l.setFont(new Font("Arial", 10));
                        l.setPrefHeight(cellHeight);
                        l.setPrefWidth(cellWidth);
                        l.setTextAlignment(TextAlignment.LEFT);
                        l.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                        l.setAlignment(Pos.TOP_LEFT);
                        l.setStyle("-fx-border-color: black; -fx-border-width: 2;");
                        GridPane.setFillWidth(l, true);
                        GridPane.setFillHeight(l, true);

                        Tooltip tooltip = new Tooltip(currentBox.getBoxDetailsWindow());
                        tooltip.setShowDelay(new Duration(10));
                        tooltip.setFont(new Font("Arial", 10));
                        StackPane stackPane = currentBox.generateStackPane(cellWidth, cellHeight, l);
                        Tooltip.install(l, tooltip);
                        gridPane.add(stackPane, j, i);

                    }
                }
            }
        });
    }
    public void showDice(int d1, int d2) {
        Image[] diceImages = new Image[6];
        for (int i = 0; i < 6; i++) {
            diceImages[i] = new Image(getClass().getResource("/game/Images/Dadi/" + (i + 1) + ".png").toExternalForm(), 100, 100, false, false);
        }
        dice1Image.setImage(diceImages[d1 - 1]);
        dice2Image.setImage(diceImages[d2 - 1]);
    }


    /* DEBUG METHODS */
    public void debug() {
        String[] commands = debugField.getText().split(" ");
        String propertyName;
        switch (commands[0]) {
            case "move":
                Game.turn(monopoly, this, Integer.parseInt(commands[1]));
                break;
            case "addProperty": // esempio: addProperty "Corso Magenta"
                propertyName = debugField.getText();
                propertyName = propertyName.substring(propertyName.indexOf("\"")+1, propertyName.lastIndexOf("\""));
                monopoly.givePlayerProperty(monopoly.getCurrentPlayer().getName(), propertyName);
                break;
            case "addHouse":
                propertyName = debugField.getText();
                propertyName = propertyName.substring(propertyName.indexOf("\"")+1, propertyName.lastIndexOf("\""));
                monopoly.addHouse(monopoly.getCurrentPlayer().getName(), propertyName);
                break;
            case "setBalance":
                monopoly.setPlayerBalance(monopoly.getCurrentPlayer().getName(), Integer.parseInt(commands[1]));
                break;
        }
    }
}