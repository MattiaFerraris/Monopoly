package table;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public abstract class Event extends Box implements Serializable {

    @Serial
    private static final long serialVersionUID = -5870121555370866982L;

    CardDeck cards;

    public Event(Colors color, int money, String name, CardDeck cards) {
        super(color, money, name);
        this.cards = cards;
    }

    @Override
    public int getMoney(int money) {
        return 0;
    }

    public Card getCard() {
        return cards.getCard();
    }
}
