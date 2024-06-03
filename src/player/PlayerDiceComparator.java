package player;

import java.util.Comparator;
import java.util.Map;

public class PlayerDiceComparator implements Comparator<Map.Entry<Player, Integer>> {
    @Override
    public int compare(Map.Entry<Player, Integer> entry1, Map.Entry<Player, Integer> entry2) {
        return entry2.getValue() - entry1.getValue();
    }
}
