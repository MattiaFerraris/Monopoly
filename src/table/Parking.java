package table;

public class Parking extends Box{
    public Parking() {
        super(Colors.BLACK,0, "Parcheggio");
    }
    @Override
    public String toString() {
        return super.toString() + "," + super.getPlayersDetails() + "," + ",".repeat(3) + "," + super.getColor();
    }

    @Override
    public int getMoney(int money) {
        return super.getMoney();
    }
}
