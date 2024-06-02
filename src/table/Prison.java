package table;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.Serial;

public class Prison extends Box {

    @Serial
    private static final long serialVersionUID = -30322442817269842L;

    public Prison() {
        super(Colors.BLACK, 0, "Prigione");
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
        return 0;
    }
}
