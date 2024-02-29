package player;

import gameLogic.Dice;

public class Player {
    private String name;
    private String symbol;
    private Position position;
    private int balance;

    private void move(Dice dice){
        position.updatePosition(dice.roll());
    }

    public void show(){
        System.out.println("Nome: " + name + "Simbolo: " + symbol + "Posizione: " + position.getPosition() + "Bilancio: " + balance);
    }
}
