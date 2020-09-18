package sys.clock;

import java.io.Serializable;

public abstract class Clock<T> implements Comparable<T>, Serializable {

    private static String clockType;
    private static Clock instance;
    private static String nodeId;

}
