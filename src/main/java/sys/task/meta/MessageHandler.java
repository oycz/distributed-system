package sys.task.meta;

import org.apache.log4j.Logger;
import sys.Server;
import sys.message.Message;
import sys.setting.Setting;


public class MessageHandler extends MetaTask {

    private static final Logger logger = Logger.getLogger(MessageHandler.class.getName());

    public MessageHandler(Server server) {
        super(server, Setting.MESSAGE_HANDLER_CLOCK_TYPE, Setting.MESSAGE_HANDLER);
    }

    @Override
    public void run() {
        while(true) {
            step(context.takeMessage());
        }
    }

    @Override
    protected Message pre() {
        return null;
    }

    @Override
    protected void step(Message message) {
        String taskIdToOffer = message.taskId;
        if(context.hasTask(taskIdToOffer)) {
            context.offerMessageToTask(message, taskIdToOffer);
            logger.debug( "Offered message to task " + taskIdToOffer);
        } else {
            context.offerMessageToTask(message, Setting.ORPHAN_MESSAGE_HANDLER);
            logger.debug("Offered orphan message to orphan message handler");
        }
    }
}