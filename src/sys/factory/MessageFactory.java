package sys.factory;

import sys.clock.TimeStampClock;
import sys.message.Message;
import sys.message.meta.MetaTaskMessage;
import sys.setting.Settings;
import sys.util.ArrayUtil;
import sys.util.UUIDUtil;

public class MessageFactory {

    // message in message of taskStartNotifier: [task_type  [args...] task_ID(generated there if empty)]
    public static Message taskStartNotifierMessage(String[] args) {
        return taskStartNotifierMessage(args, null);
    }

    public static Message taskStartNotifierMessage(String[] args, String taskId) {
        if(taskId == null) {
            taskId = UUIDUtil.randomUUID();
        }
        String[] lineArgs = ArrayUtil.addAll(args, new String[] {taskId});
        return new MetaTaskMessage(String.join(" ", lineArgs), new TimeStampClock(System.currentTimeMillis()), Settings.TASK_START_NOTIFIER);
    }

    public static Message echoerMessage(String line) {
        return new MetaTaskMessage(line, new TimeStampClock(System.currentTimeMillis()), Settings.ECHOER);
    }

    public static Message taskStarterMessage(String line) {
        return new MetaTaskMessage(line, new TimeStampClock(), Settings.TASK_STARTER);
    }

    public static Message taskStarterMessage(String[] args) {
        return new MetaTaskMessage(String.join(" ", args), new TimeStampClock(), Settings.TASK_STARTER);
    }
}
