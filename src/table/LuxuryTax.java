package table;

import java.io.Serial;

public class LuxuryTax extends Box{
    private static final int TAX = 200;
    @Serial
    private static final long serialVersionUID = -1956401495009554119L;

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

        return details;
    }

    @Override
    public int getMoney(int money) {
        return super.getMoney();
    }

}
