package table;

public class Start extends Box{
    public Start() {
        super(Colors.BLACK,100, "Start");
    }
    @Override
    public String toString() {
        return super.toString() + "Ritira " + super.getMoney() + ",".repeat(3) + "," + super.getColor();
    }
}
