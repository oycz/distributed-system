package sys.task.app;

import sys.Server;
import sys.factory.MessageFactory;
import sys.message.Message;
import sys.routing.Broadcasting;
import sys.setting.Setting;
import sys.task.Task;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Synchronizer extends Task {

    private static final Logger logger = Logger.getLogger(Synchronizer.class.getName());
    private int maxRound;

    public Synchronizer(Server server, String taskId, int maxRound) {
        super(server, Setting.SYNCHRONIZER_CLOCK_TYPE, taskId);
        this.maxRound = maxRound;
        logger.log(Level.INFO, "Synchronizer started, max round is " + maxRound);
    }

    @Override
    public void run() {
        Message message;
        Broadcasting broadcasting = new Broadcasting(context.neighbors, context, false, false);
        while((Integer) clock.clock < maxRound) {
            logger.log(Level.INFO, "Synchronizer " + taskId + ": Round " + clock + " started");
            Message forwardMessage = MessageFactory.appMessage(new String[0], clock, taskId, context.LOCALHOST, context.PORT);
            broadcasting.forward(forwardMessage);
            int neighborSize = context.neighbors.size();
            for(int curRoundMessages = 1; curRoundMessages <= neighborSize; curRoundMessages++) {
                long startTime = System.currentTimeMillis();
                try {
                    while(true) {
                        message = messageQueue.take();
                        long time = System.currentTimeMillis();
                        if(time - startTime > 3000) {
                            logger.log(Level.SEVERE, "Stuck at round " + clock + ", message queue size is " + messageQueue.size());
                            logger.log(Level.SEVERE, "All messages in message queue: ");
                            for(Message m: messageQueue) {
                                System.out.println(m.message + "..." + m.clock.clock);
                            }
                            logger.log(Level.SEVERE, "Message at hand: ");
                            System.out.println(message.message + "..." + message.clock);
                            return;
                        }
                        if(message.clock.compareTo(this.clock) == 0) {
                            logger.log(Level.INFO, "Received message from " + message.fromHost + " in round " + clock
                                    + " message is " + message.message + ", clock is " + clock);
                            break;
                        } else {
                            messageQueue.offer(message);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            logger.log(Level.INFO, "Round " + clock + " finished");
            this.clock.increase();
        }
    }

    @Override
    public void step(Message message) {
    }
}
