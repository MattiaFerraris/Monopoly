package gameLogic;

import player.Player;

public class Bank {

    private int bankMoney;

    public Bank(int bankMoney) {
        this.bankMoney = bankMoney;
    }

    public void addMoney(int amount, Player player){
        if(amount >= 0){
            this.bankMoney += amount;
            player.setBalance(player.getBalance()-amount);
        }
    }

    public void giveMoney(int amount, Player player){
        if(amount >= 0 && amount <= this.bankMoney){
            this.bankMoney -= amount;
            player.setBalance(player.getBalance()+amount);
        }
    }
}
