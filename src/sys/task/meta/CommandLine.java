package sys.task.meta;

import sys.Server;
import sys.factory.MessageFactory;
import sys.message.Message;
import sys.setting.Settings;
import sys.util.CommandChecker;

import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandLine extends MetaTask {

    private static final Logger logger = Logger.getLogger(CommandLine.class.getName());

    public CommandLine(Server server) {
        super(server, Settings.COMMAND_LINE_CLOCK_TYPE, Settings.COMMAND_LINE);
    }

    @Override
    public void run() {
        BlockingQueue<Message> messageQueue = context.messageQueue;
        Scanner in = new Scanner(System.in);
        String line;
        while(true) {
            line = String.join(" ", in.nextLine().split("\\s+"));
            String[] strs = line.split(" ");
            if(line.equals("help")) {
                logger.log(Level.INFO, "All possible commands: " + Settings.COMMANDS.toString());
            } else if(Settings.COMMANDS.contains(strs[0])) {
                String warning = CommandChecker.check(strs);
                if(warning != null) {
                    logger.log(Level.WARNING, warning);
                } else {
                    messageQueue.add(MessageFactory.taskStartNotifierMessage(strs));
                }
            } else {
                messageQueue.add(MessageFactory.echoerMessage(line));
            }
        }
    }

    @Override
    public void step(Message message) {

    }
}
