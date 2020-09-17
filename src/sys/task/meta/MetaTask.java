package sys.task.meta;

import sys.Server;
import sys.message.Message;
import sys.task.Task;

import java.util.concurrent.BlockingQueue;

public abstract class MetaTask extends Task {
    public MetaTask(Server server, String clockType) {
        super(server, clockType);
    }

    public MetaTask(Server server, String clockType, BlockingQueue<Message> messageQueue) {
        super(server, clockType, messageQueue);
    }

    public MetaTask(Server server, String clockType, String taskId) {
        super(server, clockType, taskId);
    }
}