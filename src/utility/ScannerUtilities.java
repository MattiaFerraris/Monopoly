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

    public String readString(String message) {
        System.out.print(message);
        return scanner.next();
    }
}
