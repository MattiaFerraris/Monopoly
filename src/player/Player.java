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

    public Player(String name, String symbol, int position) {
        setName(name);
        setSymbol(symbol);
        setPosition(position);
        this.balance = INITIAL_BALANCE;
        isInPrison = false;
        nPrisonTurn = 0;
    }

    //SETTER
    public void setName(String name) {
        if(name.length() > 15)
            this.name = name.substring(0, 15);
        else
            this.name = name;
    }

    public void setSymbol(String symbol) {
        if(symbol.length() == 1)
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

    //GETTER
    public String getName() {
        return name;
    }

    public String getColoredName(){
        return color + name + "\033[00m";
    }
    public String getSymbol() {
        return symbol;
    }
    public String getColoredSymbol() {
        return color + symbol + "\033[00m"; //a questo punto la stringa con il simbolo è lunga 11
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