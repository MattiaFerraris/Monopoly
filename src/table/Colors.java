package table;

public enum Colors {
    BROWN, LIGHT_BLUE, PINK, GREY, RED, YELLOW, GREEN, BLUE, BLACK;

    @Override
    public String toString() {
        return switch (this){
            case BROWN -> "\033[94m";
            case LIGHT_BLUE -> "\033[36m";
            case PINK -> "\033[35m";
            case GREY -> "\033[37m";
            case RED -> "\033[31m";
            case YELLOW -> "\033[33m";
            case GREEN -> "\033[32m";
            case BLUE -> "\033[34m";
            case BLACK -> "\033[30m";
        };
    }
}
