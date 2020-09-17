package sys.routing_algorithm;

import sys.Server;
import sys.message.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class RoutingAlgorithm {

    protected Server context;
    private static final Logger logger = Logger.getLogger(RoutingAlgorithm.class.getName());

    public RoutingAlgorithm(Server server) {
        this.context = server;
    }

    public abstract void forward(Message message);

    public void send(Message message) {
        if(message.toHost == null || message.toHost.equals(context.LOCALHOST)) {
            context.messageQueue.offer(message);
            return;
        }
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(context.sockets.get(message.toHost).getOutputStream());
            if (out == null) {
                logger.log(Level.INFO, "Channel hasn't been initialized");
            } else {
                out.writeObject(message);
                logger.log(Level.INFO, "sent " + message.toHost);
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.log(Level.SEVERE, "Message send failure");
        }
    }
}
