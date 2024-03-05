package game;

import gameLogic.Bank;
import gameLogic.Dice;
import player.Player;
import table.Table;

public class Monopoly {
    public static final int NUMBER_OF_PLAYERS = 2;
    public static final int BANK_MONEY = 1000000;
    public static final int DICE_FACES = 4;
    private static final int WIDTH = 5;
    private static final int HEIGHT = 5;
    private int currentPlayer = 0;
    Table table;
    Bank bank;
    Dice dice;
    Player[] players;

    public Monopoly() {
        this.table = new Table(WIDTH, HEIGHT);
        this.bank = new Bank(BANK_MONEY);
        this.dice = new Dice(DICE_FACES);
        this.players = new Player[NUMBER_OF_PLAYERS];
        this.currentPlayer = 0;
    }

    public void addPlayers(Player player) {
        if (currentPlayer < NUMBER_OF_PLAYERS) {
            players[currentPlayer++] = player;
        } else {
            System.out.println("Max number of players reached");
        }
    }

    public void showTable() {
        table.showTable();
    }


/*    public void movePlayer(Player player) {
        int diceNumber = dice.roll();
        for (int i = 0; i < table.boxesNumber; i++) {
            for (int j = 0; j < NUMBER_OF_PLAYERS; j++) {

                if (table.boxes[i].playersInTheBox[j] == player) {
                    if (i + diceNumber > table.boxesNumber) {
                        player.setPosition(player.getPosition() - table.boxesNumber);
                    } else
                        player.setPosition(i + diceNumber);
                }

            }
        }

    }*/

    public void movePlayer(Player player){
        int diceNumber = dice.roll();
        int tmp = player.getPosition();
        table.boxes[tmp].removePlayerFromTheBox(player); //rimuove giocatore dal box

        if (tmp + diceNumber > table.boxesNumber) {
            player.setPosition(player.getPosition() - table.boxesNumber);
        } else
            player.setPosition(tmp + diceNumber);

        table.boxes[player.getPosition()].addPlayerToTheBox(player); //aggiunge giocatore al box

    }

    public void showBalance(Player player) {

    }
}
