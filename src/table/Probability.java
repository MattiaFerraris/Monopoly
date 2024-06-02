package table;

import game.TableController;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

import java.io.Serial;
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
    public StackPane generateStackPane(int cellWidth, int cellHeight, Label l) {
        StackPane stackPane = super.generateStackPane(cellWidth, cellHeight, l);
        Rectangle r = new Rectangle();
        r.setHeight(cellHeight);
        r.setWidth(cellWidth - 2);
        r.setStyle("-fx-fill: #2a9ced; -fx-opacity: 0.8;");
        StackPane.setAlignment(r, Pos.CENTER);
        stackPane.getChildren().addAll(r, l);
        return stackPane;
    }

    @Override
    public int getMoney(int money) {
        return 0;
    }

    public ProbabilityCard getProbabilityCards() {
        ProbabilityCard probabilityCard = probabilityCards.get(0);
        probabilityCards.add(probabilityCards.get(0));
        probabilityCards.remove(0);
        Collections.shuffle(probabilityCards);
        return probabilityCard;
    }

}
