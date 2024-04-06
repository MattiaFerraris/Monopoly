package game;

import player.Player;
import utility.ScannerUtilities;

public class Game {
    public static final int NUMBER_OF_PLAYERS = 3;


    public static void main(String[] args) {
        ScannerUtilities scannerUtilities = new ScannerUtilities();
        Monopoly monopoly = new Monopoly();
        Player[] players;
        int choice;
        int turn = 0;

        System.out.println("\n\n---BENVENUTI IN MONOPOLY!---\n");

        System.out.println("Inserire il nome e il simbolo\n");
        players = generatePlayers(scannerUtilities, monopoly);
        monopoly.showTable();

        while (!monopoly.isGameOver(players)) {

            System.out.println("\nTurno di " + players[turn].getName());
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
        players[0] = newPlayer(scannerUtilities);
        monopoly.addPlayerToBox(players[0]);
        for (int i = 1; i < Game.NUMBER_OF_PLAYERS; ) {
            boolean isEquals = false;
            Player player = newPlayer(scannerUtilities);
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