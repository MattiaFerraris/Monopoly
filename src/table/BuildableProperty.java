package table;

import player.Player;

import java.io.Serial;

public class BuildableProperty extends Property{
    static final int MAX_HOUSES = 4;
    static final int MAX_HOTELS = 1;
    @Serial
    private static final long serialVersionUID = -6393718302342100774L;
    private int housesCount;
    private int hotelsCount;
    private int priceHouse;
    private int priceHotel;

    public BuildableProperty(Colors color, String name) {
        super(color, name);
        housesCount = 0;
        hotelsCount = 0;
        priceHouse = Box.generateMoneyValue(75,125);
        priceHotel = Box.generateMoneyValue(95,175);
    }

    public boolean addHouse(Player player){
        if(player.equals(super.getOwner()) && housesCount < MAX_HOUSES && hotelsCount == 0){
            housesCount++;
            return true;
        }
        return false;
    }

    public boolean addHotel(Player player){
        if(player.equals(super.getOwner()) && housesCount == MAX_HOUSES && hotelsCount < MAX_HOTELS){
            hotelsCount++;
            housesCount = 0;
            return true;
        }
        return false;
    }

    @Override
    public void reset() {
        super.reset();
        housesCount = 0;
        hotelsCount = 0;
    }

    @Override
    public int getMoney(int money) {
        return super.getMoney(money)+housesCount*15+hotelsCount*100;
    }

    public int getHousesCount() {
        return housesCount;
    }

    public int getHotelsCount() {
        return hotelsCount;
    }

    public int getPriceHouse() {
        return priceHouse;
    }

    public int getPriceHotel() {
        return priceHotel;
    }

    @Override
    public String[] getBoxDetails() {
        String[] details = super.getBoxDetails();
        details[3] = "Prezzo casa: " + Math.abs(priceHouse);
        details[4] = "Prezzo hotel: " + Math.abs(priceHotel);
        //details[5] = "⌂ ".repeat(housesCount) + "  ".repeat(hotelsCount);
        details[5] = "Case: " + housesCount + "| Hotel: " + hotelsCount;
        return details;
    }
}
