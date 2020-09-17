package sys.task.meta;

import sys.Server;
import sys.message.Message;
import sys.setting.Settings;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Echoer extends MetaTask {

    private Logger logger = Logger.getLogger(Echoer.class.getName());

    public Echoer(Server server) {
        super(server, Settings.ECHOER_CLOCK_TYPE);
    }

    @Override
    public void step(Message message) {
        logger.log(Level.INFO, message.message);
    }
}
