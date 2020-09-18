package sys.task.meta;

import sys.Server;
import sys.message.Message;
import sys.setting.Settings;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PassiveConnector extends MetaTask {

    private static Logger logger = Logger.getLogger(PassiveConnector.class.getName());
    private ServerSocket ss;

    public PassiveConnector(Server server) {
        super(server, Settings.PASSIVE_CONNECTOR_CLOCK_TYPE, Settings.PASSIVE_CONNECTOR);
        logger.log(Level.INFO, "passiveConnector run");
    }

    @Override
    public void run() {
        // start listening on port
        try {
            ss = new ServerSocket(context.PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        while(true) {
        for(int i = 0; i < context.neighbors.size(); i++) {
            Socket socket;
            try {
                socket = ss.accept();
                logger.log(Level.INFO, "Passive connected by " + socket.getInetAddress());
                Server.initSocket(socket, context);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void step(Message message) {

    }
}

