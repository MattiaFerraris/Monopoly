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
import table.Box;
import table.BuildableProperty;
import table.Property;
import utility.ScannerUtilities;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

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

        //Game.turns(monopoly, players);


        turns(monopoly, players);


    }

    public void turns(Monopoly monopoly, Player[] players) {

        for (Player player : players) {
            System.out.println(player.getColoredName() + " " + player.getColoredSymbol());
        }

        Scanner scanner = new Scanner(System.in);
        ScannerUtilities scannerUtilities = new ScannerUtilities();
        int choice;
        int turn = 0;
        monopoly.showTable();
        while (!monopoly.isGameOver(players)) {
            int playerLost = 0;
            for (int i = 0; i < players.length; i++) {
                if (players[i] == null) {
                    for (int j = i; j < players.length - 1; j++) {
                        players[j] = players[j + 1];
                    }
                    players = Arrays.copyOf(players, players.length - 1);
                    playerLost++;
                }
            }

            if (playerLost > 0 && turn != 0)
                turn--;
            System.out.println("\nTurno di " + players[turn].getColoredName() + " (" + players[turn].getColoredSymbol() + ")");
            choice = scannerUtilities.readInt("1 Mostra i soldi \n2 Lancia il dado \n:");

            switch (choice) {
                case 1:
                    monopoly.showBalance(players[turn]);
                    break;

                case 2:
                    int prevPosition = players[turn].getPosition();
                    monopoly.movePlayer(players[turn]);
                    monopoly.showTable();
                    Box box = monopoly.getBox(players[turn]);
                    if (box instanceof Property property) {
                        if (property.getOwner() == null) {
                            if (scannerUtilities.yesOrNo("Vuoi comprare " + property.getColoredName() + "? (si/no): ")) {
                                if (!monopoly.buyProperty(players[turn], property))
                                    monopoly.payPropertyFee(players[turn], property);
                            } else
                                monopoly.payPropertyFee(players[turn], property);
                        } else if (!property.getOwner().equals(players[turn])) {
                            monopoly.payPropertyFee(players[turn], property);
                        } else if (monopoly.hasPlayerAllSameColorProperties(players[turn], property)) {
                            BuildableProperty buildableProperty = (BuildableProperty) property;
                            if (buildableProperty.getHousesCount() < 4 && scannerUtilities.yesOrNo("Vuoi costruire una casa? (si/no): "))
                                monopoly.buildHouse(players[turn], buildableProperty);
                            else if (buildableProperty.getHousesCount() == 4 && buildableProperty.getHotelsCount() == 0 && scannerUtilities.yesOrNo("Vuoi costruire un hotel? (si/no): "))
                                monopoly.buildHotel(players[turn], buildableProperty);
                        }
                    } else
                        monopoly.updateBalance(prevPosition, players[turn].getPosition(), players[turn]);
                    turn = nextTurn(turn, players.length);
                    break;

                default:
                    System.out.println("Scelta non valida");
                    break;

            }
        }
    }

    public static int nextTurn(int turn, int playersNumber) {
        turn++;
        if (turn == playersNumber)
            turn = 0;
        return turn;
    }
}
