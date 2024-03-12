package gameLogic;

public class Bank {

    private int bankMoney;

    public Bank(int bankMoney) {
        this.bankMoney = bankMoney;
    }

    public void addMoney(int amount){
        if(amount >= 0)
            this.bankMoney += amount;
    }

    //CONTROLLARE SE GIUSTO RITORNARE 0 QUANDO NON SI PUO' PRELEVARE
    public int giveMoney(int amount){
        if(amount >= 0 && amount <= this.bankMoney){
            this.bankMoney -= amount;
            return amount;
        } else
            return 0;
    }
}
