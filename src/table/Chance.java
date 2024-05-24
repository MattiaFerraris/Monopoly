package table;

import java.util.ArrayList;
import java.util.List;

public class Chance extends Box {

    List<ChanceCard> chanceCards;


    public Chance(Colors color, int money, String name) {
        super(color, money, name);
        chanceCards = ChanceCard.LoadChance();
    }

    @Override
    public int getMoney(int money) {
        return 0;
    }
}
