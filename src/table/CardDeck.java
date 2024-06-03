package table;

import java.util.ArrayList;

public class CardDeck {
    ArrayList<Card> cards;

    public CardDeck(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public Card getCard() {
        Card card = cards.get(0);
        cards.add(card);
        cards.remove(0);
        return card;
    }
}
