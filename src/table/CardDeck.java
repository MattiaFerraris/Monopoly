package table;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class CardDeck implements Serializable {
    @Serial
    private static final long serialVersionUID = -7950005243166128395L;
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
