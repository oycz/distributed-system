package sys.util;

import sys.Node;

import java.util.Set;

public class ConnectorUtil {

    public static void removeNode(Set<Node> nodes, String address) {
        for(Node n: nodes) {
            if(n.hostname.equals(address)) {
                nodes.remove(n);
                break;
            }
        }
    }

    public static boolean containsNode(Set<Node> nodes, String address) {
        for(Node n: nodes) {
            if(n.hostname.equals(address)) {
                return true;
            }
        }
        return false;
    }

}
