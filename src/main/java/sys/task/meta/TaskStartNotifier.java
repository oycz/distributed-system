package sys.task.meta;

import org.apache.log4j.Logger;
import sys.Server;
import sys.factory.MessageFactory;
import sys.message.Message;
import sys.routing.Broadcasting;
import sys.setting.Setting;
import sys.task.Task;

public class TaskStartNotifier extends MetaTask {

    private Logger logger = Logger.getLogger(Task.class);

    public TaskStartNotifier(Server server) {
        super(server, Setting.TASK_START_NOTIFIER_CLOCK_TYPE, Setting.TASK_START_NOTIFIER);
    }

    @Override
    protected Message pre() {
        return null;
    }

    @Override
    public synchronized void step(Message message) {
        String[] commands = message.message.split(" ");
        String taskId = commands[commands.length - 1];
        if(context.hasTask(taskId)) {
            logger.debug("Task " + taskId + " already started");
            return;
        }
        logger.debug("Starting task locally...");
        Message selfStarter = MessageFactory.taskStarterMessage(message.message);
        context.offerMessage(selfStarter);

        logger.debug("Notifying neighbors to start task...");
        Broadcasting broadcasting = new Broadcasting(context.neighbors, context, false);
        broadcasting.forward(message);
    }
}
