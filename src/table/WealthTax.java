package table;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.Serial;

public class WealthTax extends Box {

    private static final double DEAFAULT_TAX = 0.1;
    @Serial
    private static final long serialVersionUID = -5202390224021070504L;

    private double tax;

    public WealthTax(double tax) {
        super(Colors.BLACK, 0, "Tassa patrimoniale");
        this.tax = tax;
    }

    public WealthTax() {
        this(DEAFAULT_TAX);
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
        details[1] = "Paga " + tax + " del partrim.";
        return details;
    }

    public int getMoney(int money) {
        return  (int) (-(money * tax));
    }
}
