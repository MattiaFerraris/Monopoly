package table;

import game.TableController;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Chance extends Event implements Serializable {

    @Serial
    private static final long serialVersionUID = -896940612407325798L;


    public Chance(Colors color, int money, String name, CardDeck cards) {
        super(color, money, name, cards);
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
}
