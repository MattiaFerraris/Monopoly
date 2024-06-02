package table;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.Serial;

public class Start extends Box{
    @Serial
    private static final long serialVersionUID = -2062168555047886374L;

    public Start() {
        super(Colors.BLACK,100, "Via");
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
        details[1] = "Ritira " + Math.abs(super.getMoney());
        return details;
    }

    @Override
    public int getMoney(int money) {
        return super.getMoney();
    }
}
