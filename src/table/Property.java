package table;

public class Property extends Box{
    public Property(Colors color, String name) {
        super(color, name);
    }

    @Override
    public String toString() {
        return super.toString() + "Paga " + super.getMoney() + "," + super.getPlayersDetails() + "," + ",".repeat(2) + "," + super.getColor();
    }
}
