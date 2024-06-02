package table;

import java.io.Serial;

public class GoToPrison extends Box{

    @Serial
    private static final long serialVersionUID = 6839721514262352884L;

    public GoToPrison() {
        super(Colors.BLACK, 0, "VAI IN PRIGIONE");
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
