package table;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.Serial;

public class LuxuryTax extends Box{
    private static final int TAX = 200;
    @Serial
    private static final long serialVersionUID = -1956401495009554119L;

    public LuxuryTax(int tax) {
        super(Colors.BLACK, -tax, "Tassa di lusso");
    }

    public LuxuryTax() {
        this(TAX);
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
        details[1] = "Paga " + Math.abs(super.getMoney());

        return details;
    }

    @Override
    public int getMoney(int money) {
        return super.getMoney();
    }

}
