package sys.factory;

import sys.clock.Clock;
import sys.clock.TimeStampClock;
import sys.message.Message;
import sys.message.app.AppMessage;
import sys.message.meta.MetaTaskMessage;
import sys.setting.Setting;
import sys.util.ArrayUtil;
import sys.util.UUIDUtil;

import java.util.logging.Logger;

public class MessageFactory {

    private static final Logger logger = Logger.getLogger(MessageFactory.class.getName());

    // message in message of taskStartNotifier: [task_type  [args...] task_ID(generated there if empty)]
    public static Message taskStartNotifierMessage(String[] args) {
        return taskStartNotifierMessage(args, null);
    }

    public static Message taskStartNotifierMessage(String[] args, String taskId) {
        if(taskId == null) {
            taskId = UUIDUtil.randomUUID();
        }
        String[] lineArgs = ArrayUtil.addAll(args, new String[] {taskId});
        return new MetaTaskMessage(String.join(" ", lineArgs), new TimeStampClock(System.currentTimeMillis()), Setting.TASK_START_NOTIFIER);
    }

    public static Message echoerMessage(String line) {
        return new MetaTaskMessage(line, new TimeStampClock(System.currentTimeMillis()), Setting.ECHOER);
    }

    public static Message taskStarterMessage(String line) {
        return new MetaTaskMessage(line, new TimeStampClock(), Setting.TASK_STARTER);
    }

    public static Message taskStarterMessage(String[] args) {
        return new MetaTaskMessage(String.join(" ", args), new TimeStampClock(), Setting.TASK_STARTER);
    }

    public static Message appMessage(String line, Clock clock, String taskId, String fromHost, Integer fromPort) {
        return new AppMessage(line, clock, taskId, fromHost, fromPort);
    }

    public static Message appMessage(String[] args, Clock clock, String taskId, String fromHost, Integer fromPort) {
        return new AppMessage(String.join(" ", args), clock, taskId, fromHost, fromPort);
    }
}
