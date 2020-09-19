package sys.task.meta;

import sys.Server;
import sys.message.Message;
import sys.setting.Setting;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Echoer extends MetaTask {

    private Logger logger = Logger.getLogger(Echoer.class.getName());

    public Echoer(Server server) {
        super(server, Setting.ECHOER_CLOCK_TYPE, Setting.ECHOER);
    }

    @Override
    public void step(Message message) {
        logger.log(Level.INFO, message.message);
    }
}
