package sys.task.meta;

import sys.Node;
import sys.Server;
import sys.message.Message;
import sys.setting.Settings;
import sys.util.ConnectorUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActiveConnector extends MetaTask {

    private static Logger logger = Logger.getLogger(ActiveConnector.class.getName());
    private static Set<Node> tried = new HashSet<>();


    public ActiveConnector(Server server) {
        super(server, Settings.ACTIVE_CONNECTOR_CLOCK_TYPE);
    }

    @Override
    public void run() {
//        while(true) {
//            if(context.nonConnectedNeighbors.size() == 0) {
//                return;
//            }
            for(Node n: context.nonConnectedNeighbors) {
                if(!checkAvail(n)) {
                    if(!tried.contains(n)) {
                        logger.log(Level.INFO, "Remote server " + n.hostname + ":" + n.port +" not open");
                        tried.add(n);
                    }
                    continue;
                }
                Socket socket;
                try {
                    socket = new Socket(n.hostname, n.port);
                    logger.log(Level.INFO, "Active connected " + socket.getInetAddress());
                    ConnectorUtil.initSocket(socket, context);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
//        }
    }

    private boolean checkAvail(Node n) {
        Socket s = null;
        try {
            s = new Socket();
            s.setReuseAddress(true);
            SocketAddress sa = new InetSocketAddress(n.hostname, n.port);
            s.connect(sa, Settings.CHECK_AVAIL_TIMEOUT);
        } catch (IOException e) {
            return false;
        } finally {
            try {
                s.close();
            } catch (IOException e) {
            }
        }
        return true;
    }

    @Override
    public void step(Message message) {

    }

}