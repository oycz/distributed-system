package sys.util;

import sys.Node;
import sys.Server;
import sys.task.meta.PortListener;

import java.net.Socket;
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

    public synchronized static void initSocket(Socket socket, Server context) {
        String socketAddress = socket.getInetAddress().getHostName();
//        if(context.sockets.containsKey(socketAddress)) {
////            context.sockets.get(socketAddress).close();
//        }
        context.sockets.put(socketAddress, socket);
//        removeNode(context.nonConnectedNeighbors, socketAddress);
        new Thread(new PortListener(socket, context)).start();
    }
}
