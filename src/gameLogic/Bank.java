package gameLogic;

public class Bank {

    private int bankMoney;

    public Bank(int bankMoney) {
        this.bankMoney = 1000000;
    }

    public void addMoney(int amount){
        this.bankMoney += amount;
    }

    public int giveMoney(int amount){
        this.bankMoney -= amount;
        return amount;
    }
}
