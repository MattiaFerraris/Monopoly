package table;

public class Prison extends Box {

    public Prison() {
        super(Colors.BLACK, 0, "PRIGIONE");
    }

    @Override
    public String[] getBoxDetails() {
        String[] details = super.getBoxDetails();
        details[2] = super.getSymbolsOfPlayersInBox();
        return details;
    }

    @Override
    public int getMoney(int money) {
        return 0;
    }
}
