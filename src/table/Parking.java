package table;

import java.io.Serial;

public class Parking extends Box{
    @Serial
    private static final long serialVersionUID = 2164938642356018162L;

    public Parking() {
        super(Colors.BLACK,0, "Parcheggio");
    }

    @Override
    public String[] getBoxDetails() {
        String[] details = super.getBoxDetails();

        return details;
    }

    @Override
    public int getMoney(int money) {
        return super.getMoney();
    }
}
