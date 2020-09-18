package sys.routing;

import sys.Server;
import sys.message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class RoutingAlgorithm {

    protected static Server context;
    private static final Logger logger = Logger.getLogger(RoutingAlgorithm.class.getName());
    private static final Map<String, ObjectOutputStream> outs = new HashMap<>();
    private static final Map<String, ObjectInputStream> ins = new HashMap<>();

    public RoutingAlgorithm(Server server) {
        if(context == null) {
            this.context = server;
        }
    }

    public abstract void forward(Message message);

    // Can optimize using message queue (TODO)
    public static synchronized void send(Message message) {
        if(message.toHost == null || message.toHost.equals(context.LOCALHOST)) {
            context.offerMessage(message);
            return;
        }
        ObjectOutputStream out;
        try {
            out = getOutputStream(message.toHost);
            if (out == null) {
                logger.log(Level.INFO, "Channel hasn't been initialized");
            } else {
                out.writeObject(message);
                logger.log(Level.INFO, "Sent " + message.toHost);
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.log(Level.SEVERE, "Message send failure");
        }
    }

    private synchronized static ObjectOutputStream getOutputStream(String toHost) {
        if(!outs.containsKey(toHost)) {
            ObjectOutputStream out = null;
            try {
                out = new ObjectOutputStream(context.sockets.get(toHost).getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            outs.put(toHost, out);
        }
        return outs.get(toHost);
    }
}
