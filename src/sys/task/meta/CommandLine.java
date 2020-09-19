package sys.task.meta;

import sys.Server;
import sys.factory.MessageFactory;
import sys.message.Message;
import sys.setting.Setting;
import sys.util.CommandChecker;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandLine extends MetaTask {

    private static final Logger logger = Logger.getLogger(CommandLine.class.getName());

    public CommandLine(Server server) {
        super(server, Setting.COMMAND_LINE_CLOCK_TYPE, Setting.COMMAND_LINE);
    }

    @Override
    public void run() {
        Scanner in = new Scanner(System.in);
        String line;
        while(true) {
            line = String.join(" ", in.nextLine().split("\\s+"));
            String[] strs = line.split(" ");
            if(line.equals("help")) {
                logger.log(Level.INFO, "All possible commands: " + Setting.COMMANDS.toString());
            } else if(Setting.COMMANDS.contains(strs[0])) {
                String warning = CommandChecker.check(strs);
                if(warning != null) {
                    logger.log(Level.WARNING, warning);
                } else {
                    context.offerMessage(MessageFactory.taskStartNotifierMessage(strs));
                }
            } else {
                context.offerMessage(MessageFactory.echoerMessage(line));
            }
        }
    }

    @Override
    public void step(Message message) {

    }
}
