package table;

import java.util.Arrays;
import java.util.Random;

public class Table {
    private int x;
    private int y;
    final public int totalBoxesCount;
    private Box[] boxes;
    private Box[][] table;

    public Table(int x, int y) {
        this.x = x;
        this.y = y;
        this.totalBoxesCount = (2 * x + (y - 2) * 2); //40 (x=11, y=11)
        boxes = assignBoxes(totalBoxesCount, createRandomBoxes());
        table = generateTable(boxes);
    }

    public Box getBox(int index) {
        return boxes[index];
    }


    private Box[] createRandomBoxes() {
        //MARRONE
        Box[] boxes = new Box[0];
        boxes = add(boxes, new Property(Colors.BROWN, "Vicolo Corto"));
        boxes = add(boxes, new Property(Colors.BROWN, "Vicolo Stretto"));
        //AZZURRO
        boxes = add(boxes, new Property(Colors.LIGHT_BLUE, "Bastioni Gran Sasso"));
        boxes = add(boxes, new Property(Colors.LIGHT_BLUE, "Viale Monterosa"));
        boxes = add(boxes, new Property(Colors.LIGHT_BLUE, "Viale Vesuvio"));
        //ROSA
        boxes = add(boxes, new Property(Colors.PINK, "Via Accademia"));
        boxes = add(boxes, new Property(Colors.PINK, "Corso Ateneo"));
        boxes = add(boxes, new Property(Colors.PINK, "Piazza Università"));
        //GRIGIO
        boxes = add(boxes, new Property(Colors.GREY, "Via Verdi"));
        boxes = add(boxes, new Property(Colors.GREY, "Via Corso Raffaello"));
        boxes = add(boxes, new Property(Colors.GREY, "Via Piazza Dante"));
        //ROSSO
        boxes = add(boxes, new Property(Colors.RED, "Via Marco Polo"));
        boxes = add(boxes, new Property(Colors.RED, "Corso Magellano"));
        boxes = add(boxes, new Property(Colors.RED, "Largo Colombo"));
        //GIALLO
        boxes = add(boxes, new Property(Colors.YELLOW, "Viale Costantino"));
        boxes = add(boxes, new Property(Colors.YELLOW, "Viale Traiano"));
        boxes = add(boxes, new Property(Colors.YELLOW, "Piazza Giulio Cesare"));
        //VERDE
        boxes = add(boxes, new Property(Colors.GREEN, "Via Roma"));
        boxes = add(boxes, new Property(Colors.GREEN, "Corso Impero"));
        boxes = add(boxes, new Property(Colors.GREEN, "Largo Augusto"));
        //BLU
        boxes = add(boxes, new Property(Colors.BLUE, "Viale dei Giardini"));
        boxes = add(boxes, new Property(Colors.BLUE, "Parco della Vittoria"));
        //NERO
        boxes = add(boxes, new Property(Colors.BLACK, "Società Acqua Potabile"));
        boxes = add(boxes, new Property(Colors.BLACK, "Società Elettrica"));

        boxes = add(boxes, new LuxuryTax(200));
        boxes = add(boxes, new WealthTax(0.10));


        boxes = add(boxes, new EmptyBox());
        boxes = add(boxes, new EmptyBox());
        boxes = add(boxes, new EmptyBox());
        boxes = add(boxes, new EmptyBox());
        boxes = add(boxes, new EmptyBox());
        boxes = add(boxes, new EmptyBox());

        //DA SOSTITUIRE CON PRIGIONE E VaiInPrigione x Mattia
        boxes = add(boxes, new EmptyBox());
        boxes = add(boxes, new EmptyBox());


        return boxes;
    }

    private Box[] add(Box[] boxes, Box box) {
        Box[] tmp = new Box[boxes.length + 1];
        for (int i = 0; i < boxes.length; i++) {
            if (boxes[i] != null) {
                tmp[i] = boxes[i];
            }
        }
        for (int i = 0; i < tmp.length; i++) {
            if (tmp[i] == null)
                tmp[i] = box;
        }
        return tmp;
    }

    private Box[] assignBoxes(int totalBoxes, Box[] boxes) {
        Box[] boxesInTable = new Box[totalBoxes];
        assignDefaultBoxes(boxesInTable);
        assignRandomBoxes(boxesInTable, boxes);
        return boxesInTable;
    }

    private void assignDefaultBoxes(Box[] boxesInTable) {
        boxesInTable[0] = new Start(); //START
        boxesInTable[totalBoxesCount / 2] = new Parking(); //PARCHEGGIO
        boxesInTable[(int) x / 2] = new Property(Colors.BLACK, "Stazione SUD");
        boxesInTable[(int) x / 2 + (x - 1)] = new Property(Colors.BLACK, "Stazione OVEST");
        boxesInTable[(int) x / 2 + (x - 1) * 2] = new Property(Colors.BLACK, "Stazione NORD");
        boxesInTable[(int) x / 2 + (x - 1) * 3] = new Property(Colors.BLACK, "Stazione EST");
        //parte nuova
        boxesInTable[x - 1] = new Prison();
        boxesInTable[(x - 1) * 3] = new GoToPrison();
    }


    private void assignRandomBoxes(Box[] boxesInTable, Box[] randomBoxes) {
        Random ran = new Random();
        for (int i = 0; i < boxesInTable.length; i++) {
            if (boxesInTable[i] == null) {
                boxesInTable[i] = pickNewBox(randomBoxes, boxesInTable);
            }
        }
    }

    private Box pickNewBox(Box[] boxes, Box[] boxesInTable) {
        Random ran = new Random();
        Box newBox;
        do {
            newBox = boxes[ran.nextInt(boxes.length)];
        } while (isBoxInTable(newBox, boxesInTable));
        return newBox;
    }

    private boolean isBoxInTable(Box box, Box[] boxesInTable) {
        for (Box b : boxesInTable) {
            if (box.equals(b))
                return true;
        }
        return false;
    }

    private Box[][] generateTable(Box[] boxes) {
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
        return table;
    }

    @Override
    public String toString() {
        StringBuilder stringTable = new StringBuilder();
        for (int i = 0; i < x; i++) {

            if (i == 0 || i == 1 || i == x - 1) {
                stringTable.append("-".repeat(Box.boxWidth * x));

            } else {
                stringTable.append("-".repeat(Box.boxWidth));
                stringTable.append(" ".repeat(Box.boxWidth * (x - 2)));
                stringTable.append("-".repeat(Box.boxWidth));
            }
            stringTable.append("\n");

            for (int d = 0; d < Box.boxHeight; d++) {
                for (int col = 0; col < table[d].length; col++) {

                    if (table[i][col] == null) {
                        stringTable.append(" ".repeat(Box.boxWidth));
                    }
                    if (table[i][col] != null) {
                        if (d == 0)
                            stringTable.append(table[i][col].getColor());

                        String[] boxDetails = table[i][col].toString().split(",");
                        stringTable.append("|");

                        stringTable.append(boxDetails[d]).append(" ".repeat(Box.boxWidth - boxDetails[d].length() - 2));
                        stringTable.append("|");
                        stringTable.append("\u001B[0m");
                    }
                }
                stringTable.append("\n");
            }
        }
        stringTable.append("-".repeat(Box.boxWidth * x));
        return stringTable.toString();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}