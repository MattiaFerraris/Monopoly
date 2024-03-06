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

        System.out.println("\n\n---BENVENUTI AL GIOCO DEL MONOPOLY---\n");

        System.out.println("Inserisci i dati dei Giocatori \n(Nome e Simbolo)");
        players = monopoly.generatePlayers(scannerUtilities);

        while (!monopoly.isGameOver(players)) {
            //System.out.print("\033[H\033[2J");
            monopoly.showTable();

            System.out.println("Turno di " + players[turn].getName());
            choice = scannerUtilities.readInt("1 Controllo bilancio \n2 Lancia dado  \n:");

            switch (choice) {
                case 1:
                    monopoly.showBalance(players[turn]);
                    break;

                case 2:
                    monopoly.movePlayer(players[turn]);
                    turn = nextTurn(turn);
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
