package table;

import game.Game;
import player.Player;

import java.util.Arrays;
import java.util.Random;

abstract public class Box {
    private Colors color;
    private final int money;
    private String name;

    final static int WIDTH = 24; //inizialmente 24
    final static int HEIGHT = 7; //inizialmente 5

    private static final int MIN_MONEY = 50;
    private static final int MAX_MONEY = 150;
    private Player[] playersInBox;
    private int cntPlayersInBox = 0;

    public Box(Colors color, int money, String name) {
        this.color = color;
        this.money = money;
        this.name = name;
        this.playersInBox = new Player[Game.NUMBER_OF_PLAYERS];

        //creo un'array di stringhe contenente i dettagli
        //di ogni box sotto forma di stringhe (le dimensioni sono impostate manualmente).
    }

    public Box(int money, String name) {
        this.money = money;
        this.name = name;
        this.playersInBox = new Player[Game.NUMBER_OF_PLAYERS];

        //crea un'array di stringhe contenente i dettagli
        //di ogni box sotto forma di stringhe (le dimensioni sono impostate manualmente).
    }

    public Box(Colors color, String name) {
        this(color, -generateMoneyValue(MIN_MONEY, MAX_MONEY), name);
    }

    static int generateMoneyValue(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    public void removePlayerFromTheBox(Player player) {
        for (int i = 0; i < playersInBox.length; i++) {
            if (playersInBox[i].equals(player)) {
                for (int j = i; j < playersInBox.length - 1; j++) {
                    playersInBox[j] = playersInBox[j + 1];
                }
                playersInBox[playersInBox.length - 1] = null;
                cntPlayersInBox--;
                return;
            }

        }
    }

    public void addPlayerToTheBox(Player player) {
        for (int i = 0; i < playersInBox.length; i++) {
            if (playersInBox[i] == null) {
                playersInBox[i] = player;
                cntPlayersInBox++;
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

    public String getName() {
        return name;
    }
    public String getColoredName() {
        return color + name + Colors.RESET;
    }

    public String getSymbolsOfPlayersInBox() {

        StringBuilder playersDetails = new StringBuilder();
        for (Player player : playersInBox) {
            if (player != null)
                playersDetails.append(player.getColoredSymbol()).append(" ");
        }
        String details = playersDetails.toString();
        return !details.isEmpty() ? details.substring(0, details.length() - 1) : "";
    }

    /**
     * Crea un array di stringhe con i dettagli del box da visualizzare, da sovrascrivere nelle classi figlie
     *
     * @return array di stringhe con i dettagli del box
     */
    public String[] getBoxDetails() {
        String[] details = new String[HEIGHT];
        Arrays.fill(details, "");
        details[0] = name;
        //details[HEIGHT - 1] = getSymbolsOfPlayersInBox();
        details[HEIGHT - 1] = getSymbolsOfPlayersInBox();
        return details;
    }

    public int getCntPlayersInTheBox() {
        return cntPlayersInBox;
    }

}