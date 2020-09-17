package sys.task.meta;


import sys.Server;
import sys.message.Message;
import sys.setting.Settings;
import sys.util.ConnectorUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PassiveConnector extends MetaTask {

    private static Logger logger = Logger.getLogger(PassiveConnector.class.getName());
    private ServerSocket ss;

    public PassiveConnector(Server server) {
        super(server, Settings.PASSIVE_CONNECTOR_CLOCK_TYPE);
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
//            synchronized (PassiveConnector.class) {
                if(context.nonConnectedNeighbors.size() == 0) {
                    return;
                }
                Socket socket;
                try {
                    socket = ss.accept();
                    for(Thread thread: context.taskThreads) {
                        System.out.println(thread);
                    }
//                    String socketAddress = socket.getInetAddress().toString();
//                    if(ConnectorUtil.containsNode(context.nonConnectedNeighbors, socketAddress)) {
//                        continue;
//                    }
                    logger.log(Level.INFO, "Passive connected by " + socket.getInetAddress());
                    ConnectorUtil.initSocket(socket, context);
                } catch (IOException e) {
                    e.printStackTrace();
                }
//            }
        }
    }

    @Override
    public void step(Message message) {

    }
}

