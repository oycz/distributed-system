package sys.task.meta;

import sys.Server;
import sys.clock.TimeStampClock;
import sys.message.Message;
import sys.message.meta.MetaTaskMessage;
import sys.setting.Settings;

import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandLine extends MetaTask {

    private static final Logger logger = Logger.getLogger(CommandLine.class.getName());

    public CommandLine(Server server) {
        super(server, Settings.COMMAND_LINE_CLOCK_TYPE);
    }

    @Override
    public void run() {
        String taskStarterId = Settings.TASK_STARTER;
        String echoerId = Settings.ECHOER;
        String taskNotifierId = Settings.TASK_NOTIFIER;
        BlockingQueue<Message> messageQueue = context.messageQueue;
        Scanner in = new Scanner(System.in);
        String line;
        while(true) {
            line = String.join(" ", in.nextLine().split("\\s+"));
            String[] strs = line.split(" ");
            if(line.equals("help")) {
                logger.log(Level.INFO, "All possible commands: " + Settings.COMMANDS.toString());
            } else if(Settings.COMMANDS.contains(strs[0])) {
                messageQueue.add(new MetaTaskMessage(line, taskNotifierId, new TimeStampClock(System.currentTimeMillis()),
                        context.LOCALHOST, context.PORT , context.LOCALHOST, context.PORT));
            } else {
                messageQueue.add(new MetaTaskMessage(line, echoerId, new TimeStampClock(System.currentTimeMillis()),
                        context.LOCALHOST, context.PORT , context.LOCALHOST, context.PORT));
            }
        }
    }

    @Override
    public void step(Message message) {

    }
}
