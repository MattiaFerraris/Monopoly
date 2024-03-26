package table;

public class Parking extends Box{
    public Parking() {
        super(Colors.BLACK,0, "Parcheggio");
    }
    @Override
    public String toString() {
        return super.toString() + "Paga " + ",".repeat(4) + "," + super.colorString();
    }
}
