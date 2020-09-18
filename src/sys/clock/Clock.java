package sys.clock;

import java.io.Serializable;

public abstract class Clock<T> implements Comparable<Clock>, Serializable {

    public T clock;
    private static String clockType;
    private static Clock instance;
    private static String nodeId;

    public abstract void increase();
}
