package table;

import game.TableController;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

import java.io.Serial;
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
    public StackPane generateStackPane(int cellWidth, int cellHeight, Label l) {
        StackPane stackPane = super.generateStackPane(cellWidth, cellHeight, l);
        Rectangle r = new Rectangle();
        r.setHeight(cellHeight);
        r.setWidth(cellWidth - 2);
        r.setStyle("-fx-fill: #ff7300; -fx-opacity: 0.8;");
        StackPane.setAlignment(r, Pos.CENTER);
        stackPane.getChildren().addAll(r, l);
        return stackPane;
    }

    @Override
    public int getMoney(int money) {
        return 0;
    }

    public ChanceCard getChanceCard() {
        Collections.shuffle(chanceCards);
        ChanceCard chanceCard = chanceCards.get(0);
        chanceCards.add(chanceCards.get(0));
        chanceCards.remove(0);

        return chanceCard;
    }

}
