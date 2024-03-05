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
        int turn=0;

        System.out.println("---BENVENUTI AL GIOCO DEL MONOPOLY---");

        System.out.println("Inserisci i dati dei Giocatori \n(nome e simbolo):");
        players = monopoly.generatePlayers(scannerUtilities);

        while (!monopoly.isGameOver()){
            //System.out.print("\033[H\033[2J");
            monopoly.showTable();

            System.out.println("Turno di" + players[turn]);
            choice = scannerUtilities.readInt("1 Controllo bilancio \n2 Lancia dado  \n:");

            switch (choice){
                case 1:
                    monopoly.showBalance(players[turn]);
                    break;

                case 2:
                    monopoly.movePlayer(players[turn]);
                    break;

                default:
                    System.out.println("Scelta non valida");
                    break;

                System.out.println();
            }
            turn = nextTurn(turn);
        }
    }

    public static int nextTurn(int turn){
        return turn++ < Monopoly.NUMBER_OF_PLAYERS ? turn++ : 0;
    }
}
