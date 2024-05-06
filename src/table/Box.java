package table;

import game.Game;
import player.Player;
import java.util.Random;

abstract public class Box {
    private Colors color;
    private final int money;
    private String name;
    final static int BOX_WIDTH = 24;
    final static int HEIGHT = 5;

    private static final int MIN_MONEY = 50;
    private static final int MAX_MONEY = 150;
    private Player[] playersInBox;

    public Box(Colors color, int money, String name) {
        this.color = color;
        this.money = money;
        this.name = name;
        this.playersInBox = new Player[Game.NUMBER_OF_PLAYERS];
    }

    public Box(Colors color, String name){
        this(color, -generateMoneyValue(MIN_MONEY, MAX_MONEY) ,name);
    }

    private static int generateMoneyValue(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    public void removePlayerFromTheBox(Player player) {
        for (int i = 0; i < playersInBox.length; i++) {
            if(playersInBox[i].equals(player)) {
                for (int j = i; j < playersInBox.length-1; j++) {
                    playersInBox[j] = playersInBox[j + 1];
                }
                playersInBox[playersInBox.length-1] = null;
                return;
            }
        }
    }

    public void addPlayerToTheBox(Player player) {
        for (int i = 0; i < playersInBox.length; i++) {
            if(playersInBox[i] == null) {
                playersInBox[i] = player;
                return;
            }
        }
    }
    public int getMoney() {
        return money;
    }

    public abstract int getMoney(int money);

    public Colors getColor() {
        return color;
    }

    public String getSymbolsOfPlayersInBox() {
        StringBuilder playersDetails = new StringBuilder();
        for (Player player : playersInBox) {
            if (player != null)
                playersDetails.append(player.getSymbol()).append(" ");
        }
        String details = playersDetails.toString();
        return !details.isEmpty() ? details.substring(0, details.length() - 1) : "";
    }

    /**
     * Crea un array di stringhe con i dettagli del box da visualizzare, da sovrascrivere nelle classi figlie
     * @return array di stringhe con i dettagli del box
     */
    public String[] getBoxDetails(){
        String[] details = new String[HEIGHT];
        details[0] = name;
        for (int i = 1; i < details.length; i++) {
            details[i] = "";
        }
        return details;
    }
}
