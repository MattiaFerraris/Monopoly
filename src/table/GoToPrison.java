package table;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.Serial;

public class GoToPrison extends Box{

    @Serial
    private static final long serialVersionUID = 6839721514262352884L;

    public GoToPrison() {
        super(Colors.BLACK, 0, "VAI IN PRIGIONE");
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
