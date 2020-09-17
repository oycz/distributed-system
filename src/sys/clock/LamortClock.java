package sys.clock;

public class LamortClock extends Clock<Integer>  {

    private Integer lamportClock;

    public LamortClock() {
        this.lamportClock = 0;
    }

    public LamortClock(String nodeId) {
        this.lamportClock = 0;
    }

    @Override
    public int compareTo(Integer o) {
        return lamportClock - o;
    }
}