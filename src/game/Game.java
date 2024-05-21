package game;

import player.Player;
import table.Box;
import table.BuildableProperty;
import table.Colors;
import table.Property;
import utility.ScannerUtilities;

import java.awt.*;
import java.util.Arrays;

public class Game {
    public static final int NUMBER_OF_PLAYERS = 4;

    public static void main(String[] args) {
        ScannerUtilities scannerUtilities = new ScannerUtilities();
        Monopoly monopoly = new Monopoly();
        Player[] players;
        int choice;
        int turn = 0;

        //System.out.println("\n\n---BENVENUTI IN MONOPOLY!---\n");
        System.out.println("\n\n---BENVENUTI IN MONOPOLY!---\n");


        System.out.println("Inserire il nome e il simbolo\n");
        players = generatePlayers(scannerUtilities, monopoly);
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

    public static Player newPlayer(ScannerUtilities scannerUtilities) {
        String name = "";
        String symbol = "";
        while (name.isEmpty())
            name = scannerUtilities.readString("Inserisci il nome: ").trim();
        while (symbol.isEmpty())
            symbol = scannerUtilities.readString("Inserisci il simbolo: ").trim();
        return new Player(name, symbol.substring(0, 1), 0);
    }

    public static Player[] generatePlayers(ScannerUtilities scannerUtilities, Monopoly monopoly) {
        Player[] players = new Player[Game.NUMBER_OF_PLAYERS];
        Colors[] defaultColors = {Colors.RED, Colors.BLUE, Colors.GREEN, Colors.YELLOW};
        players[0] = newPlayer(scannerUtilities);
        players[0].setColor(defaultColors[0]);
        monopoly.addPlayerToBox(players[0]);
        for (int i = 1; i < Game.NUMBER_OF_PLAYERS; ) {
            boolean isEquals = false;
            Player player = newPlayer(scannerUtilities);
            player.setColor(defaultColors[i]);
            for (int j = 0; j < i; j++) {
                if (player.equals(players[j])) {
                    System.out.println("Il giocatore esiste già. Inserisci un nome o un simbolo diverso.");
                    isEquals = true;
                    break;
                }
            }
            if (!isEquals) {
                players[i] = player;
                monopoly.addPlayerToBox(players[i]);
                i++;
            }
        }
        return players;
    }
}