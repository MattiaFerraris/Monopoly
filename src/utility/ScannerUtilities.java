package utility;

import java.util.Scanner;

public class ScannerUtilities {
    private final Scanner scanner;

    public ScannerUtilities() {
        scanner = new Scanner(System.in);
    }

    public int readInt(String message) {
        System.out.print(message);
        while (!scanner.hasNextInt()) {
            System.out.println(message);
            scanner.next();
        }
        return scanner.nextInt();
    }

    public boolean yesOrNo(String message) {
        System.out.print(message);
        String answer = scanner.next();
        while (!isYes(answer) && !isNo(answer)) {
            System.out.print(message);
            answer = scanner.next();
        }
        return isYes(answer);
    }


    private boolean isYes(String answer) {
        return answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("si") || answer.equalsIgnoreCase("sì");
    }

    private boolean isNo(String answer) {
        return answer.equalsIgnoreCase("no") || answer.equalsIgnoreCase("n");
    }

    public String readString(String message) {
        scanner.nextLine();
        System.out.print(message);
        return scanner.nextLine();
    }
}
