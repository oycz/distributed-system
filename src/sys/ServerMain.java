package sys;

import sys.setting.Settings;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerMain {

    private static Map<String, String> argMap;
    private static Config config;
    private static Server server;

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
                if(!Settings.NECESSARY_PARAMS.contains(k)) {
                    logger.log(Level.SEVERE, "Argument " + k + " does not exist");
                    return;
                } else {
                    argMap.put(k, v);
                }
            }
        }
        // add default values
        for(String defaultK: Settings.DEFAULT_PARAMS.keySet()) {
            if(!argMap.containsKey(defaultK)) {
                argMap.put(defaultK, Settings.DEFAULT_PARAMS.get(defaultK));
            }
        }
        // handle missing arguments
        for(String param: Settings.NECESSARY_PARAMS) {
            if(!argMap.containsKey(param)) {
                logger.log(Level.SEVERE, "Argument missing. All necessary parameters: " + Settings.NECESSARY_PARAMS.toString());
                return;
            }
        }
        ServerMain.argMap = argMap;

        // read config
        config = new Config(Settings.CONFIG_PATH);

        Node thisNode = config.nodesById.get(ServerMain.argMap.get("node_id"));

        // init clock
        String clockType = argMap.get("clock");

        // init connection
        server = new Server(config.nodeNum, thisNode);

        // start server
        server.start();
    }
}