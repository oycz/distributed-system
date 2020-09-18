package sys.clock;

public class LamortClock extends Clock<Integer>  {

    public LamortClock() {
        this.clock = 0;
    }

    public LamortClock(String nodeId) {
        this.clock = 0;
    }

    public void increase() {
        ++this.clock;
    }

    @Override
    public String toString() {
        return this.clock + "";
    }

    @Override
    public int compareTo(Clock o) {
        return clock - (Integer) o.clock;
    }
}