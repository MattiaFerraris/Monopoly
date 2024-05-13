package game;

import gameLogic.Bank;
import gameLogic.Dice;
import player.Player;
import table.*;

public class Monopoly {

    public static final int BANK_MONEY = 1000000;
    public static final int DICE_FACES = 6;
    public static final int WIDTH = 11;
    public static final int HEIGHT = 11;
    public static final int NPRISONTURNS = 3;
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

    private int diceRoll() {
        return dice1.roll() + dice2.roll();
    }

    public void showTable() {
        System.out.println(table);
    }

    public void movePlayer(Player player) {
        int dado1 = dice1.roll();
        int dado2 = dice2.roll();

        move(player, dado1, dado2);
    }

    public void move(Player player, int dado1, int dado2){
        //SE IN PRIGIONE
        if(player.isInPrison()){
            inPrison(player);
            return;
        }

        System.out.print("Numero uscito dal dado 1: " + dado1 + "\n" + "Numero uscito dal dado 2: " + dado2 + "\n" + "Somma dadi: " +  (dado1+dado2) + "\n");
        int temPosition = player.getPosition();
        table.getBox(temPosition).removePlayerFromTheBox(player); //rimuove giocatore dal box

        int newPosition = temPosition + (dado1+dado2);

        //VAI IN PRIGIONE
        if(newPosition == (table.getX()-1)*3){
            player.setPosition(table.getX()-1);
            table.getBox(player.getPosition()).addPlayerToTheBox(player);
            player.setnPrisonTurn(NPRISONTURNS);
            player.setInPrison(true);
            return;
        }

        player.setPosition(newPosition >= table.totalBoxesCount ? newPosition - table.totalBoxesCount : newPosition);
        table.getBox(player.getPosition()).addPlayerToTheBox(player); //aggiunge giocatore al box
        //updateBalance(temPosition, player.getPosition(), table.getBox(player.getPosition()), player);
    }

    public Box getBox(Player player){
        return table.getBox(player.getPosition());
    }

    public boolean buyProperty(Player player, Property property){
        if(player.getBalance() >= property.getPrice()){
            bank.updateBalance(-property.getPrice(), player);
            property.setOwner(player);
            System.out.println("Proprietà acquistata");
            return true;
        }
        else
            System.out.println("Non hai abbastanza soldi");
        return false;
    }

    public void payPropertyFee(Player player, Property property){
        Player owner = property.getOwner();
        if(owner != null){
            bank.transferMoney(property.getMoney(player.getBalance()), owner, player);
            System.out.println(player.getName() + " ha pagato " + Math.abs(property.getMoney(player.getBalance())) + " a " + owner.getName() + " per " + property.getName());
        }
        bank.updateBalance(property.getMoney(player.getBalance()), player);
    }

    private void inPrison(Player player){
        int dado1 = dice1.roll();
        int dado2 = dice2.roll();

        System.out.println("Turni rimanenti in prigione: " + player.getnPrisonTurn() + "\n");

        if(player.getnPrisonTurn() == 0){
            System.out.println("Pagati 50 CHF per uscire di prigione");
            bank.updateBalance(-50, player);
            player.setInPrison(false);
            move(player, dado1, dado2);
        } else if(dado1 == dado2){
            player.setInPrison(false);
            move(player, dado1, dado2);
        }
        else{
            System.out.print("Numero uscito dal dado 1: " + dado1 + "\n" + "Numero uscito dal dado 2: " + dado2 + "\n" + "Somma dadi: " +  (dado1+dado2) + "\n");
            System.out.println("NO DADO DOPPIO");
            player.setnPrisonTurn(player.getnPrisonTurn()-1);
        }
    }



    public void updateBalance(int oldPosition, int newPosition, Player player) {
        Box newBox = table.getBox(newPosition);
        if (newPosition == 0)
            bank.updateBalance(100, player);
        else if (oldPosition > newPosition) {
            bank.updateBalance(newBox.getMoney(player.getBalance()), player);
            bank.updateBalance(100, player);
        } else
            bank.updateBalance(newBox.getMoney(player.getBalance()), player);

    }

    public boolean hasPlayerAllSameColorProperties(Player player, Property property){
        int count = 0;
        Colors color = property.getColor();
        if(color == Colors.BLACK)
            return false;

        for (int i = 0; i < table.totalBoxesCount; i++) {
            if(table.getBox(i).getColor() == color){
                if(((Property)table.getBox(i)).getOwner() == player)
                    count++;
            }
        }

        return count == table.getPropertyCount(color);
    }

    public void buildHouse(Player player, BuildableProperty property){
        property.addHouse(player);
        bank.updateBalance(-property.getPriceHouse(), player);
    }

    public void buildHotel(Player player, BuildableProperty property){
        property.addHotel(player);
        bank.updateBalance(-property.getPriceHotel(), player);
    }

    public void showBalance(Player player) {
        System.out.println(player.getName() + " ha " + player.getBalance() + " soldi.");
    }

    public boolean isGameOver(Player[] players) {
        int cntPlayers = players.length;

        for (int i = 0; i < players.length; i++) {

            if (players[i].getBalance() <= 0) {
                cntPlayers -= 1;
                System.out.println("Giocatore " + players[i].getName() + " ha perso");
                for(Box box : table.getBoxes())
                    if(box instanceof Property property)
                        if(property.getOwner() == players[i])
                            property.setOwner(null);
                players[i] = null;

                if (cntPlayers == 1) {
                    System.out.println("Il player: " + players[i == 0 ? 1 : 0].getName() + " ha vinto!!");
                    return true;
                }
            }
        }
        return false;
    }



    public void addPlayerToBox(Player player) {
        table.getBox(0).addPlayerToTheBox(player);
    }
}
