package sys.message.app;

import sys.clock.Clock;
import sys.message.Message;

public class AppMessage extends Message {

    public AppMessage(String message, Clock clock, String metaTaskId) {
        super(message, clock, metaTaskId);
    }

    public AppMessage(String message, Clock clock, String metaTaskId, String fromHost, Integer fromPort) {
        super(message, metaTaskId, clock, fromHost, fromPort);
    }

    public AppMessage(String message, Clock clock, String metaTaskId, String fromHost, Integer fromPort, String toHost, Integer toPort) {
        super(message, metaTaskId, clock, fromHost, fromPort, toHost, toPort);
    }

}
