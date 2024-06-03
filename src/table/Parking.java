package table;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.Serial;

public class Parking extends Box{
    @Serial
    private static final long serialVersionUID = 2164938642356018162L;

    public Parking() {
        super(Colors.BLACK,0, "Parcheggio");
    }

    @Override
    public StackPane generateStackPane(int cellWidth, int cellHeight, Label l) {
        StackPane stackPane = super.generateStackPane(cellWidth, cellHeight, l);
        stackPane.getChildren().add(l);
        return stackPane;
    }

    @Override
    public String[] getBoxDetails() {
        String[] details = super.getBoxDetails();

        return details;
    }

    @Override
    public int getMoney(int money) {
        return super.getMoney();
    }
}
