package table;

import game.Game;
import player.Player;

import java.util.Arrays;
import java.util.Random;


public class Box {
    private Colors color; //tolto il private da Colors così posos fare costruttore senza per caselle speciali
    private final int money;
    private String name;
    final static int boxWidth = 24;
    final static int height = 5;

    private static final int minMoney = 50;
    private static final int maxMoney = 150;
    protected Player[] playersInTheBox;

    public Box(Colors color, int money, String name) {
        this.color = color;
        this.money = money;
        this.name = name;
        this.playersInTheBox = new Player[Game.NUMBER_OF_PLAYERS];

        //creo un'array di stringhe contenente i dettagli
        //di ogni box sotto forma di stringhe (le dimensioni sono impostate manualmente).
    }
    public Box(int money, String name) {
        this.money = money;
        this.name = name;
        this.playersInTheBox = new Player[Game.NUMBER_OF_PLAYERS];

        //creo un'array di stringhe contenente i dettagli
        //di ogni box sotto forma di stringhe (le dimensioni sono impostate manualmente).
    }

    public Box(Colors color, String name){
        this(color, generateMoneyValue(minMoney, maxMoney) ,name);
    }

    static int generateMoneyValue(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    public void removePlayerFromTheBox(Player player) {
        for (int i = 0; i < playersInTheBox.length; i++) {
            if(playersInTheBox[i] == player) {
                playersInTheBox[i] = null;
                break;
            }
        }
    }

    public void addPlayerToTheBox(Player player) {
        for (int i = 0; i < playersInTheBox.length; i++) {
            if(playersInTheBox[i] == null) {
                playersInTheBox[i] = player;
                break;
            }
        }
    }

    public static int getMinMoney(){
        return minMoney;
    }
    public static int getMaxMoney(){
        return maxMoney;
    }

    public int getMoney() {
        return money;
    }

    public Colors getColor() {
        return color;
    }

    public String getPlayersDetails() {
        StringBuilder playersDetails = new StringBuilder();
        for (Player player : playersInTheBox) {
            if (player != null)
                playersDetails.append(player.getSymbol()).append(" ");
        }
        String details = playersDetails.toString();
        return !details.isEmpty() ? details.substring(0, details.length() - 1) : "";
    }

    @Override
    public String toString() {
        return name + ",";
    }
}
