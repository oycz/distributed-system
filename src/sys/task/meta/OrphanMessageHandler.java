package sys.task.meta;

import sys.Server;
import sys.message.Message;
import sys.setting.Settings;

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;

public class OrphanMessageHandler extends MetaTask {

    public OrphanMessageHandler(Server server) {
        super(server, Settings.ORPHAN_MESSAGE_HANDLER_CLOCK_TYPE, new LinkedBlockingQueue<>(), Settings.ORPHAN_MESSAGE_HANDLER);
    }

    @Override
    public void run() {
        Iterator<Message> iterator = messageQueue.iterator();
        while(true) {
            if(messageQueue.isEmpty()) {
                Thread.yield();
            }
            while(iterator.hasNext()) {
                Message message = iterator.next();
                step(message);
            }
        }
    }

    @Override
    public void step(Message message) {
        if(context.idToTask.containsKey(message.taskId)) {
            context.idToTask.get(message.taskId).offer(message);
        }
    }
}
