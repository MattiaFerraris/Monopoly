package table;

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
        this.totalBoxesCount = (2 * x + (y - 2) * 2); //32 (x=9, y=9)
        boxes = generateBoxes(totalBoxesCount, assignBoxes());
        table = generateTable(boxes);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Box getBox(int index) {
        return boxes[index];
    }


    Box[] assignBoxes() {
        //24 boxes
        //MARRONE
        Box[] tmp = new Box[0];
        tmp = add(tmp, new Property(Colors.BROWN, "Vicolo Corto"));
        tmp = add(tmp, new Property(Colors.BROWN, "Vicolo Stretto"));
        //AZZURRO
        tmp = add(tmp, new Property(Colors.LIGHT_BLUE, "Bastioni Gran Sasso"));
        tmp = add(tmp, new Property(Colors.LIGHT_BLUE, "Viale Monterosa"));
        tmp = add(tmp, new Property(Colors.LIGHT_BLUE, "Viale Vesuvio"));
        //ROSA
        tmp = add(tmp, new Property(Colors.PINK, "Via Accademia"));
        tmp = add(tmp, new Property(Colors.PINK, "Corso Ateneo"));
        tmp = add(tmp, new Property(Colors.PINK, "Piazza Università"));
        //GRIGIO
        tmp = add(tmp, new Property(Colors.GREY, "Via Verdi"));
        tmp = add(tmp, new Property(Colors.GREY, "Via Corso Raffaello"));
        tmp = add(tmp, new Property(Colors.GREY, "Via Piazza Dante"));
        //ROSSO
        tmp = add(tmp, new Property(Colors.RED, "Via Marco Polo"));
        tmp = add(tmp, new Property(Colors.RED, "Corso Magellano"));
        tmp = add(tmp, new Property(Colors.RED, "Largo Colombo"));
        //GIALLO
        tmp = add(tmp, new Property(Colors.YELLOW, "Viale Costantino"));
        tmp = add(tmp, new Property(Colors.YELLOW, "Viale Traiano"));
        tmp = add(tmp, new Property(Colors.YELLOW, "Piazza Giulio Cesare"));
        //VERDE
        tmp = add(tmp, new Property(Colors.GREEN, "Via Roma"));
        tmp = add(tmp, new Property(Colors.GREEN, "Corso Impero"));
        tmp = add(tmp, new Property(Colors.GREEN, "Largo Augusto"));
        //BLU
        tmp = add(tmp, new Property(Colors.BLUE, "Viale dei Giardini"));
        tmp = add(tmp, new Property(Colors.BLUE, "Parco della Vittoria"));
        //NERO
        tmp = add(tmp, new Property(Colors.BLACK, "Società Acqua Potabile"));
        tmp = add(tmp, new Property(Colors.BLACK, "Società Elettrica"));

        //VARIE
        tmp = add(tmp, new LuxuryTax());
        tmp = add(tmp, new WealthTax());
        return tmp;
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

    /**
     * Genera i box da inserire nella tabella
     * @param totalBoxes
     * @param boxes
     * @return
     */
    Box[] generateBoxes(int totalBoxes, Box[] boxes) {
        Box[] boxesInTable = new Box[totalBoxes];
        //box assegnati di default
        boxesInTable[0] = new Start(); //START
        boxesInTable[totalBoxesCount / 2] = new Parking(); //PARCHEGGIO
        boxesInTable[(int) x / 2] = new Property(Colors.BLACK, "Stazione SUD");
        boxesInTable[(int) x / 2 + (x - 1)] = new Property(Colors.BLACK, "Stazione OVEST");
        boxesInTable[(int) x / 2 + (x - 1) * 2] = new Property(Colors.BLACK, "Stazione NORD");
        boxesInTable[(int) x / 2 + (x - 1) * 3] = new Property(Colors.BLACK, "Stazione EST");

        int cnt = 0;
        for (int i = 0; i < totalBoxes; i++) {

            if (boxesInTable[i] == null && cnt < boxes.length)
                boxesInTable[i] = pickNewBox(boxes, boxesInTable);

        }

        return boxesInTable;
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
        return table;
    }

    @Override
    public String toString() {
        String stringTable = "";
        for (int i = 0; i < x; i++) {

            if (i == 0 || i == 1 || i == x - 1) {
                stringTable += "-".repeat(Box.BOX_WIDTH * x);

            } else {
                stringTable += "-".repeat(Box.BOX_WIDTH);
                stringTable += " ".repeat(Box.BOX_WIDTH * (x - 2));
                stringTable += "-".repeat(Box.BOX_WIDTH);
            }
            stringTable += "\n";

            for (int d = 0; d < Box.HEIGHT; d++) {
                for (int col = 0; col < table[d].length; col++) {

                    if (table[i][col] == null) {
                        stringTable += " ".repeat(Box.BOX_WIDTH);
                    }
                    if (table[i][col] != null) {
                        if(d == 0)
                            stringTable += table[i][col].getColor();

                        String[] boxDetails = table[i][col].getBoxDetails();

                        stringTable += "|";
                        stringTable += boxDetails[d] + " ".repeat(Box.BOX_WIDTH - boxDetails[d].length() - 2);
                        stringTable += "|";
                        stringTable += "\u001B[0m";
                    }
                }
                stringTable += "\n";
            }
        }
        stringTable += "-".repeat(Box.BOX_WIDTH * x);
        return stringTable;
    }
}