package game;

import gameLogic.Bank;
import gameLogic.Dice;
import player.Player;
import table.Box;
import table.Table;
import utility.ScannerUtilities;

public class Monopoly {

    public static final int BANK_MONEY = 1000000;
    public static final int DICE_FACES = 4;
    public static final int WIDTH = 5;
    public static final int HEIGHT = 5;
    public static final int START_POSITION = 0;
    Table table;
    Bank bank;
    Dice dice;

    public Monopoly() {
        this.table = new Table(WIDTH, HEIGHT);
        this.bank = new Bank(BANK_MONEY);
        this.dice = new Dice(DICE_FACES);
    }

    public Player[] generatePlayers(ScannerUtilities scannerUtilities) {
        Player[] players = new Player[Game.NUMBER_OF_PLAYERS];

        for (int i = 0; i < Game.NUMBER_OF_PLAYERS;) {
            System.out.println("--------");

            String name = scannerUtilities.readString("Enter player name: ");
            String symbol = scannerUtilities.readString("Enter player symbol: ");
            players[i] = new Player(name, symbol, START_POSITION); //crea un giocatore con posizione 0 di default

            for (int j = 0; j < i; j++) {
                if (players[i].isEquals(players[j])) {
                    System.out.println("Player already exists. Enter a different name or symbol.");
                    i--;
                    break;
                }
            }

            table.boxes[START_POSITION].addPlayerToTheBox(players[i]);
            i++;
        }
        return players;
    }

    public void showTable() {
        table.showTable();
    }

    public void movePlayer(Player player) {        //DA SISTEMARE
        int diceNumber = dice.roll();
        System.out.print("DICE SAID: " + diceNumber + "\n");
        int temPosition = player.getPosition();
        table.boxes[temPosition].removePlayerFromTheBox(player); //rimuove giocatore dal box

        if (temPosition + diceNumber >= table.boxesNumber) {
            player.setPosition(table.boxesNumber - player.getPosition());
        } else
            player.setPosition(temPosition + diceNumber);

        table.boxes[player.getPosition()].addPlayerToTheBox(player); //aggiunge giocatore al box
        updateBalance(temPosition, player.getPosition(), table.boxes[player.getPosition()], player);
    }

    private void updateBalance(int oldPosition, int newPosition, Box newBox, Player player) {
        if (oldPosition > newPosition)
            player.setBalance(player.getBalance()+newBox.getMoney());
        else
            player.setBalance(player.getBalance()-newBox.getMoney());
    }

    public void showBalance(Player player) {
        System.out.println("Player " + player.getName() + " has " + player.getBalance() + " money.");
    }

    public boolean isGameOver(Player[] players) {
        for (Player player : players) {
            if (player.getBalance() <= 0) {
                System.out.println(player.getName().toUpperCase() + " HA PERSO!");
                return true;
            }
        }
        return false;
    }

}
