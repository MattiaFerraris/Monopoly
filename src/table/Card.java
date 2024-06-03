package table;

import java.io.Serial;
import java.io.Serializable;

public class Card implements Serializable {
    @Serial
    private static final long serialVersionUID = -7684442710459717505L;
    private String print;
    private ProbabilityChanceType type;
    private int amount;
    private String place;

    public Card(String print,ProbabilityChanceType type, int amount, String place) {
        this.print = print;
        this.type = type;
        this.amount = amount;
        this.place = place;
    }

    public String getPrint() {
        return print;
    }

    public ProbabilityChanceType getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public String getPlace() {
        return place;
    }
}
