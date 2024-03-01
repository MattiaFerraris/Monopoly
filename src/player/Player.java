package player;
import gameLogic.Dice;

import gameLogic.Dice;

public class Player {
    private String name;
    private String symbol;
    private Position position;
    private int balance = 2000;

    public Player(String name, String symbol, Position position) {
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
    public void setPosition(Position position) {
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
    public Position getPosition() {
        return position;
    }
    public int getBalance() {
        return balance;
    }

    //AGGIORNAMENTO POSIZIONE
    public void move(Dice dice){
        position.updatePosition(dice.roll());
    }

    //VISUALIZZO GIOCATORE
    public void show(){
        System.out.println("Nome: " + name + ", Simbolo: " + symbol + ", Posizione: " + position.getPositionNumber() + ", Bilancio: " + balance);
    }
}
