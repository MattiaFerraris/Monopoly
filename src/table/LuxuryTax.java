package table;

public class LuxuryTax extends Box{
    private static final int TAX = 200;
    public LuxuryTax(int tax) {
        super(Colors.BLACK, -tax, "Tassa di lusso");
    }

    public LuxuryTax() {
        this(TAX);
    }

    @Override
    public String[] getBoxDetails() {
        String[] details = super.getBoxDetails();
        details[1] = "Paga " + Math.abs(super.getMoney());
        details[2] = super.getSymbolsOfPlayersInBox();
        return details;
    }

    @Override
    public int getMoney(int money) {
        return super.getMoney();
    }

}
