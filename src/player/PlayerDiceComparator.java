package player;

import java.util.Comparator;
import java.util.Map;

public class PlayerDiceComparator implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        Map.Entry<Player, Integer> entry1 = (Map.Entry<Player, Integer>) o1;
        Map.Entry<Player, Integer> entry2 = (Map.Entry<Player, Integer>) o2;
        return entry1.getValue() - entry2.getValue();
    }
}
