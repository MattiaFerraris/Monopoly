package player;

import table.Colors;

import java.util.Objects;

public class Player {
    private static final int INITIAL_BALANCE = 2000;
    private String name;
    private String symbol;
    private int position;
    private int balance;
    private boolean isInPrison;
    private int nPrisonTurn;
    private Colors color;

    private String coloredName;
    private String coloredSymbol;
    private static Colors[] colors = {Colors.RED, Colors.BLUE, Colors.GREEN, Colors.YELLOW};
    private static int colorsCnt;

    public Player(String name, String symbol, int position) {
        setName(name);
        setSymbol(symbol);
        setPosition(position);
        this.balance = INITIAL_BALANCE;
        isInPrison = false;
        nPrisonTurn = 0;

        setColoredName(name);
        setColoredSymbol(symbol);
        colorsCnt++;
    }

    //SETTER
    public void setName(String name) {
        if (name.length() > 15)
            this.name = name.substring(0, 15);
        else
            this.name = name;
    }

    public void setSymbol(String symbol) {
        if (symbol.length() == 1)
            this.symbol = symbol;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setInPrison(boolean inPrison) {
        isInPrison = inPrison;
    }

    public void setnPrisonTurn(int nPrisonTurn) {
        this.nPrisonTurn = nPrisonTurn;
    }


    public void setColoredName(String name) {
        if(colorsCnt == colors.length)
            colorsCnt = 0;
        coloredName = "\033[03m" + colors[colorsCnt] + name + "\033[00m";
    }

    public void setColoredSymbol(String symbol) {
        if(colorsCnt == colors.length)
            colorsCnt = 0;
        coloredSymbol = "\033[03m" + colors[colorsCnt] + symbol + "\033[00m"; //stringa con il simbolo è lunga 11 (5+1+5)
    }

    //GETTER
    public String getName() {
        return name;
    }

    public String getColoredName() {
        return coloredName;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getColoredSymbol() {
        return coloredSymbol;
    }


    public int getPosition() {
        return position;
    }

    public int getBalance() {
        return balance;
    }

    public boolean isInPrison() {
        return isInPrison;
    }

    public int getnPrisonTurn() {
        return nPrisonTurn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name) || Objects.equals(symbol, player.symbol);
    }

    public void setColor(Colors color) {
        this.color = color;
    }


}