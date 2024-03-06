package game;

import gameLogic.Bank;
import gameLogic.Dice;
import player.Player;
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
        Player players[] = new Player[Game.NUMBER_OF_PLAYERS];
        for (int i = 0; i < Game.NUMBER_OF_PLAYERS;) {
            String name = scannerUtilities.readString("Enter player name: ");
            String symbol = scannerUtilities.readString("Enter player symbol: ");
            Player tempPlayer = new Player(name, symbol);
            for(int j = 0; j < i; j++){
                if(tempPlayer.isEquals(players[j])){
                    System.out.println("Player already exists. Enter a different name or symbol.");
                    break;
                }
            }
            i++;
        }
        return players;
    }

    public void showTable() {
        table.showTable();
    }

    public void movePlayer(Player player) {
    }

    public void showBalance(Player player) {
        System.out.println("Player " + player.getName() + " has " + player.getBalance() + " money.");
    }

    public boolean isGameOver(Player players[]) {
        for(Player player : players){
            if(player.getBalance() <= 0){
                return true;
            }
        }
        return false;
    }
    
}
