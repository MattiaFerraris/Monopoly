package table;

public class Table {
    int x;
    int y;
    Box[] boxes;
    Box[][] table;

    Table(int x, int y) {
        this.x = x;
        this.y = y;
        int boxesNumber = (2 * x + (y - 2) * 2); //16 (x=5, y=5)
        boxes = generateBoxes(boxesNumber);
        table = generateTable(boxes);
    }

    Box[] generateBoxes(int n) {
        Box[] tmp = new Box[n];
        for (int i = 0; i < tmp.length; i++) {
            tmp[i] = new Box(Box.picType(i), Box.generateMoneyValue(50, 150, i), i);
        }
        return tmp;
    }

    Box[][] generateTable(Box[] boxes) {
        Box[][] table = new Box[y][x];

        for (int i = y; i > 0; i--) {

            if (i == y) {         //se la colonna corrisponde all'ultima
                for (int j = 0; j < x - 1; j++) {     //popola l'ultima riga della matrice
                    table[i - 1][x - j] = boxes[j];    //partendo da destra a prendere i box dal primo (START)
                }                                      //e finendo con la casella 4
            } else if (i == 1) {
                for (int j = 0; j > x - 1; j--) {               //la prima riga della matrice
                    table[i - 1][x - j] = boxes[boxes.length - j - 1 - (y - 2) * 2];
                                                //deve uscire 12
                }
            } else {

            }

        }


        return table;
    }


    void showTable() {
        for (int i = 0; i < x; i++) {
            if (i == 0 || i == 1 || i == x - 1) {
                System.out.println("-".repeat(Box.boxWidth * x));

            } else {
                System.out.print("-".repeat(Box.boxWidth));
                System.out.print(" ".repeat(Box.boxWidth * (x - 2)));
                System.out.print("-".repeat(Box.boxWidth));

            }
            System.out.println();
        }

    }

}