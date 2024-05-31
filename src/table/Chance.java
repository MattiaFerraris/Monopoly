package table;

import game.TableController;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Chance extends Box {

    @Serial
    private static final long serialVersionUID = -896940612407325798L;
    List<ChanceCard> chanceCards;


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
        TableController.showAlert(chanceCard.getPrint(), "IMPREVISTI");
        //System.out.printf(chanceCard.getPrint());
    }

}
