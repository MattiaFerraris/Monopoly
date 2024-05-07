package table;

public class GoToPrison extends Box{

    public GoToPrison() {
        super(Colors.BLACK, 0, "\uD83E\uDEF5VAI IN PRIGIONE");
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
