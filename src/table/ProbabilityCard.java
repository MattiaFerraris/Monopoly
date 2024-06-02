package table;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ProbabilityCard extends Card implements Serializable {
    @Serial
    private static final long serialVersionUID = -2657772835724882919L;

    public ProbabilityCard(String print, ProbabilityChanceType type, int ammount, String place) {
        super(print, type, ammount, place);
    }

    static public ArrayList<ProbabilityCard> loadProbability(){
        ArrayList<ProbabilityCard> cards = new ArrayList<ProbabilityCard>();

        try (FileInputStream file = new FileInputStream("Probabilita.txt"); Scanner scanner = new Scanner(file)) {
            String line;
            int i = 0;

            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                String[] split = line.split(";");


                if(!split[1].equals("vai a"))
                    cards.add(new ProbabilityCard(split[0], ProbabilityChanceType.getProbabilityChanceType(split[1]), Integer.parseInt(split[2]),""));
                else
                    cards.add(new ProbabilityCard(split[0], ProbabilityChanceType.getProbabilityChanceType(split[1]), 0, split[2]));

                i++;
            }

        } catch (FileNotFoundException e) {
            System.out.printf("File not found: %s\n", e.getMessage());
        } catch (IOException e) {
            System.out.printf("Error during reading file: %s\n", e.getMessage());
        }

        Collections.shuffle(cards);
        return cards;
    }
}
