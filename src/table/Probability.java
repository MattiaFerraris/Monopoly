package table;

import java.util.ArrayList;
import java.util.List;

public class Probability extends Box {

    List<ProbabilityCard> probabilityCards;

    public Probability(Colors color, int money, String name) {
        super(color, money, name);
        probabilityCards = ProbabilityCard.LoadProbability();
    }

    @Override
    public int getMoney(int money) {
        return 0;
    }

}
