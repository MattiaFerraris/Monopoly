package table;

public class Property extends Box{
    public Property(Colors color, String name) {
        super(color, name);
    }

    @Override
    public String toString() {
        return super.toString() + "Paga " + Math.abs(super.getMoney()) + "," + super.getPlayersDetails() + "," + ",".repeat(2) + "," + super.getColor();
    }

    @Override
    public int getMoney(int money) {
        return super.getMoney();
    }
}
