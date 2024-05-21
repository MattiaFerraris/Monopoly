package table;

import java.util.List;

public class Probability extends Box {

    public Probability(Colors color, int money, String name) {
        super(color, money, name);
    }

    @Override
    public int getMoney(int money) {
        return 0;
    }


}
