package sys.task.app.syncrhonizer;

import org.apache.log4j.Logger;
import sys.Server;
import sys.factory.MessageFactory;
import sys.message.Message;
import sys.routing.Broadcasting;
import sys.setting.Setting;
import sys.task.Task;


public class Synchronizer extends Task {

    private static final Logger logger = Logger.getLogger(Synchronizer.class);
    protected int maxRound;

    public Synchronizer(Server server, String taskId, int maxRound) {
        super(server, Setting.SYNCHRONIZER_CLOCK_TYPE, taskId);
        this.maxRound = maxRound;

        logger.info("Synchronizer " + taskId + " started, max round is " + maxRound);
    }

    @Override
    public void run() {
        Long start = System.currentTimeMillis();
        Message message;
        Broadcasting broadcasting = new Broadcasting(context.neighbors, context, false, false);
        while((Integer) clock.clock < maxRound) {
            logger.info("Synchronizer " + taskId + ": Round " + clock + " started");
            Message forwardMessage = pre();
            broadcasting.forward(forwardMessage);
            int neighborSize = context.neighbors.size();
            for(int curRoundMessages = 1; curRoundMessages <= neighborSize; curRoundMessages++) {
                try {
                    while(true) {
                        message = messageQueue.take();
                        if(message.clock.compareTo(this.clock) == 0) {
                            logger.debug( "Received message from " + message.fromHost + " in round " + clock
                                    + " message is " + message.message + ", clock is " + clock);
                            step(message);
                            break;
                        } else {
                            messageQueue.offer(message);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            logger.info("Round " + clock + " finished");
            this.clock.increase();
        }
        Long end = System.currentTimeMillis();
        logger.info( "Synchronizer completed, costs " + ((double) (end - start) / 1000) + " seconds in total");
    }

    @Override
    protected Message pre() {
        return MessageFactory.appMessage(new String[0], clock, taskId, context.LOCALHOST, context.PORT);
    }

    @Override
    protected void step(Message message) {
    }
}
