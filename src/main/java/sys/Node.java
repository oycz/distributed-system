package sys;

import java.util.List;

public class Node {

    public String id;
    public String hostname;
    public Integer port;
    public List<Node> neighbors;

    public Node(String hostname, Integer port) {
        this.id = id;
        this.hostname = hostname;
        this.port = port;
    }

    public Node(String id, String hostname, Integer port, List<Node> neighbors) {
        this.id = id;
        this.hostname = hostname;
        this.port = port;
        this.neighbors = neighbors;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
