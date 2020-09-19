package sys.task.meta;

import sys.Node;
import sys.Server;
import sys.message.Message;
import sys.setting.Setting;

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
        super(server, Setting.ACTIVE_CONNECTOR_CLOCK_TYPE, Setting.ACTIVE_CONNECTOR);
        logger.log(Level.INFO, "activeConnector run");
    }

    @Override
    public void run() {
            for(Node n: context.neighbors) {
                if(!checkAvail(n)) {
                    if(!tried.contains(n)) {
                        logger.log(Level.INFO, "Remote server " + n.hostname +" not open");
                        tried.add(n);
                    }
                    continue;
                }
                Socket socket;
                try {
                    socket = new Socket(n.hostname, n.port);
                    logger.log(Level.INFO, "Active connected " + socket.getInetAddress());
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

    private boolean checkAvail(Node n) {
        Socket s = null;
        try {
            s = new Socket();
            s.setReuseAddress(true);
            SocketAddress sa = new InetSocketAddress(n.hostname, Setting.HEARTBEAT_PORT);
            s.connect(sa, Setting.CHECK_AVAIL_TIMEOUT);
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
}