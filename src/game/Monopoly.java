package game;

import gameLogic.Bank;
import gameLogic.Dice;
import player.Player;
import table.Box;
import table.Table;
import utility.ScannerUtilities;

public class Monopoly {

    public static final int BANK_MONEY = 1000000;
    public static final int DICE_FACES = 6;
    public static final int WIDTH = 9;
    public static final int HEIGHT = 9;
    private Table table;
    private Bank bank;
    private Dice dice;

    public Monopoly() {
        this.table = new Table(WIDTH, HEIGHT);
        this.bank = new Bank(BANK_MONEY);
        this.dice = new Dice(DICE_FACES);
    }

    public void showTable() {
        table.showTable();
    }

    public void movePlayer(Player player) {
        int diceNumber = dice.roll();
        System.out.print("Numero uscito dal dado: " + diceNumber + "\n");
        int temPosition = player.getPosition();
        table.getBox(temPosition).removePlayerFromTheBox(player); //rimuove giocatore dal box

        if (temPosition + diceNumber > table.boxesNumber) {        //provare a unire questo if con else if sotto
            player.setPosition((temPosition + diceNumber) - table.boxesNumber);
        }
        else if(temPosition + diceNumber == table.boxesNumber){
            player.setPosition(0);
        }else
            player.setPosition(temPosition + diceNumber);

        table.getBox(player.getPosition()).addPlayerToTheBox(player); //aggiunge giocatore al box
        updateBalance(temPosition, player.getPosition(), table.getBox(player.getPosition()), player);
    }

    private void updateBalance(int oldPosition, int newPosition, Box newBox, Player player) {
        /*if(newBox.getIndex()==0){
            bank.giveMoney(100, player);
            return;
        }*/
        if (oldPosition > newPosition){
            bank.addMoney(newBox.getMoney(), player);
            bank.giveMoney(100 ,player);
        }
        else
            bank.addMoney(newBox.getMoney(), player);
    }


    public void showBalance(Player player) {
        System.out.println(player.getName() + " ha " + player.getBalance() + " soldi.");
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

    public void addPlayerToBox(Player player){
        table.getBox(0).addPlayerToTheBox(player);
    }
}
