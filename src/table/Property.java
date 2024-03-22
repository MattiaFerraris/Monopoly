package table;

public class Property extends Box{
    public Property(Colors color, String name) {
        super(color, name);
    }

    public String colorString(){
        return switch (color){
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
    @Override
    public String toString() {
        return super.toString() + "Paga " + money + "," + ",".repeat(3) + "," + colorString();
    }
}
