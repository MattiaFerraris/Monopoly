package player;

public class Player {
    private String name;
    private String symbol;
    private int position;
    private int balance = 2000;

    public Player(String name, String symbol, int position) {
        setName(name);
        setSymbol(symbol);
        setPosition(position);
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

    public boolean isEquals(Player player2){
        return this.name.equalsIgnoreCase(player2.getName()) || this.symbol.equalsIgnoreCase(player2.getSymbol());
    }

    //VISUALIZZO GIOCATORE
    public void show(Player player){
        System.out.println("Nome: " + name + ", Simbolo: " + symbol + ", Posizione: " + player.getPosition() + ", Bilancio: " + balance);
    }
}
