package table;

import java.util.ArrayList;
import java.util.List;

public enum Colors {
    BROWN("\033[94m"), LIGHT_BLUE("\033[36m"), PINK("\033[35m"), GREY("\033[37m"), RED("\033[31m"), YELLOW("\033[33m"), GREEN("\033[32m"), BLUE("\033[34m"), BLACK("\033[00m"), RESET("\033[00m");

    private final String color;

    Colors(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return color;
    }
}
