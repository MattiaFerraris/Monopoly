package table;

public enum ProbabilityChanceType {
    PAY("paga"),RECEIVE("ricevi"),GOTO("vai a");

    String string;

    ProbabilityChanceType(String string) {
        this.string = string;
    }

   static public ProbabilityChanceType getProbabilityChanceType(String s) {
        s = s.toLowerCase().trim();

        return switch (s) {
            case "paga" -> PAY;
            case "ricevi" -> RECEIVE;
            default -> GOTO;
        };
    }
}
