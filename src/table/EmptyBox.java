package table;

public class EmptyBox extends Box{
    public EmptyBox() {
        super(Colors.BLACK,0, "");
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
