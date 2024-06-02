package game;

import gameLogic.Bank;
import gameLogic.Dice;
import player.Player;
import player.PlayerDiceComparator;
import table.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class Monopoly implements Serializable {
    public static final int NUMBER_OF_PLAYERS = 4;
    public static final int BANK_MONEY = 1000000;
    public static final int DICE_FACES = 6;
    public static final int WIDTH = 11;
    public static final int HEIGHT = 11;
    public static final int MAX_PRISION_TURNS = 3;
    @Serial
    private static final long serialVersionUID = 5796923655661778963L;
    private Table table;
    private Bank bank;
    private transient Dice dice1 = new Dice(DICE_FACES);
    private transient Dice dice2 = new Dice(DICE_FACES);
    private ArrayList<Player> players;
    private Player currentPlayer;

    public Monopoly(ArrayList<Player> players) {
        this.table = new Table(WIDTH, HEIGHT);
        this.bank = new Bank(BANK_MONEY);
        this.players = shufflePlayerOrder(players);
        for (Player player : this.players)
            addPlayerToStart(player);
        this.currentPlayer = this.players.get(0);
    }

    private ArrayList<Player> shufflePlayerOrder(ArrayList<Player> players) {
        Map<Player, Integer> playerDiceMap = new HashMap<>();
        TableController.showAlert("SFIDA INIZIALE DEI DADI");
        for (Player player : players) {
            int dice1 = this.dice1.roll();
            int dice2 = this.dice2.roll();

            TableController.showAlert(null, dice1 + " e " + dice2 + " = " + (dice1 + dice2), player);
            playerDiceMap.put(player, dice1 + dice2);
        }
        ArrayList<Map.Entry<Player, Integer>> shuffledPlayers = new ArrayList(playerDiceMap.entrySet());
        shuffledPlayers.sort(new PlayerDiceComparator());
        ArrayList<Player> orderedPlayers = new ArrayList<>();
        for (int i = 0; i < shuffledPlayers.size(); i++)
            orderedPlayers.add(((Map.Entry<Player, Integer>) shuffledPlayers.get(i)).getKey());
        StringBuilder orderedPlayersString = new StringBuilder();
        for (Player player : orderedPlayers)
            orderedPlayersString.append(player.getName()).append("\n");
        TableController.showAlert("Ordine di gioco:\n" + orderedPlayersString);
        return orderedPlayers;
    }

    public void showTable() {
        System.out.println(table);
    }

    public int[] movePlayer(Player player) {
        int dado1 = dice1.roll();
        int dado2 = dice2.roll();
        move(player, dado1, dado2);
        return new int[]{dado1, dado2};
    }

    public void move(Player player, int position) {
        int temPosition = player.getPosition();
        table.getBox(temPosition).removePlayerFromTheBox(player); //rimuove giocatore dal box

        int newPosition = temPosition + (position);

        if (newPosition >= table.totalBoxesCount)
            bank.updateBalance(100, player);

        //VAI IN PRIGIONE
        if (newPosition == (table.getX() - 1) * 3) {
            player.setPosition(table.getX() - 1);
            table.getBox(player.getPosition()).addPlayerToTheBox(player);
            player.setnPrisonTurn(MAX_PRISION_TURNS);
            player.setInPrison(true);
            return;
        }

        player.setPosition(newPosition >= table.totalBoxesCount ? newPosition - table.totalBoxesCount : newPosition);
        table.getBox(player.getPosition()).addPlayerToTheBox(player); //aggiunge giocatore al box
    }

    public void move(Player player, int dado1, int dado2) {
        //SE IN PRIGIONE
        if (player.isInPrison()) {
            inPrison(player);
            return;
        }
        move(player, dado1 + dado2);
    }

    public Box getBox(Player player) {
        return table.getBox(player.getPosition());
    }

    public boolean buyProperty(Player player, Property property) {
        if (player.getBalance() >= property.getPrice()) {
            bank.updateBalance(-property.getPrice(), player);
            property.setOwner(player);
            return true;
        }
        return false;
    }

    public void payPropertyFee(Player player, Property property) {
        Player owner = property.getOwner();
        if (owner != null) {
            bank.transferMoney(property.getMoney(player.getBalance()), player, owner);
            TableController.showAlert(player.getName() + " ha pagato " + Math.abs(property.getMoney(player.getBalance())) + " a " + owner.getName() + " per " + property.getName());
            return;
        }
        bank.updateBalance(property.getMoney(player.getBalance()), player);
    }

    public void useProbabilityCard(Player player, ProbabilityCard probabilityCard) {
        TableController.showAlert("PROBABILITÀ!", probabilityCard.getPrint(), currentPlayer);
        if (probabilityCard.getType() == ProbabilityChanceType.PAY)
            bank.updateBalance(-probabilityCard.getAmmount(), player);
        else if (probabilityCard.getType() == ProbabilityChanceType.RECEIVE)
            bank.updateBalance(probabilityCard.getAmmount(), player);
        else {
            int boxPosition = table.getBoxPosition(probabilityCard.getPlace());
            movePlayer(player.getName(), boxPosition);
        }
    }

    public void useChanceCard(Player player, ChanceCard chanceCard) {
        TableController.showAlert("IMPREVISTI!", chanceCard.getPrint(), currentPlayer);
        if (chanceCard.getType() == ProbabilityChanceType.PAY)
            bank.updateBalance(-chanceCard.getAmmount(), player);
        else if (chanceCard.getType() == ProbabilityChanceType.RECEIVE)
            bank.updateBalance(chanceCard.getAmmount(), player);
        else {
            int boxPosition = table.getBoxPosition(chanceCard.getPlace());
            movePlayer(player.getName(), boxPosition);
        }
    }

    private void inPrison(Player player) {
        int dado1 = dice1.roll();
        int dado2 = dice2.roll();

        TableController.showAlert("A " + player.getName() + " mancano " + player.getnPrisonTurn() + " turni per uscire di prigione");

        if (player.getnPrisonTurn() == 0) {
            TableController.showAlert("USCITA DI PRIGIONE", "Dado 1: " + dado1 + "\n" + "Dado 2: \n" + " esci di prigione pagando 50 CHF!", player);
            bank.updateBalance(-50, player);
            player.setInPrison(false);
            move(player, dado1, dado2);
        } else if (dado1 == dado2) {
            player.setInPrison(false);
            move(player, dado1, dado2);
        } else {
            TableController.showAlert("Tiro dei dadi in prigione", "Dado 1: " + dado1 + "\n" + "Dado 2: " + dado2 + "\nNon esci di prigione!", player);
            player.setnPrisonTurn(player.getnPrisonTurn() - 1);
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

    public boolean hasPlayerAllSameColorProperties(Player player, Property property) {
        int count = 0;
        Colors color = property.getColor();
        if (color == Colors.BLACK)
            return false;

        for (int i = 0; i < table.totalBoxesCount; i++) {
            if (table.getBox(i) instanceof Property && table.getBox(i).getColor() == color) {
                if (((Property) table.getBox(i)).getOwner() == player)
                    count++;
            }
        }

        return count == table.getPropertyCount(color);
    }

    public void buildHouse(Player player, BuildableProperty property) {
        property.addHouse(player);
        bank.updateBalance(-property.getPriceHouse(), player);
    }

    public void buildHotel(Player player, BuildableProperty property) {
        property.addHotel(player);
        bank.updateBalance(-property.getPriceHotel(), player);
    }

    public void showBalance(Player player) {
        System.out.println(player.getName() + " ha " + player.getBalance() + " soldi.");
    }

    public boolean isGameOver() {
        return players.size() == 1;
    }

    public ArrayList<Player> getLostPlayers() {
        ArrayList<Player> lostPlayers = new ArrayList<>();
        for (Iterator<Player> iterator = players.iterator(); iterator.hasNext(); ) {
            Player player = iterator.next();
            if (player.getBalance() <= 0) {
                //TableController.showAlert(player.getName() + " HA PERSO!");
                lostPlayers.add(player);
                iterator.remove();
                for (int i = 0; i < table.totalBoxesCount; i++) {
                    if (table.getBox(i) instanceof Property property) {
                        if (property.getOwner() == player)
                            property.reset();
                    }
                }
            }
        }
        return lostPlayers;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void nextTurn() {
        int index = players.indexOf(currentPlayer);
        currentPlayer = players.get((index + 1) % players.size());
    }

    private void addPlayerToStart(Player player) {
        table.getBox(0).addPlayerToTheBox(player);
    }

    public static Monopoly loadState(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        Monopoly monopoly = (Monopoly) objectInputStream.readObject();
        monopoly.dice1 = new Dice(DICE_FACES);
        monopoly.dice2 = new Dice(DICE_FACES);
        return monopoly;
    }

    public int getBankBalance() {
        return bank.getBankMoney();
    }

    public Player[] getPlayers() {
        return players.toArray(new Player[players.size()]);
    }

    public boolean buyPropertyFromPlayer(Player buyer, Player seller, Property property) {
        if (buyer.getBalance() >= property.getPrice()) {
            bank.transferMoney(property.getPrice(), buyer, seller);
            property.setOwner(buyer);
            property.reset();
            return true;
        }
        return false;
    }

    /* DEBUG METHODS */

    public Player getPlayer(String playerName) {
        for (Player p : players) {
            if (p.getName().equals(playerName))
                return p;
        }
        return null;
    }

    public void givePlayerProperty(String playerName, String propertyName) {
        Player player = getPlayer(playerName);
        if (player == null) {
            System.out.println("Giocatore non trovato");
            return;
        }
        for (int i = 0; i < table.totalBoxesCount; i++) {
            if (table.getBox(i) instanceof Property property) {
                if (property.getName().equals(propertyName)) {
                    property.setOwner(player);
                    System.out.println(propertyName + " assegnata a " + playerName);
                    return;
                }
            }
        }
        System.out.println(propertyName + " non trovata");
    }

    public Table getTable() {
        return table;
    }

    public void movePlayer(String playerName, int position) {
        Player player = getPlayer(playerName);
        if (player == null) {
            System.out.println("Giocatore non trovato");
            return;
        }
        table.getBox(player.getPosition()).removePlayerFromTheBox(player);
        player.setPosition(position);
        table.getBox(player.getPosition()).addPlayerToTheBox(player);

        if (position == (table.getX() - 1)) {
            player.setnPrisonTurn(MAX_PRISION_TURNS);
            player.setInPrison(true);
        }
    }

    public void setPlayerBalance(String playerName, int balance) {
        Player player = getPlayer(playerName);
        if (player == null) {
            System.out.println("Giocatore non trovato");
            return;
        }
        player.setBalance(balance);
    }

    public void addHouse(String playerName, String propertyName) {
        for (int i = 0; i < table.totalBoxesCount; i++) {
            if (table.getBox(i) instanceof BuildableProperty property) {
                if (property.getName().equals(propertyName)) {
                    property.addHouse(getPlayer(playerName));
                    TableController.showAlert("Casa aggiunta a " + propertyName);
                    //System.out.println("Casa aggiunta a " + propertyName);
                    return;
                }
            }
        }
        System.out.println("Proprietà non trovata");
    }
}