package sys.clock;

import java.util.HashMap;
import java.util.Map;

// TODO
public class MatrixClock extends Clock<Map> {

    private Map<String, Map<String, Integer>> matrixClock;

    public MatrixClock(String nodeId) {
        this.matrixClock = new HashMap<>();
        Map<String, Integer> self = new HashMap<>();
        self.put(nodeId, 0);
        matrixClock.put(nodeId, self);
    }

    @Override
    public int compareTo(Map o) {
        return 0;
    }
}
