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


        for (int i = 0; i < x; i++) {     //popola l'ultima riga della matrice
            table[y - 1][x - i - 1] = boxes[i];    //partendo da destra a prendere i box dal primo (START)
        }                                      //e finendo con la casella 4

        for (int i = y - 1; i > 1; i--) {       //popola la colonna di sinistra (tranne il primo e ultimo)
            table[i - 1][0] = boxes[x + (y - 1 - i)];
        }

        for (int i = 0; i < x - 1; i++) {       //popola la prima riga (tranne l'ultimo elemento)
            table[0][i] = boxes[boxes.length / 2 + i];
        }

        for (int i = 0; i < y - 1; i++) {       //popola la colonna di destra (tranne l'ultimo elemento)
            table[i][x - 1] = boxes[(x - 1) * 3 + i];
        }


        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                if (table[i][j] == null) {
                    System.out.print("n ");
                } else
                    System.out.print(table[i][j].index + " ");
            }
            System.out.println();
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

            for (int d = 0; d < y; d++) {
                for (int col = 0; col < x; col++) {
                    if (table[d][col] == null)
                        System.out.print(" ".repeat(Box.boxWidth));
                    else {
                        System.out.print("|");
                        System.out.print(table[d][col].boxDetails[d]);
                        System.out.print("|");
                    }
                }
                System.out.println();
            }
        }
        System.out.println("-".repeat(Box.boxWidth*x));
    }
}