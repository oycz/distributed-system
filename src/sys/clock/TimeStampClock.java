package sys.clock;

public class TimeStampClock extends Clock<Long> {

    long timestampClock = 0;

    public TimeStampClock() {
        this.timestampClock = System.currentTimeMillis();
    }

    public TimeStampClock(String nodeId) {
        this.timestampClock = System.currentTimeMillis();
    }

    public TimeStampClock(Long timestamp) {
        this.timestampClock = timestamp;
    }
    @Override
    public int compareTo(Long l) {
        return (int) (timestampClock - l);
    }
}
