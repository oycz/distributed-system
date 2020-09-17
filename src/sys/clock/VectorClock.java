package sys.clock;

import java.util.HashMap;
import java.util.Map;

// TODO
public class VectorClock extends Clock<Map> {

    private Map<String, Integer> vectorClock;

    public VectorClock(String nodeId) {
        this.vectorClock = new HashMap<>();
        this.vectorClock.put(nodeId, 0);
    }

//    @Override
//    public int compareTo(VectorClock o) {
//        boolean hasBefore = false, hasAfterOrEqual = false;
//        Set<String> keys = new HashSet<>();
//        keys.addAll(vectorClock.keySet());
//        keys.addAll(o.vectorClock.keySet());
//        for(String key: keys) {
//            Integer c1 = vectorClock.containsKey(key)? vectorClock.get(key): 0;
//            Integer c2 = o.vectorClock.containsKey(key)? o.vectorClock.get(key): 0;
//            if(c1 < c2) {
//                hasBefore = true;
//            } else if(c1 >= c2) {
//                hasAfterOrEqual = true;
//            }
//            if(hasBefore && hasAfterOrEqual) {
//                return 0;
//            }
//        }
//        if(hasBefore) {
//            return -1;
//        }
//        return 1;
//    }

    @Override
    public int compareTo(Map o) {
        return 0;
    }
}
