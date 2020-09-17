package sys.routing_algorithm;

import sys.Node;
import sys.Server;
import sys.message.Message;

import java.util.ArrayList;
import java.util.List;

public class Broadcasting extends RoutingAlgorithm {

    private List<Node> targetNodes;
    private boolean sendSelf;

    public Broadcasting(List<Node> targetNodes, Server server, boolean sendSelf) {
        super(server);
        this.targetNodes = targetNodes;
        this.sendSelf = sendSelf;
    }

    @Override
    public void forward(Message message) {
        List<Node> targetNodes = new ArrayList<>(this.targetNodes);
        if(this.sendSelf) {
            targetNodes.add(new Node(context.LOCALHOST, context.PORT));
        }
        for(Node node: targetNodes) {
            Message copy = null;
            try {
                System.out.println(message.clone());
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
