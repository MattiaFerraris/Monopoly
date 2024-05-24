package game;

import player.Player;
import table.Box;
import table.BuildableProperty;
import table.Colors;
import table.Property;
import utility.ScannerUtilities;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Game {

    public static void main(String[] args) {
        ScannerUtilities scannerUtilities = new ScannerUtilities();
        Monopoly monopoly = new Monopoly(generatePlayers(scannerUtilities));
        int choice;
        int turn = 0;

        System.out.println("\n\n---BENVENUTI IN MONOPOLY!---\n");
        monopoly.showTable();
        while (!monopoly.isGameOver()) {
            ArrayList<Player> lostPlayers = monopoly.getLostPlayers();

            if(!lostPlayers.isEmpty())
                for(Player player : lostPlayers)
                    System.out.println(player.getColoredName() + " ha perso!");


            Player currentPlayer = monopoly.getCurrentPlayer();

            System.out.println("\nTurno di " + currentPlayer.getColoredName());
            choice = scannerUtilities.readInt("1 Mostra i soldi \n2 Lancia il dado \n:");

            switch (choice) {
                case 1:
                    monopoly.showBalance(currentPlayer);
                    break;
                case 2:
                    int prevPosition = currentPlayer.getPosition();
                    monopoly.movePlayer(currentPlayer);
                    monopoly.showTable();
                    Box box = monopoly.getBox(currentPlayer);
                    if (box instanceof Property property) {
                        if (property.getOwner() == null) {
                            if (scannerUtilities.yesOrNo("Vuoi comprare " + property.getColoredName() + "? (si/no): ")) {
                                if (!monopoly.buyProperty(currentPlayer, property))
                                    monopoly.payPropertyFee(currentPlayer, property);
                            } else
                                monopoly.payPropertyFee(currentPlayer, property);
                        } else if (!property.getOwner().equals(currentPlayer)) {
                            monopoly.payPropertyFee(currentPlayer, property);
                        } else if (monopoly.hasPlayerAllSameColorProperties(currentPlayer, property)) {
                            BuildableProperty buildableProperty = (BuildableProperty) property;
                            if (buildableProperty.getHousesCount() < 4 && scannerUtilities.yesOrNo("Vuoi costruire una casa? (si/no): "))
                                monopoly.buildHouse(currentPlayer, buildableProperty);
                            else if (buildableProperty.getHousesCount() == 4 && buildableProperty.getHotelsCount() == 0 && scannerUtilities.yesOrNo("Vuoi costruire un hotel? (si/no): "))
                                monopoly.buildHotel(currentPlayer, buildableProperty);
                        }
                    } else
                        monopoly.updateBalance(prevPosition, currentPlayer.getPosition(), currentPlayer);
                    monopoly.nextTurn();
                    break;
                default:
                    System.out.println("Scelta non valida");
                    break;

            }
        }
    }


    public static Player newPlayer(ScannerUtilities scannerUtilities) {
        String name = "";
        String symbol = "";
        while (name.isEmpty())
            name = scannerUtilities.readString("Inserisci il nome: ").trim();
        while (symbol.isEmpty())
            symbol = scannerUtilities.readString("Inserisci il simbolo: ").trim();
        return new Player(name, symbol.substring(0, 1), 0);
    }

    public static ArrayList<Player> generatePlayers(ScannerUtilities scannerUtilities) {
        System.out.println("Inserire il nome e il simbolo\n");
        ArrayList<Player>  players = new ArrayList<>(Monopoly.NUMBER_OF_PLAYERS);
        Colors[] defaultColors = {Colors.RED, Colors.BLUE, Colors.GREEN, Colors.YELLOW};
        for (int i = 0; i < Monopoly.NUMBER_OF_PLAYERS; ) {
            Player playerToAdd = newPlayer(scannerUtilities);
            if(players.contains(playerToAdd))
                System.out.println("Nome o simbolo già utilizzato");
            else {
                playerToAdd.setColor(defaultColors[i]);
                players.add(playerToAdd);
                i++;
            }
        }
        return players;
    }
}