package table;

public class Start extends Box{
    public Start() {
        super(Colors.BLACK,100, "Start");
    }

    @Override
    public String[] getBoxDetails() {
        String[] details = super.getBoxDetails();
        details[1] = "Ritira " + Math.abs(super.getMoney());
        details[2] = super.getSymbolsOfPlayersInBox();
        return details;
    }

    @Override
    public int getMoney(int money) {
        return super.getMoney();
    }
}
