package sys.routing;

import sys.Node;
import sys.Server;
import sys.message.Message;

import java.util.ArrayList;
import java.util.List;

public class Broadcasting extends Routing {

    private List<Node> targetNodes;
    private boolean sendSelf = false;
    private boolean avoidMessageSource = true;

    public Broadcasting(List<Node> targetNodes, Server server) {
        super(server);
        this.targetNodes = targetNodes;
    }

    public Broadcasting(List<Node> targetNodes, Server server, boolean sendSelf) {
        super(server);
        this.targetNodes = targetNodes;
        this.sendSelf = sendSelf;
    }

    public Broadcasting(List<Node> targetNodes, Server server, boolean sendSelf, boolean avoidMessageSource) {
        super(server);
        this.targetNodes = targetNodes;
        this.sendSelf = sendSelf;
        this.avoidMessageSource = avoidMessageSource;
    }

    @Override
    public void forward(Message message) {
        List<Node> targetNodes = new ArrayList<>(this.targetNodes);
        if(this.sendSelf) {
            targetNodes.add(new Node(context.LOCALHOST, context.PORT));
        }
        for(Node node: targetNodes) {
            if(avoidMessageSource && node.hostname.equals(message.fromHost)) {
                continue;
            }
            Message copy = null;
            try {
                copy = (Message) message.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            copy.toHost = node.hostname;
            copy.toPort = node.port;
            send(copy);
        }
    }
}
