package table;

public class GoToPrison extends Box{

    public GoToPrison() {
        super(Colors.BLACK, 0, "Vai in prigione");
    }

    public String toString() {
        return super.toString() + "," + super.getPlayersDetails() + "," + ",".repeat(3) + "," + super.getColor();
    }

    @Override
    public int getMoney(int money) {
        return 0;
    }
}
