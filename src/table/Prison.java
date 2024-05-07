package table;

public class Prison extends Box {

    public Prison() {
        super(Colors.BLACK, 0, "Prigione");
    }

    public String toString() {
        return super.toString() + "," + super.getPlayersDetails() + "," + ",".repeat(3) + "," + super.getColor();
    }

    @Override
    public int getMoney(int money) {
        return 0;
    }
}
