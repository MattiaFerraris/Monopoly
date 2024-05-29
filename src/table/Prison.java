package table;

import java.io.Serial;

public class Prison extends Box {

    @Serial
    private static final long serialVersionUID = -30322442817269842L;

    public Prison() {
        super(Colors.BLACK, 0, "PRIGIONE");
    }

    @Override
    public String[] getBoxDetails() {
        String[] details = super.getBoxDetails();
        return details;
    }

    @Override
    public int getMoney(int money) {
        return 0;
    }
}
