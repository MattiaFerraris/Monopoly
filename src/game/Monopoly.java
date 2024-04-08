package game;

import gameLogic.Bank;
import gameLogic.Dice;
import player.Player;
import table.Box;
import table.Table;
import utility.ScannerUtilities;

import java.util.Arrays;

public class Monopoly {

    public static final int BANK_MONEY = 1000000;
    public static final int DICE_FACES = 6;
    public static final int WIDTH = 9;
    public static final int HEIGHT = 9;
    private Table table;
    private Bank bank;
    private Dice dice1;
    private Dice dice2;

    public Monopoly() {
        this.table = new Table(WIDTH, HEIGHT);
        this.bank = new Bank(BANK_MONEY);
        this.dice1 = new Dice(DICE_FACES);
        this.dice2 = new Dice(DICE_FACES);
    }

    private int diceRoll(){
        return dice1.roll() + dice2.roll();
    }

    public void showTable() {
        System.out.println(table);
    }

    public void movePlayer(Player player) {
        int diceNumber = diceRoll();
        System.out.print("Numero uscito dal dado: " + diceNumber + "\n");
        int temPosition = player.getPosition();
        table.getBox(temPosition).removePlayerFromTheBox(player); //rimuove giocatore dal box

        int newPosition = temPosition + diceNumber;
        player.setPosition(newPosition >= table.boxesNumber ? newPosition - table.boxesNumber : newPosition);
        table.getBox(player.getPosition()).addPlayerToTheBox(player); //aggiunge giocatore al box
        updateBalance(temPosition, player.getPosition(), table.getBox(player.getPosition()), player);
    }

    private void updateBalance(int oldPosition, int newPosition, Box newBox, Player player) {
        if (newPosition == 0)
            bank.updateBalance(100, player);
        else if (oldPosition > newPosition){
            bank.updateBalance(newBox.getMoney(), player);
            bank.updateBalance(100 ,player);
        }
        else
            bank.updateBalance(newBox.getMoney(), player);

    }

    public void showBalance(Player player) {
        System.out.println(player.getName() + " ha " + player.getBalance() + " soldi.");
    }

    public boolean isGameOver(Player[] players) {

        int cntPlayers= players.length;

        for (int i=0;i< players.length;i++) {

            if (players[i].getBalance() <= 0) {
                cntPlayers-=1;
                players[i] = null;

                if(cntPlayers == 1) {
                    System.out.println("Il player: " + players[0] + " ha vinto!!");
                    return true;
                }
            }
        }
        return false;
    }

    public void addPlayerToBox(Player player){
        table.getBox(0).addPlayerToTheBox(player);
    }
}
