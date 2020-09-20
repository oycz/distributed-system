package sys.clock;


import org.apache.log4j.Logger;

public class LamportClock extends Clock<Integer>  {

    private static final Logger logger = Logger.getLogger(LamportClock.class);

    public LamportClock() {
        this.clock = 0;
    }

    public LamportClock(String nodeId) {
        this.clock = 0;
    }

    public void increase() {
        ++this.clock;
        logger.debug("Lamport clock increased, current clock value is " + this.clock);
    }

    @Override
    public String toString() {
        return this.clock + "";
    }

    @Override
    public int compareTo(Clock o) {
        return clock - (Integer) o.clock;
//        return (Integer) o.clock - clock;
    }
}