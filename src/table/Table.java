package table;

import java.util.Arrays;
import java.util.Random;

public class Table {
    private int x;
    private int y;
    final public int totalBoxesCount;
    private Box[] boxes;
    private Box[][] table;
    private int[] propertyCount = new int[Colors.values().length - 1];


    public Table(int x, int y) {
        this.x = x;
        this.y = y;
        this.totalBoxesCount = (2 * x + (y - 2) * 2); //40 (x=11, y=11)
        boxes = assignBoxes(totalBoxesCount, createRandomBoxes());
        for (Box box : boxes) {
            if (box.getColor() != Colors.BLACK) {
                propertyCount[box.getColor().ordinal()]++;
            }
        }
        table = generateTable(boxes);
    }

    public Box getBox(int index) {
        return boxes[index];
    }


    private Box[] createRandomBoxes() {
        //MARRONE
        Box[] boxes = new Box[0];
        boxes = add(boxes, new BuildableProperty(Colors.BROWN, "Vicolo Corto"));
        boxes = add(boxes, new BuildableProperty(Colors.BROWN, "Vicolo Stretto"));
        //AZZURRO
        boxes = add(boxes, new BuildableProperty(Colors.LIGHT_BLUE, "Bastioni Gran Sasso"));
        boxes = add(boxes, new BuildableProperty(Colors.LIGHT_BLUE, "Viale Monterosa"));
        boxes = add(boxes, new BuildableProperty(Colors.LIGHT_BLUE, "Viale Vesuvio"));
        //ROSA
        boxes = add(boxes, new BuildableProperty(Colors.PINK, "Via Accademia"));
        boxes = add(boxes, new BuildableProperty(Colors.PINK, "Corso Ateneo"));
        boxes = add(boxes, new BuildableProperty(Colors.PINK, "Piazza Università"));
        //GRIGIO
        boxes = add(boxes, new BuildableProperty(Colors.GREY, "Via Verdi"));
        boxes = add(boxes, new BuildableProperty(Colors.GREY, "Via Corso Raffaello"));
        boxes = add(boxes, new BuildableProperty(Colors.GREY, "Via Piazza Dante"));
        //ROSSO
        boxes = add(boxes, new BuildableProperty(Colors.RED, "Via Marco Polo"));
        boxes = add(boxes, new BuildableProperty(Colors.RED, "Corso Magellano"));
        boxes = add(boxes, new BuildableProperty(Colors.RED, "Largo Colombo"));
        //GIALLO
        boxes = add(boxes, new BuildableProperty(Colors.YELLOW, "Viale Costantino"));
        boxes = add(boxes, new BuildableProperty(Colors.YELLOW, "Viale Traiano"));
        boxes = add(boxes, new BuildableProperty(Colors.YELLOW, "Piazza Giulio Cesare"));
        //VERDE
        boxes = add(boxes, new BuildableProperty(Colors.GREEN, "Via Roma"));
        boxes = add(boxes, new BuildableProperty(Colors.GREEN, "Corso Impero"));
        boxes = add(boxes, new BuildableProperty(Colors.GREEN, "Largo Augusto"));
        //BLU
        boxes = add(boxes, new BuildableProperty(Colors.BLUE, "Viale dei Giardini"));
        boxes = add(boxes, new BuildableProperty(Colors.BLUE, "Parco della Vittoria"));
        //NERO
        boxes = add(boxes, new BuildableProperty(Colors.BLACK, "Società Acqua Potabile"));
        boxes = add(boxes, new BuildableProperty(Colors.BLACK, "Società Elettrica"));

        boxes = add(boxes, new LuxuryTax());
        boxes = add(boxes, new WealthTax());


        boxes = add(boxes, new EmptyBox());
        boxes = add(boxes, new EmptyBox());
        boxes = add(boxes, new EmptyBox());
        boxes = add(boxes, new EmptyBox());
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
                stringTable.append("⚊".repeat(Box.WIDTH * x));

            } else {
                stringTable.append("⚊".repeat(Box.WIDTH));
                stringTable.append(" ".repeat(Box.WIDTH * (x - 2)));
                stringTable.append("⚊".repeat(Box.WIDTH));
            }
            stringTable.append("\n");

            for (int d = 0; d < Box.HEIGHT; d++) {
                for (int col = 0; col < table[d].length; col++) {

                    if (table[i][col] == null) {
                        stringTable.append(" ".repeat(Box.WIDTH));
                    }
                    if (table[i][col] != null) {
                        stringTable.append("❙");
                        if (d == 0)
                            stringTable.append(table[i][col].getColor());

                        String[] boxDetails = table[i][col].getBoxDetails();

                        stringTable.append(boxDetails[d]).append(" ".repeat(Box.WIDTH - boxDetails[d].length() - 2));
                        stringTable.append("\u001B[0m");
                        stringTable.append("❙");
                    }
                }
                stringTable.append("\n");
            }
        }
        stringTable.append("⚊".repeat(Box.WIDTH * x));
        return stringTable.toString();
    }

    public int getPropertyCount(Colors color) {
        return propertyCount[color.ordinal()];
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Box[] getBoxes() {
        return boxes;
    }
}