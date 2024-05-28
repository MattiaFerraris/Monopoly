package table;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Probability extends Box {

    @Serial
    private static final long serialVersionUID = -6002629718963144379L;
    List<ProbabilityCard> probabilityCards;

    public Probability(Colors color, int money, String name) {
        super(color, money, name);
        probabilityCards = ProbabilityCard.LoadProbability();
    }

    @Override
    public int getMoney(int money) {
        return 0;
    }

    public void getProbabilityCards() {
        Collections.shuffle(probabilityCards);
        useProbabilityCard(probabilityCards.get(0));
    }

    public void useProbabilityCard(ProbabilityCard probabilityCard) {
        System.out.printf(probabilityCard.getPrint());
    }
}
