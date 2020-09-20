package sys.clock;

// TODO
public class TimeStampClock extends Clock<Long> {

    public TimeStampClock() {
        this.clock = System.currentTimeMillis();
    }

    public TimeStampClock(String nodeId) {
        this.clock = System.currentTimeMillis();
    }

    public TimeStampClock(Long timestamp) {
        this.clock = timestamp;
    }

    @Override
    public void increase() {

    }

    @Override
    public int compareTo(Clock o) {
        return 0;
    }
}
