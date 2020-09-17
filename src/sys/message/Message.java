package sys.message;

import sys.clock.Clock;

import java.io.Serializable;

public abstract class Message implements Serializable, Comparable<Message>, Cloneable {

    protected Clock clock;
    public String message;
    public String taskId;
    public String fromHost;
    public Integer fromPort;
    public String toHost;
    public Integer toPort;

    public Message(String message, Clock clock, String taskId) {
        this.message = message;
        this.clock = clock;
        this.taskId = taskId;
    }

    public Message(String message, String taskId, Clock clock, String fromHost, Integer fromPort) {
        this(message, clock, taskId);
        this.fromHost = fromHost;
        this.fromPort = fromPort;
    }

    public Message(String message, String taskId, Clock clock, String fromHost, Integer fromPort, String toHost, Integer toPort) {
        this(message, clock, taskId);
        this.fromHost = fromHost;
        this.fromPort = fromPort;
        this.toHost = toHost;
        this.toPort = toPort;
    }

    @Override
    public int compareTo(Message message) {
        return clock.compareTo(message.clock);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}