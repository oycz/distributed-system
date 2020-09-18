package sys.task.app;

import sys.Server;
import sys.factory.MessageFactory;
import sys.message.Message;
import sys.routing.Broadcasting;
import sys.setting.Settings;
import sys.task.Task;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Synchronizer extends Task {

    private static Logger logger = Logger.getLogger(Synchronizer.class.getName());
    private int maxRound;

    public Synchronizer(Server server, String taskId, int maxRound) {
        super(server, Settings.SYNCHRONIZER_CLOCK_TYPE, taskId);
        this.maxRound = maxRound;
        logger.log(Level.INFO, "Synchronizer started, max round is " + maxRound);
    }

    @Override
    public void run() {
        Message message = null;
        Broadcasting broadcasting = new Broadcasting(context.neighbors, context, false, false);
        while((Integer) clock.clock < maxRound) {
            logger.log(Level.INFO, "Synchronizer " + taskId + ": Round " + clock.toString() + " started");
            Message forwardMessage = MessageFactory.appMessage(new String[0], clock, taskId, context.LOCALHOST, context.PORT);
            broadcasting.forward(forwardMessage);
            int neighborSize = context.neighbors.size();
            for(int curRoundMessages = 1; curRoundMessages <= neighborSize; curRoundMessages++) {
                try {
                    message = messageQueue.take();
                    if(message.clock.compareTo(this.clock) != 0) {
                        continue;
                    } else {
                        messageQueue.offer(message);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                logger.log(Level.INFO, "Received message from " + message.fromHost + " in round " + clock.toString());
            }
            logger.log(Level.INFO, "Round " + ((Integer)clock.clock + 1) + " finished");
            this.clock.increase();
        }
    }

    @Override
    public void step(Message message) {
    }
}
