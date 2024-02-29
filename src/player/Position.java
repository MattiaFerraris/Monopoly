package player;

import table.Table;

public class Position {
    private int positionNumber = 0;

    void updatePosition(int dice){
        if(positionNumber == (Table.X)){
            positionNumber = positionNumber + dice;
        }


    }

    public int getPosition() {
        return positionNumber;
    }
}
