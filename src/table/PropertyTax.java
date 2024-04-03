package table;

public class PropertyTax extends Box {
    private double tax;
    public PropertyTax(double tax) {
        super(Colors.BLACK, 0, "Tassa patrimoniale");
        this.tax = tax;
    }
    @Override
    public String toString() {
        return super.toString() + "Paga " + tax + " del partrim." + "," + super.getPlayersDetails() + "," + ",".repeat(3) + "," + super.getColor();
     }

    public int getMoney(int money) {
        return (int) (money * tax);
    }
}
