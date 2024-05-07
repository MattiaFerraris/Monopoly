package table;



public class Property extends Box{
    //
    private int nHouses = 0;
    private int nHotels = 0;
    private int priceHouse;
    private int priceHotel;
    private int price;

    //
    public Property(Colors color, String name) {
        super(color, name);
        priceHouse = Box.generateMoneyValue(75,125);
        priceHotel = Box.generateMoneyValue(95,175);
        price = Box.generateMoneyValue(150,500);
    }

    @Override
    public String[] getBoxDetails() {
        String[] details = super.getBoxDetails();
        details[1] = "Paga " + Math.abs(super.getMoney());
        details[2] = "Prezzo acquisto " + price;
        details[3] = "Prezzo casa: " + Math.abs(priceHouse);
        details[4] = "Prezzo hotel: " + Math.abs(priceHotel);
        details[5] = "Case: " + nHouses + "| Hotel: " + nHotels;

        return details;
    }

    @Override
    public int getMoney(int money) {
        return super.getMoney();
    }
}
