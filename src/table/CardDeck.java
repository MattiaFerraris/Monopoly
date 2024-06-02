package table;

import java.util.ArrayList;

public class CardDeck {
    ArrayList<? extends Card> cards;

    public CardDeck(String fileName) {
        if (fileName.equals("Imprevisti.txt")) {
            cards = ChanceCard.loadChance();
        } else if (fileName.equals("Probabilita.txt")) {
            cards = ProbabilityCard.loadProbability();
        }
    }

    public Card getCard() {
        ArrayList<Card> tempCards = new ArrayList<>(cards);
        Card card = tempCards.get(0);
        tempCards.add(card);
        tempCards.remove(0);
        cards = tempCards;
        return card;
    }
}
