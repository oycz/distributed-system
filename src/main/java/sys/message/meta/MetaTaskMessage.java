package sys.message.meta;


import sys.clock.Clock;
import sys.message.Message;

public class MetaTaskMessage extends Message {

    public MetaTaskMessage(String message, Clock clock, String metaTaskId) {
        super(message, clock, metaTaskId);
    }

    public MetaTaskMessage(String message, Clock clock, String metaTaskId, String fromHost, Integer fromPort) {
        super(message, metaTaskId, clock, fromHost, fromPort);
    }

    public MetaTaskMessage(String message, Clock clock, String metaTaskId, String fromHost, Integer fromPort, String toHost, Integer toPort) {
        super(message, metaTaskId, clock, fromHost, fromPort, toHost, toPort);
    }

}
