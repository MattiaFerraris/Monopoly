package table;

public class Start extends Box{
    public Start() {
        super(Colors.BLACK,100, "Start");
    }
    @Override
    public String toString() {
        return super.toString() + "Ritira " + Math.abs(super.getMoney()) + "," + super.getPlayersDetails() + "," + ",".repeat(2) + "," + super.getColor();
    }

    @Override
    public int getMoney(int money) {
        return super.getMoney();
    }
}
