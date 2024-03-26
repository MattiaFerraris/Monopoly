package table;

public class LuxuryTax extends Box{
    public LuxuryTax(int tax) {
        super(Colors.BLACK, tax, "Tassa di lusso");
    }

    @Override
    public String toString() {
        return super.toString() + "Paga " + money  + ",".repeat(4) + "," + "\033[30m";
    }
}
