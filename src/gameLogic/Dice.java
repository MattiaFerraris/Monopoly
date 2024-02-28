package gameLogic;

import java.util.Random;

public class Dice {
    private final int faces;

    public Dice(int faces) {
        this.faces = faces;
    }

    public int roll(){
        Random random = new Random();
        return random.nextInt(faces) + 1;
    }
}
