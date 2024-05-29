package table;

import java.io.Serial;

public class Start extends Box{
    @Serial
    private static final long serialVersionUID = -2062168555047886374L;

    public Start() {
        super(Colors.BLACK,100, "Start");
    }

    @Override
    public String[] getBoxDetails() {
        String[] details = super.getBoxDetails();
        details[1] = "Ritira " + Math.abs(super.getMoney());
        return details;
    }

    @Override
    public int getMoney(int money) {
        return super.getMoney();
    }
}
