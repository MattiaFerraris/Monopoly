package table;

import player.Player;

import java.util.Random;


public class Box {
    Types type;
    int money;
    int index;
    final static int boxWidth = 24;
    final static int height = 5;
    String[] boxDetails;
    Player[] playersInTheBox;

    //Player[] playersInTheBox;   //aggiunta

    Box(Types type, int money, int index) {
        this.type = type;
        this.money = money;
        this.index = index;
        this.playersInTheBox = new Player[2]; //this.players = new Player[Game.numberOfPlayers];
        this.boxDetails = makeDetailsString(type, money, playersInTheBox);

        //creo un'array di stringhe contenente i dettagli
        //di ogni box sotto forma di stringhe (le dimensioni sono impostate manualmente).
    }

    static int generateMoneyValue(int min, int max, int index) {
        if (index == 0) {
            return 100;
        }
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    static String getTypeString(Types type) {
        return type.toString();

    }

    static String getMoneyString(Types type, int money) {
        Types[] arrTypes = Types.values();

        if (type.toString().equalsIgnoreCase("start")) { //se è un pedaggio
            return "Pay: " + Integer.toString(money);
        }
        if (type.toString().equalsIgnoreCase("toll")) { //se è lo start
            return "Get: " + Integer.toString(money);
        }
        return "";

    }

    static Types picType(int index) {
        if (index == 0) {
            return Types.START;
        }
        return Types.TOLL;
    }

    String[] makeDetailsString(Types type, int money, Player[] players) {
        return new String[]{getTypeString(type), getMoneyString(type, money), "", "", playersToString()};
    }
    String playersToString() {
        String tmp = "";
        for (Player inTheBox : playersInTheBox) {
            if (inTheBox != null) {
                tmp += inTheBox.getSymbol() + " ";
            }
        }
        return tmp;
    }

}
