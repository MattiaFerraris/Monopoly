package game;

//librerie javaFX

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

//
import player.Player;
import table.Box;
import table.BuildableProperty;
import table.Colors;
import table.Property;
import utility.ScannerUtilities;


import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

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
        /*System.out.println("\n\n---BENVENUTI IN MONOPOLY!---\n");
        System.out.println("Inserire il nome e il simbolo\n");*/
        launch(args);
    }

    public static void turns(Monopoly monopoly, Player[] players){
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
            System.out.println("\nTurno di " + players[turn].getColoredName());
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