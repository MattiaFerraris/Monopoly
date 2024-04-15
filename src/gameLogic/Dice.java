package gameLogic;

import java.util.Random;

public class Dice {
    private final int faces;
    public static final int DEFAULT_FACES = 6;

    public Dice(int faces) {
        if(faces <= 0)
            this.faces = DEFAULT_FACES;
        else
            this.faces = faces;
    }

    public int roll(){
        Random random = new Random();
        return random.nextInt(faces) + 1;
    }
}
