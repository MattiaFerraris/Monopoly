package table;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChanceCard implements Serializable {

    @Serial
    private static final long serialVersionUID = 7377643817872625028L;
    String print;
    ProbabilityChanceType type;
    int ammount;
    String place;

    public ChanceCard(String print,ProbabilityChanceType type, int ammount, String place) {
        this.print = print;
        this.type = type;
        this.ammount = ammount;
        this.place = place;
    }

    public String getPrint() {
        return print;
    }

    public ProbabilityChanceType getType() {
        return type;
    }

    public int getAmmount() {
        return ammount;
    }

    public String getPlace() {
        return place;
    }


    static public List<ChanceCard> LoadChance(){
        List<ChanceCard> cards = new ArrayList<ChanceCard>();

        try (FileInputStream file = new FileInputStream("Imprevisti.txt"); Scanner scanner = new Scanner(file)) {
            String line;
            int i = 0;

            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                String[] split = line.split(";");

                if(!split[1].equals("vai a"))
                    cards.add(new ChanceCard(split[0], ProbabilityChanceType.getProbabilityChanceType(split[1]), Integer.parseInt(split[2]),""));
                else
                    cards.add(new ChanceCard(split[0], ProbabilityChanceType.getProbabilityChanceType(split[1]), 0, split[2]));

                i++;
            }

        } catch (FileNotFoundException e) {
            System.out.printf("File not found: %s\n", e.getMessage());
        } catch (IOException e) {
            System.out.printf("Error during reading file: %s\n", e.getMessage());
        }

        return cards;
    }
}
