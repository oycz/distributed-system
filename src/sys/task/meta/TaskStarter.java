package sys.task.meta;

import sys.Server;
import sys.message.Message;
import sys.setting.Settings;
import sys.task.Task;
import sys.task.app.Synchronizer;

import java.util.Arrays;
import java.util.logging.Logger;


public class TaskStarter extends MetaTask {

    private Logger logger = Logger.getLogger(Task.class.getName());

    public TaskStarter(Server server) {
        super(server, Settings.TASK_STARTER_CLOCK_TYPE);
    }

    @Override
    public void step(Message message) {
//        System.out.println("Received task starter message..." + message.message + "..." + message.taskId);
        String[] strs = message.message.split(" ");
        String taskName = strs[0];
        String taskId = strs[strs.length - 1];
        String[] params = Arrays.copyOfRange(strs, 1, strs.length - 1);
        switch (taskName) {
            case "synchronizer": {
                int maxRound = 50;
                if(params.length >= 1) {
                    maxRound = Integer.parseInt(params[0]);
                }
                Synchronizer synchronizer = new Synchronizer(context, taskId, maxRound);
                context.newTask(synchronizer);
                break;
            }
            case "eccentricity": {

                break;
            }
        }
    }
}