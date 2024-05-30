package table;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class Table implements Serializable {
    @Serial
    private static final long serialVersionUID = 1662367044573714210L;
    private int x;
    private int y;
    final public int totalBoxesCount;
    private final Box[] boxes;
    private final Box[][] table;
    private final int[] propertyCount = new int[8];

    public Table(int x, int y) {
        this.x = x;
        this.y = y;
        this.totalBoxesCount = (2 * x + (y - 2) * 2); //40 (x=11, y=11)
        boxes = assignBoxes(totalBoxesCount);
        for (Box box : boxes) {
            if (box instanceof BuildableProperty) {
                propertyCount[box.getColor().ordinal()]++;
            }
        }
        table = generateTable(boxes);
    }

    public Box getBox(int index) {
        return boxes[index];
    }

    public void readBoxes(ArrayList<Box> boxes) {
        try (FileInputStream file = new FileInputStream("PropertyData.csv");
             Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] tokens = scanner.nextLine().split(",");
                if (tokens.length != 2)
                    continue;
                if (Colors.valueOf(tokens[0]) == Colors.BLACK)
                    boxes.add(new Property(Colors.valueOf(tokens[0].trim()), tokens[1].trim()));
                else
                    boxes.add(new BuildableProperty(Colors.valueOf(tokens[0].trim()), tokens[1].trim()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Box[] createRandomBoxes() {
        ArrayList<Box> boxes = new ArrayList<>();
        readBoxes(boxes);
        boxes.add(new LuxuryTax());
        boxes.add(new WealthTax());
        for (int i = 0; i < 3; i++)
            boxes.add(new Chance(Colors.RED, 0, "Imprevisti"));
        for (int i = 0; i < 3; i++)
            boxes.add(new Probability(Colors.BLUE, 0, "Probabilità"));

        Collections.shuffle(boxes);
        return boxes.toArray(new Box[0]);
    }

    private Box[] assignBoxes(int totalBoxes) {
        Box[] boxesInTable = new Box[totalBoxes];
        assignDefaultBoxes(boxesInTable);
        assignRandomBoxes(boxesInTable);
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


    private void assignRandomBoxes(Box[] boxesInTable) {
        Box[] randomBoxes = createRandomBoxes();
        int assignedBoxes = 0;
        for (int i = 0; i < boxesInTable.length; i++) {
            if (boxesInTable[i] == null)
                boxesInTable[i] = randomBoxes[assignedBoxes++];
        }
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
                stringTable.append("-".repeat(Box.WIDTH * x));

            } else {
                stringTable.append("-".repeat(Box.WIDTH));
                stringTable.append(" ".repeat(Box.WIDTH * (x - 2)));
                stringTable.append("-".repeat(Box.WIDTH));
            }
            stringTable.append("\n");

            for (int d = 0; d < Box.HEIGHT; d++) {
                for (int col = 0; col < table[d].length; col++) {

                    if (table[i][col] == null) {
                        stringTable.append(" ".repeat(Box.WIDTH));

                    }
                    if (table[i][col] != null) {
                        stringTable.append("|");
                        if (d == 0)
                            stringTable.append(table[i][col].getColor());

                        String[] boxDetails = table[i][col].getBoxDetails();

                        if (d == Box.HEIGHT - 1) {
                            stringTable.append(printSymbolLine(table[i][col], boxDetails[d]));
                        } else
                            stringTable.append(boxDetails[d]).append(" ".repeat(Box.WIDTH - boxDetails[d].length() - 2));
                        stringTable.append("\u001B[0m");
                        stringTable.append("|");
                    }
                }
                stringTable.append("\n");
            }
        }
        stringTable.append("-".repeat(Box.WIDTH * x));
        return stringTable.toString();
    }

    private String printSymbolLine(Box box, String symbolString) {
        if (box.getCntPlayersInTheBox() > 0) {
            return symbolString + " ".repeat(Box.WIDTH - (box.getCntPlayersInTheBox() * 2) - 1);
        }
        return symbolString + " ".repeat(Box.WIDTH - (box.getCntPlayersInTheBox() * 2) - 2);

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