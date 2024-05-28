package table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Chance extends Box {

    private List<ChanceCard> chanceCards;


    public Chance(Colors color, int money, String name) {
        super(color, money, name);
        chanceCards = ChanceCard.LoadChance();
    }

    @Override
    public int getMoney(int money) {
        return 0;
    }

    public void getChanceCard() {
        Collections.shuffle(chanceCards);
        useChanceCard(chanceCards.get(0));
    }

    public void useChanceCard(ChanceCard chanceCard) {
        System.out.printf(chanceCard.getPrint());
    }
}
