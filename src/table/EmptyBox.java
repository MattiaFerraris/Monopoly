package table;

public class EmptyBox extends Box{
    public EmptyBox() {
        super(Colors.BLACK,0, "");
    }

    @Override
    public String[] getBoxDetails() {
        String[] details = super.getBoxDetails();

        return details;
    }

    @Override
    public int getMoney(int money) {
        return super.getMoney();
    }
}
