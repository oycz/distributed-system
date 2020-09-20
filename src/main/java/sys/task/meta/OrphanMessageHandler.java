package sys.task.meta;

import org.apache.log4j.Logger;
import sys.Server;
import sys.message.Message;
import sys.setting.Setting;

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;

public class OrphanMessageHandler extends MetaTask {

    private static final Logger logger = Logger.getLogger(OrphanMessageHandler.class);

    public OrphanMessageHandler(Server server) {
        super(server, Setting.ORPHAN_MESSAGE_HANDLER_CLOCK_TYPE, new LinkedBlockingQueue<>(), Setting.ORPHAN_MESSAGE_HANDLER);
    }

    @Override
    public void run() {
        while(true) {
            Iterator<Message> iterator = messageQueue.iterator();
            while(iterator.hasNext()) {
                Message message = iterator.next();
                step(message);
            }
        }
    }

    @Override
    protected Message pre() {
        return null;
    }

    @Override
    protected void step(Message message) {
        if(context.hasTask(message.taskId)) {
            messageQueue.remove(message);
            context.offerMessageToTask(message, message.taskId);
            logger.debug("Orphan message handler offered message to task " + message.taskId);
        }
    }

}
