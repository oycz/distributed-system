package sys.task.meta;

import sys.Server;
import sys.message.Message;
import sys.setting.Setting;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Logger;

public class Heartbeat extends MetaTask {

    private static Logger logger = Logger.getLogger(Heartbeat.class.getName());
    private ServerSocket ss;

    public Heartbeat(Server server) {
        super(server, Setting.HEARTBEAT_CLOCK_TYPE, null, Setting.HEARTBEAT);
    }

    @Override
    public void run() {
        try {
            ss = new ServerSocket(Setting.HEARTBEAT_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(true) {
            try {
                ss.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected Message pre() {
        return null;
    }

    @Override
    protected void step(Message message) {

    }
}
