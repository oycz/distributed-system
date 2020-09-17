package sys.task.app;

import sys.Server;
import sys.message.Message;
import sys.setting.Settings;
import sys.task.Task;

public class Synchronizer extends Task {

    private int round;
    private int maxRound;

    public Synchronizer(Server server, String taskId, int maxRound) {
        super(server, Settings.SYNCHRONIZER_CLOCK_TYPE, taskId);
        this.round = 0;
        this.maxRound = maxRound;
    }

    @Override
    public void step(Message message) {
        // TODO

    }
}
