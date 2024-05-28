package gameLogic;

import player.Player;

import java.io.Serial;
import java.io.Serializable;

public class Bank implements Serializable {
    @Serial
    private static final long serialVersionUID = -78467081455101893L;
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
     *
     * @param amount Se è negativo il giocatore perde soldi, altrimenti li guadagna.
     */
    public void updateBalance(int amount, Player player) {
        if (amount < 0)
            addMoney(Math.abs(amount), player);
        else
            giveMoney(amount, player);
    }

    /**
     * Metodo che aggiorna il saldo dei giocatori in base all'importo passato come parametro.
     * @param amount Quantità di denaro che Player1 paga a Player2.
     * @param player1 Giocatore che paga.
     * @param player2 Giocatore che riceve.
     */
    public boolean transferMoney(int amount, Player player1, Player player2) {
        amount = Math.abs(amount);
        if(player1.getBalance() < amount)
            return false;
        player1.setBalance(player1.getBalance() - amount);
        player2.setBalance(player2.getBalance() + amount);
        return true;
    }
}
