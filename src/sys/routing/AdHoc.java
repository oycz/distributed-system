package sys.routing;

import sys.Server;
import sys.message.Message;

public class AdHoc extends RoutingAlgorithm {

    public AdHoc(Server server) {
        super(server);
    }

    @Override
    public void forward(Message message) {
        send(message);
    }
}
