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
}
