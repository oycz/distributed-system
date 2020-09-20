package sys.clock;

import java.util.HashMap;
import java.util.Map;

public class VectorClock extends Clock<Map<String, Integer>> {

    public VectorClock(String nodeId) {
        this.clock = new HashMap<>();
        this.clock.put(nodeId, 0);
    }

    @Override
    public void increase() {

    }

    @Override
    public int compareTo(Clock o) {
        return 0;
    }
}
