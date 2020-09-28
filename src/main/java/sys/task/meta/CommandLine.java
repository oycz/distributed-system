package sys.task.meta;

import org.apache.log4j.Logger;
import sys.Server;
import sys.factory.MessageFactory;
import sys.message.Message;
import sys.setting.Setting;

public class CommandLine extends MetaTask {

    private static final Logger logger = Logger.getLogger(CommandLine.class);

    public CommandLine(Server server) {
        super(server, Setting.COMMAND_LINE_CLOCK_TYPE, Setting.COMMAND_LINE);
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String[] line = {"eccentricity"};
        context.offerMessage(MessageFactory.taskStartNotifierMessage(line));
    }

    @Override
    protected Message pre() {
        return null;
    }

    @Override
    protected void step(Message message) {

    }
}
