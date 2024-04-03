package player;


import java.util.Objects;

public class Player {
    private static final int INITIAL_BALANCE = 2000;
    private String name;
    private String symbol;
    private int position;
    private int balance;

    public Player(String name, String symbol, int position) {
        setName(name);
        setSymbol(symbol);
        setPosition(position);
        this.balance = INITIAL_BALANCE;
    }

    //SETTER
    public void setName(String name) {
        if(!name.isBlank() && name.length() < 7)
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

    //GETTER
    public String getName() {
        return name;
    }
    public String getSymbol() {
        return symbol;
    }
    public int getPosition() {
        return position;
    }
    public int getBalance() {
        return balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name) && Objects.equals(symbol, player.symbol);
    }

    //VISUALIZZO GIOCATORE
    public String toString(Player player){
        return "Nome: " + name + ", Simbolo: " + symbol + ", Posizione: " + player.getPosition() + ", Soldi: " + balance;
    }
}
