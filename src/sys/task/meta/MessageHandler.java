package sys.task.meta;

import sys.Server;
import sys.message.Message;
import sys.setting.Settings;

import java.util.logging.Logger;

public class MessageHandler extends MetaTask {

    private static Logger logger = Logger.getLogger(MessageHandler.class.getName());

    public MessageHandler(Server server) {
        super(server, Settings.MESSAGE_HANDLER_CLOCK_TYPE, Settings.MESSAGE_HANDLER);
    }

    @Override
    public void run() {
        while(true) {
            step(context.takeMessage());
        }
    }

    @Override
    public void step(Message message) {
        String taskIdToOffer = message.taskId;
        if(context.hasTask(taskIdToOffer)) {
            context.offerMessageToTask(message, taskIdToOffer);
        } else {
            context.offerMessageToTask(message, Settings.ORPHAN_MESSAGE_HANDLER);
        }
    }
}