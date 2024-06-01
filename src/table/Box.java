package table;

import game.Game;
import player.Player;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

abstract public class Box implements Serializable {
    @Serial
    private static final long serialVersionUID = 7488274957749693850L;
    private Colors color;
    private final int money;
    private String name;

    final static int WIDTH = 24; //inizialmente 24
    final static int HEIGHT = 7; //inizialmente 5

    private static final int MIN_MONEY = 50;
    private static final int MAX_MONEY = 150;
    private LinkedList<Player> playersInBox;

    public Box(Colors color, int money, String name) {
        this.color = color;
        this.money = money;
        this.name = name;
        this.playersInBox = new LinkedList<>();

        //creo un'array di stringhe contenente i dettagli
        //di ogni box sotto forma di stringhe (le dimensioni sono impostate manualmente).
    }

    public Box(int money, String name) {
        this.money = money;
        this.name = name;
        this.playersInBox = new LinkedList<>();

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Box box = (Box) o;
        return Objects.equals(name, box.name);
    }

    public void removePlayerFromTheBox(Player player) {
        playersInBox.remove(player);
    }

    public void addPlayerToTheBox(Player player) {
        playersInBox.add(player);
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
                playersDetails.append(player.getSymbol()).append(" ");
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

    public String getBoxDetailsToString() {
        return name + "\n\n" + getSymbolsOfPlayersInBox();
    }

    public int getCntPlayersInTheBox() {
        return playersInBox.size();
    }


}