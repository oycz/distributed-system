package sys.message;

import java.util.Comparator;

public class MessageComparator implements Comparator<Message> {

    @Override
    public int compare(Message o1, Message o2) {
        int cmp = o1.clock.compareTo(o2.clock.clock);
        return cmp;
    }
}
