package sys.task.meta;

import sys.Server;
import sys.message.Message;
import sys.setting.Setting;
import sys.task.Task;
import sys.task.app.syncrhonizer.EccentricityCalculator;
import sys.task.app.syncrhonizer.Synchronizer;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TaskStarter extends MetaTask {

    private Logger logger = Logger.getLogger(Task.class.getName());

    public TaskStarter(Server server) {
        super(server, Setting.TASK_STARTER_CLOCK_TYPE, Setting.TASK_STARTER);
    }

    @Override
    protected Message pre() {
        return null;
    }

    @Override
    protected void step(Message message) {
        String[] strs = message.message.split(" ");
        String taskName = strs[0];
        String taskId = strs[strs.length - 1];
        if(context.hasTask(taskId)) {
            return;
        }
        logger.log(Level.INFO, "Starting task" + taskName + ", task id is " + taskId + "...");
        String[] params = Arrays.copyOfRange(strs, 1, strs.length - 1);

        switch (taskName) {
            case "synchronizer": {
                int maxRound = Integer.parseInt(params[0]);
                Synchronizer synchronizer = new Synchronizer(context, taskId, maxRound);
                context.newTask(synchronizer);
                break;
            }
            case "eccentricity": {
                int maxRound = context.NET_NODE_NUMBER;
                EccentricityCalculator eccentricityCalculator = new EccentricityCalculator(context, taskId, maxRound);
                context.newTask(eccentricityCalculator);
                break;
            }
        }
    }
}
