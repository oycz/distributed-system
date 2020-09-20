package sys.task.meta;

import org.apache.log4j.Logger;
import sys.Server;
import sys.message.Message;
import sys.setting.Setting;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PassiveConnector extends MetaTask {

    private static Logger logger = Logger.getLogger(PassiveConnector.class);
    private ServerSocket ss;

    public PassiveConnector(Server server) {
        super(server, Setting.PASSIVE_CONNECTOR_CLOCK_TYPE, Setting.PASSIVE_CONNECTOR);
        logger.info( "Passive connection activated");
    }

    @Override
    public void run() {
        // start listening on port
        try {
            ss = new ServerSocket(context.PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(true) {
            Socket socket;
            try {
                socket = ss.accept();
                logger.info("Passive connected by " + socket.getInetAddress());
                Server.initSocket(socket, context);
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

