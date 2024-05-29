package table;

import java.io.Serial;

public class EmptyBox extends Box{
    @Serial
    private static final long serialVersionUID = -8188221692649199852L;

    public EmptyBox() {
        super(Colors.BLACK,0, "");
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
