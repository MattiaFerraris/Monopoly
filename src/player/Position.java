//CLASSE NON USATE, DA TOGLIERE
package player;

import table.Table;

public class Position {
    private int positionNumber = 0;
    private Table table;

    public Position(Table table) {
        this.table = table;
    }

    public void setPositionNumber(int positionNumber) {
        this.positionNumber = positionNumber;
    }
    public int getPositionNumber() {
        return positionNumber;
    }

    void updatePosition(int dice){
        System.out.println("Dado: " + dice);
        if((positionNumber+dice) >= getTableDimensions()){
            positionNumber = (positionNumber+dice)-getTableDimensions();
        }
        else
            positionNumber = positionNumber + dice;
    }

    int getTableDimensions(){
        return ((table.getX()*2+table.getY()*2)-5);
    }
}
