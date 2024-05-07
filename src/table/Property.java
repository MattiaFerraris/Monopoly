package table;


import player.Player;

public class Property extends Box{
    static final int MAX_HOUSES = 4;
    static final int MAX_HOTELS = 1;
    private int housesCount;
    private int hotelsCount;
    private int priceHouse;
    private int priceHotel;
    private int price;
    private Player owner;

    public Property(Colors color, String name) {
        super(color, name);
        housesCount = 0;
        hotelsCount = 0;
        priceHouse = Box.generateMoneyValue(75,125);
        priceHotel = Box.generateMoneyValue(95,175);
        price = Box.generateMoneyValue(150,500);
    }

    public boolean addHouse(Player player){
        if(player.equals(owner) && housesCount < MAX_HOUSES && hotelsCount == 0){
            housesCount++;
            return true;
        }
        return false;
    }

    public boolean addHotel(Player player){
        if(player.equals(owner) && housesCount == MAX_HOUSES && hotelsCount < MAX_HOTELS){
            hotelsCount++;
            housesCount = 0;
            return true;
        }
        return false;
    }

    public Player getOwner() {
        return owner;
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
