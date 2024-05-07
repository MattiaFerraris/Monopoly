package table;

public class WealthTax extends Box {

    private static final double DEAFAULT_TAX = 0.1;

    private double tax;

    public WealthTax(double tax) {
        super(Colors.BLACK, 0, "Tassa patrimoniale");
        this.tax = tax;
    }

    public WealthTax() {
        this(DEAFAULT_TAX);
    }

    @Override
    public String[] getBoxDetails() {
        String[] details = super.getBoxDetails();
        details[1] = "Paga " + tax + " del partrim.";
        details[2] = super.getSymbolsOfPlayersInBox();
        return details;
    }

    public int getMoney(int money) {
        return  (int) (-(money * tax));
    }
}
