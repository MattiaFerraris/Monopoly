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

        System.out.println("\n\n---BENVENUTI IN MONOPOLY!---\n");

        System.out.println("Inserire il nome e il simbolo\n");
        players = monopoly.generatePlayers(scannerUtilities);

        monopoly.showTable();

        while (!monopoly.isGameOver(players)) {

            System.out.println("\nTurno di" + players[turn].getName());
            choice = scannerUtilities.readInt("1 Mostra i soldi \n2 Lancia il dado \n:");

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
                    System.out.println("Scelta non valida");
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
