package sys.task;

import sys.Server;
import sys.clock.Clock;
import sys.factory.ClockFactory;
import sys.message.Message;
import sys.util.UUIDUtil;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public abstract class Task implements Runnable {

    public String taskId;
    public Clock clock;
    public Server context;
    protected BlockingQueue<Message> messageQueue;

    public Task(Server server, String clockType) {
        this.context = server;
        this.clock = ClockFactory.newClock(clockType);
        this.taskId = UUIDUtil.randomUUID();
        this.messageQueue = new PriorityBlockingQueue<>();
    }

    public Task(Server server, String clockType, String taskId) {
        this.context = server;
        this.clock = ClockFactory.newClock(clockType);
        this.taskId = taskId;
        this.messageQueue = new PriorityBlockingQueue<>();
    }

    public Task(Server server, String clockType, BlockingQueue<Message> messageQueue, String taskId) {
        this.context = server;
        this.clock = ClockFactory.newClock(clockType);
        this.taskId = taskId;
        this.messageQueue = messageQueue;
    }

    public void offerMessage(Message message) {
        this.messageQueue.offer(message);
    }

    @Override
    public void run() {
        Message message = null;
        while(true) {
            try {
                message = messageQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            step(message);
        }
    }

    public abstract void step(Message message);
}
