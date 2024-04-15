package table;

public class LuxuryTax extends Box{
    public LuxuryTax(int tax) {
        super(Colors.BLACK, -tax, "Tassa di lusso");
    }

    @Override
    public String toString() {
        return super.toString() + "Paga " + Math.abs(super.getMoney()) + "," + super.getPlayersDetails() + "," + ",".repeat(3) + "," + super.getColor();
    }

    @Override
    public int getMoney(int money) {
        return super.getMoney();
    }
}
