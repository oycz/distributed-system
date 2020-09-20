package sys.task.meta;

import org.apache.log4j.Logger;
import sys.Server;
import sys.message.Message;
import sys.setting.Setting;


public class Echoer extends MetaTask {

    private Logger logger = Logger.getLogger(Echoer.class);

    public Echoer(Server server) {
        super(server, Setting.ECHOER_CLOCK_TYPE, Setting.ECHOER);
    }

    @Override
    protected Message pre() {
        return null;
    }

    @Override
    protected void step(Message message) {
        logger.info(message.message);
    }
}
