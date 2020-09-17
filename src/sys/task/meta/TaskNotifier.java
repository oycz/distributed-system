package sys.task.meta;

import sys.Server;
import sys.clock.TimeStampClock;
import sys.message.Message;
import sys.message.meta.MetaTaskMessage;
import sys.routing_algorithm.Broadcasting;
import sys.setting.Settings;
import sys.task.Task;
import sys.util.UUIDUtil;

import java.util.logging.Logger;

public class TaskNotifier extends MetaTask {

    private Logger logger = Logger.getLogger(Task.class.getName());

    public TaskNotifier(Server server) {
        super(server, Settings.TASK_NOTIFIER_CLOCK_TYPE);
    }

    @Override
    public void step(Message message) {
        System.out.println("notify " + message.message);
        String newTaskId = UUIDUtil.randomUUID();
        MetaTaskMessage notify = new MetaTaskMessage(message.message + " " + newTaskId, Settings.TASK_STARTER, new TimeStampClock(), context.LOCALHOST, context.PORT);
//      SyncrhonizerNotifyMessage notify = new SyncrhonizerNotifyMessage("", taskID, new LamortClock(), context.LOCALHOST, context.PORT);
//        for(String k: context.sockets.keySet()) {
//            System.out.println(k + "..." + context.sockets.get(k));
//        }
        Broadcasting broadcasting = new Broadcasting(context.neighbors, context, true);
        broadcasting.forward(notify);
    }
}
