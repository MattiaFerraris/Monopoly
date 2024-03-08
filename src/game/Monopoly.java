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
        String name = scannerUtilities.readString("Enter player name: ");
        String symbol = scannerUtilities.readString("Enter player symbol: ");
        players[0] = new Player(name, symbol, 0); //crea un giocatore con posizione 0 di default
        table.boxes[0].addPlayerToTheBox(players[0]);
        for (int i = 1; i < Game.NUMBER_OF_PLAYERS; ) {
            boolean isEquals = false;
            name = scannerUtilities.readString("Enter player name: ");
            symbol = scannerUtilities.readString("Enter player symbol: ");
            Player player = new Player(name, symbol, 0);
            for (int j = 0; j < i; j++) {
                if (player.isEquals(players[j])) {
                    System.out.println("Player already exists. Enter a different name or symbol.");
                    isEquals = true;
                    break;
                }
            }
            if (!isEquals) {
                players[i] = player;
                table.boxes[0].addPlayerToTheBox(players[i]);
                i++;
            }
        }
        return players;
    }

    public void showTable() {
        table.showTable();
    }

    public void movePlayer(Player player) {
        int diceNumber = dice.roll();
        System.out.print("DICE SAID: " + diceNumber + "\n");
        int temPosition = player.getPosition();
        table.boxes[temPosition].removePlayerFromTheBox(player); //rimuove giocatore dal box

        if (temPosition + diceNumber > table.boxesNumber) {        //provare a unire questo if con else if sotto
            player.setPosition((temPosition + diceNumber) - table.boxesNumber);
        }
        else if(temPosition + diceNumber == table.boxesNumber){
            player.setPosition(0);
        }else
            player.setPosition(temPosition + diceNumber);

        table.boxes[player.getPosition()].addPlayerToTheBox(player); //aggiunge giocatore al box
        updateBalance(temPosition, player.getPosition(), table.boxes[player.getPosition()], player);
    }

    private void updateBalance(int oldPosition, int newPosition, Box newBox, Player player) {
        if (oldPosition > newPosition)
            player.setBalance(player.getBalance() + newBox.getMoney());
        else
            player.setBalance(player.getBalance() - newBox.getMoney());
        if (isGameOver()) {

        }


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

    public boolean isGameOver() {
        return true;
    }

}
