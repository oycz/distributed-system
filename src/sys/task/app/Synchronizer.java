package sys.task.app;

import sys.Server;
import sys.message.Message;
import sys.setting.Settings;
import sys.task.Task;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Synchronizer extends Task {

    private static Logger logger = Logger.getLogger(Synchronizer.class.getName());
    private int round;
    private int maxRound;

    public Synchronizer(Server server, String taskId, int maxRound) {
        super(server, Settings.SYNCHRONIZER_CLOCK_TYPE, taskId);
        this.round = 0;
        this.maxRound = maxRound;
        logger.log(Level.INFO, "Synchronizer started, max round is " + maxRound);
    }

    @Override
    public void step(Message message) {
        // TODO
    }
}
