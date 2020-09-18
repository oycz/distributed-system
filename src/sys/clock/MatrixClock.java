package sys.clock;

import java.util.HashMap;
import java.util.Map;

// TODO
public class MatrixClock extends Clock<Map<String, Map<String, Integer>>> {

    public MatrixClock(String nodeId) {
        this.clock = new HashMap<>();
        Map<String, Integer> self = new HashMap<>();
        self.put(nodeId, 0);
        clock.put(nodeId, self);
    }

    @Override
    public void increase() {

    }

    @Override
    public int compareTo(Clock o) {
        return 0;
    }
}
