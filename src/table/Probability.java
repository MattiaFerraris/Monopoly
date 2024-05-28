package table;

import player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Probability extends Box {

    private List<ProbabilityCard> probabilityCards;

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
