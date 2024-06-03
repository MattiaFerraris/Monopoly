package table;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ChanceCard extends Card implements Serializable {

    @Serial
    private static final long serialVersionUID = 7377643817872625028L;

    public ChanceCard(String print,ProbabilityChanceType type, int amount, String place) {
        super(print, type, amount, place);
    }
}
