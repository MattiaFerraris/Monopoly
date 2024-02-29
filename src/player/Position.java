package player;

public class Position {
    private int positionNumber = 0;

    void updatePosition(int dice){
        positionNumber = positionNumber + dice;

    }

    public int getPosition() {
        return positionNumber;
    }
}
