package table;

public class Property extends Box{
    public Property(Colors color, String name) {
        super(color, name);
    }

    @Override
    public String[] getBoxDetails() {
        String[] details = super.getBoxDetails();
        details[1] = "Paga " + Math.abs(super.getMoney());
        details[2] = super.getSymbolsOfPlayersInBox();
        return details;
    }

    @Override
    public int getMoney(int money) {
        return super.getMoney();
    }
}
