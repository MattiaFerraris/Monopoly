package table;


import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import player.Player;

import java.io.Serial;

public class Property extends Box{
    @Serial
    private static final long serialVersionUID = 41741538644209896L;
    private Player owner;
    private int price;

    public Property(Colors color, String name) {
        super(color, name);
        price = Box.generateMoneyValue(150,500);
    }

    public void reset(){
        owner = null;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public StackPane generateStackPane(int cellWidth, int cellHight, Label l) {
        StackPane stackPane = super.generateStackPane(cellWidth, cellHight, l);
        Rectangle r = new Rectangle();
        r.setHeight(10);
        r.setWidth(cellWidth - 2);
        String color = super.getColor().toString();
        r.setStyle("-fx-fill: " + color + "; -fx-stroke: black; -fx-stroke-width: 2;");
        StackPane.setAlignment(r, Pos.TOP_CENTER);
        stackPane.getChildren().addAll(r, l);
        return stackPane;
    }

    @Override
    public String[] getBoxDetails() {
        String[] details = super.getBoxDetails();
        details[1] = "Paga " + Math.abs(super.getMoney());
        details[2] = owner!=null?owner.getName():("Prezzo acquisto " + price);
        return details;
    }

    @Override
    public int getMoney(int money) {
        return super.getMoney();
    }
}
