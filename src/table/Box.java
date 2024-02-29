package table;

import java.util.Random;


public class Box {
    Types type;
    int money;
    int index;
    final static int boxWidth = 24;
    final static int height = 7;

    //Player[] playersInTheBox;   //aggiunta

    Box(Types type, int money, int index) {
        this.type = type;
        this.money = money;
        this.index = index;
    }

    static int generateMoneyValue(int min, int max, int index) {
        if (index == 0) {
            return 100;
        }
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    static Types picType(int index) {
        if (index == 0) {
            return Types.START;
        }
        return Types.TOLL;
    }

}
