package sys.task.meta;

import sys.Server;
import sys.factory.MessageFactory;
import sys.message.Message;
import sys.routing.Broadcasting;
import sys.setting.Settings;
import sys.task.Task;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TaskStartNotifier extends MetaTask {

    private Logger logger = Logger.getLogger(Task.class.getName());

    public TaskStartNotifier(Server server) {
        super(server, Settings.TASK_START_NOTIFIER_CLOCK_TYPE, Settings.TASK_START_NOTIFIER);
    }

    @Override
    public void step(Message message) {
        String[] commands = message.message.split(" ");
        String taskId = commands[commands.length - 1];
        if(context.hasTask(taskId)) {
            logger.log(Level.INFO, "Task " + taskId + " already started");
            return;
        }
        logger.log(Level.INFO, "Starting task locally...");
        Message selfStarter = MessageFactory.taskStarterMessage(message.message);
        context.offerMessage(selfStarter);

        logger.log(Level.INFO, "Notifying neighbors to start task...");
        Broadcasting broadcasting = new Broadcasting(context.neighbors, context, false);
        broadcasting.forward(message);
    }
}
