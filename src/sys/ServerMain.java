package sys;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerMain {

    final static String CONFIG_PATH = "config/config";
    final static Map<String, String> DEFAULT_PARAMS = new HashMap<>();
    final static Set<String> NECESSARY_PARAMS = new LinkedHashSet<>();

    static {
        DEFAULT_PARAMS.put("clock", "lamport");
        NECESSARY_PARAMS.add("node_id");
        NECESSARY_PARAMS.add("clock");
    }

    static Map<String, String> argMap;
//    static Clock clock;
    static Config config;
    static Server server;

    static Logger logger = Logger.getLogger(ServerMain.class.getName());

    public static void main(String args[]) throws IOException {
        // read arguments
        Map<String, String> argMap = new HashMap<>();
        for(String arg: args) {
            if(!arg.contains("=")) {
                logger.log(Level.SEVERE, "Argument " + arg + " illegal. Please follow the format 'arg=value'");
                return;
            } else {
                String k = arg.split("=")[0].trim();
                String v = arg.split("=")[1].trim();
                if(!NECESSARY_PARAMS.contains(k)) {
                    logger.log(Level.SEVERE, "Argument " + k + " does not exist");
                    return;
                } else {
                    argMap.put(k, v);
                }
            }
        }
        // add default values
        for(String defaultK: DEFAULT_PARAMS.keySet()) {
            if(!argMap.containsKey(defaultK)) {
                argMap.put(defaultK, DEFAULT_PARAMS.get(defaultK));
            }
        }
        // handle missing arguments
        for(String param: NECESSARY_PARAMS) {
            if(!argMap.containsKey(param)) {
                logger.log(Level.SEVERE, "Argument missing. All necessary parameters: " + NECESSARY_PARAMS.toString());
                return;
            }
        }
        ServerMain.argMap = argMap;

        // read config
        config = new Config(CONFIG_PATH);

        Node thisNode = config.nodesById.get(argMap.get("node_id"));

        // init clock
        String clockType = argMap.get("clock");
//        Clock.setClockType(clockType);
//        Clock.setNodeId(thisNode.id);
//        clock = Clock.getInstance();

        // init connection
//        server = new Server(config.nodeNum, thisNode, clock);
        server = new Server(config.nodeNum, thisNode);

        // start server
        server.start();

//        CommandLine command = new CommandLine(server);
//        command.start();
    }
}