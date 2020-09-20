package sys;

import sys.util.StringUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config {

    String path;

    public Integer nodeNum;
    public Map<String, List<Node>> nodeNeighbors;
    public Map<String, Node> nodesById = new HashMap<>();
    public Map<Integer, Node> nodesByIdx = new HashMap<>();

    public Config(String path) throws IOException {
        this.path = path;
        this.read();
    }

    public void read() throws IOException {
        Integer nodeNum = null;
        Map<String, List<Node>> conn = new HashMap<>();
        Map<String, Node> nodesById = new HashMap<>();
        Map<Integer, Node> nodesByIdx = new HashMap<>();
        int nodesIdxP = 0, connP = 0;

        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line;
        while((line = reader.readLine()) != null) {
            line = line.split("#")[0].trim();
            String[] lineParams = line.split("\\s+");
            if(((!StringUtil.isNumeric(lineParams[0]) || Integer.parseInt(lineParams[0]) < 0))) {
                continue;
            }
            if(nodeNum == null) {
                nodeNum = Integer.parseInt(lineParams[0]);
            } else if(nodesById.size() < nodeNum) {
                String id = lineParams[0];
                String hostname = lineParams[1];
                Integer port = Integer.parseInt(lineParams[2]);
                conn.put(id, new ArrayList<>());
                Node n = new Node(id, hostname, port, conn.get(id));
                nodesById.put(lineParams[0], n);
                nodesByIdx.put(nodesIdxP++, n);
            } else if(connP < nodeNum) {
                Node n = nodesByIdx.get(connP++);
                List<Node> l = conn.get(n.id);
                for(int i = 0; i < lineParams.length; i++) {
                    l.add(nodesById.get(lineParams[i]));
                }
            }
        }
        this.nodesById = nodesById;
        this.nodesByIdx = nodesByIdx;
        this.nodeNum = nodeNum;
        this.nodeNeighbors = conn;
    }
}

