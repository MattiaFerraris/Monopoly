package table;


import player.Player;

import java.io.Serial;

public class Property extends Box{
    @Serial
    private static final long serialVersionUID = 41741538644209896L;
    private Player owner;
    private int price;

    public Property(Colors color, String name) {
        super(color, name);
        price = Box.generateMoneyValue(150,500);
    }

    public void reset(){
        owner = null;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String[] getBoxDetails() {
        String[] details = super.getBoxDetails();
        details[1] = "Paga " + Math.abs(super.getMoney());
        details[2] = owner!=null?owner.getName():("Prezzo acquisto " + price);
        return details;
    }

    @Override
    public int getMoney(int money) {
        return super.getMoney();
    }
}
