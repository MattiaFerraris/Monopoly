package table;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProbabilityCard {

    private String print;
    private ProbabilityChanceType type;
    private int ammount;
    private String place;

    public ProbabilityCard(String print,ProbabilityChanceType type, int ammount, String place) {
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

    static public List<ProbabilityCard> LoadProbability(){
        List<ProbabilityCard> cards = new ArrayList<ProbabilityCard>();

        try (FileInputStream file = new FileInputStream("Probabilita.txt"); Scanner scanner = new Scanner(file)) {
            String line;
            int i = 0;

            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                String[] split = line.split(";");


                if(!split[1].equals("GOTO"))
                    cards.add(new ProbabilityCard(split[0], ProbabilityChanceType.getProbabilityChanceType(split[1]), Integer.parseInt(split[2]),""));
                else
                    cards.add(new ProbabilityCard(split[0], ProbabilityChanceType.valueOf(split[1]), 0, split[2]));

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
