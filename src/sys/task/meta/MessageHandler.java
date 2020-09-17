package sys.task.meta;

import sys.Server;
import sys.message.Message;
import sys.message.meta.MetaTaskMessage;
import sys.setting.Settings;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

public class MessageHandler extends MetaTask {

    private static Logger logger = Logger.getLogger(MessageHandler.class.getName());

    public MessageHandler(Server server) {
        super(server, Settings.MESSAGE_HANDLER_CLOCK_TYPE, server.messageQueue);
    }

    @Override
    public void run() {
        BlockingQueue<Message> messageQueue = context.messageQueue;
        while(true) {
            Message message = null;
            step(message);
        }
    }

    @Override
    public void step(Message message) {
        String orphanMessageHandlerId = context.metaTaskId.get(Settings.ORPHAN_MESSAGE_HANDLER);
        try {
            message = messageQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String taskIdToOffer;
        if(message instanceof MetaTaskMessage) {
            String metaTaskId = message.taskId;
            taskIdToOffer = context.metaTaskId.get(metaTaskId);
        } else {
            taskIdToOffer = message.taskId;
        }
        if(context.idToTask.containsKey(taskIdToOffer)) {
            context.idToTask.get(taskIdToOffer).offer(message);
        } else {
            context.idToTask.get(orphanMessageHandlerId).offer(message);
        }
    }
}