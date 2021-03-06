package sys.routing;

import org.apache.log4j.Logger;
import sys.Server;
import sys.message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public abstract class Routing {

    protected static Server context;
    private static final Logger logger = Logger.getLogger(Routing.class.getName());
    private static final Map<String, ObjectOutputStream> outs = new HashMap<>();
    private static final Map<String, ObjectInputStream> ins = new HashMap<>();

    public Routing(Server server) {
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
                logger.fatal( "Channel hasn't been initialized");
            } else {
                out.writeObject(message);
                out.reset();
                logger.debug( "Sent message to " + message.toHost + ", clock is " + message.clock + ", message is " + message.message);
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error( "Message send failure");
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
