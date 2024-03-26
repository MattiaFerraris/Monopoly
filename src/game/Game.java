package game;

import player.Player;
import utility.ScannerUtilities;

public class Game {
    public static final int NUMBER_OF_PLAYERS = 2;


    public static void main(String[] args) {
        ScannerUtilities scannerUtilities = new ScannerUtilities();
        Monopoly monopoly = new Monopoly();
        Player[] players;
        int choice;
        int turn = 0;

        System.out.println("\n\n---WELCOME TO MONOPOLY!---\n");

        System.out.println("Insert player's name and symbol\n");
        players = monopoly.generatePlayers(scannerUtilities);

        monopoly.showTable();

        while (!monopoly.isGameOver(players)) {

            System.out.println("\n" + players[turn].getName() + "'s TURN");
            choice = scannerUtilities.readInt("1 Check Balance \n2 Throw DICE \n:");

            switch (choice) {
                case 1:
                    monopoly.showBalance(players[turn]);
                    break;

                case 2:
                    monopoly.movePlayer(players[turn]);
                    turn = nextTurn(turn);
                    monopoly.showTable();
                    break;

                default:
                    System.out.println("Choice not valid");
                    break;

            }
        }
    }

    public static int nextTurn(int turn) {
        turn++;
        if (turn >= NUMBER_OF_PLAYERS)
            turn = 0;
        return turn;
    }
}
