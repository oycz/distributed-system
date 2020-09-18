package sys.task.meta;

import sys.Server;
import sys.message.Message;
import sys.task.Task;

import java.util.concurrent.BlockingQueue;

public abstract class MetaTask extends Task {

    public MetaTask(Server server, String clockType, String taskId) {
        super(server, clockType, taskId);
    }

    public MetaTask(Server server, String clockType, BlockingQueue<Message> messageQueue, String taskId) {
        super(server, clockType, messageQueue, taskId);
    }

}
