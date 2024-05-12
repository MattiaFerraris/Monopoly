package table;


import player.Player;

public class Property extends Box{
    private Player owner;
    private int price;

    public Property(Colors color, String name) {
        super(color, name);
        price = Box.generateMoneyValue(150,500);
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
        details[2] = "Prezzo acquisto " + price;
        return details;
    }

    @Override
    public int getMoney(int money) {
        return super.getMoney();
    }
}
