package gameLogic;

import player.Player;

public class Bank {
    private int bankMoney;

    public Bank(int bankMoney) {
        this.bankMoney = bankMoney;
    }

    public void addMoney(int amount, Player player) {
        if (amount >= 0) {
            this.bankMoney += amount;
            player.setBalance(player.getBalance() - amount);
        }
    }

    public void giveMoney(int amount, Player player) {
        if (amount >= 0 && amount <= this.bankMoney) {
            this.bankMoney -= amount;
            player.setBalance(player.getBalance() + amount);
        }
    }

    /**
     * Metodo che aggiorna il saldo del giocatore in base all'importo passato come parametro.
     * Se amount è negativo il giocatore perde soldi, altrimenti li guadagna.
     */
    public void updateBalance(int amount, Player player) {
        if (amount < 0)
            addMoney(Math.abs(amount), player);
        else
            giveMoney(amount, player);
    }
}
