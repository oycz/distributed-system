package sys;

import org.apache.log4j.Logger;
import sys.setting.Setting;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ServerMain {

    private static Logger logger = Logger.getLogger(ServerMain.class);
    private static Server server;

    public static void main(String args[]) throws IOException {
//        // init log4j
//        BasicConfigurator.configure();
        // read arguments
        Map<String, String> argMap = new HashMap<>();
        for(String arg: args) {
            if(!arg.contains("=")) {
                logger.error("Argument " + arg + " illegal. Please follow the format 'arg=value'");
                return;
            } else {
                String k = arg.split("=")[0].trim();
                String v = arg.split("=")[1].trim();
                if(!Setting.NECESSARY_PARAMS.containsKey(k)) {
                    logger.error("Argument " + k + " does not exist");
                    return;
                } else {
                    argMap.put(k, v);
                }
            }
        }
        // add default values
        for(String defaultK: Setting.DEFAULT_PARAMS.keySet()) {
            if(!argMap.containsKey(defaultK)) {
                argMap.put(defaultK, Setting.DEFAULT_PARAMS.get(defaultK));
            }
        }
        // handle missing arguments
        for(String param: Setting.NECESSARY_PARAMS.keySet()) {
            if(!argMap.containsKey(param)) {
                logger.error("Argument missing. All necessary parameters: " + Setting.NECESSARY_PARAMS.toString());
                return;
            } else if(!Setting.NECESSARY_PARAMS.get(param).contains(argMap.get(param))) {
                logger.error("Invalid argument \"" + param + "=" + argMap.get(param)
                        +"\", all possible values: " + Setting.NECESSARY_PARAMS.get(param).toString());
                return;
            }
        }

        String configPath;
        if(argMap.get("env").equals("test")) {
            configPath = Setting.TEST_CONFIG_PATH;
        } else {
            configPath = Setting.CONFIG_PATH;
        }
        logger.info(configPath);
        // read config.conf
        Config config = new Config(configPath);
        String nodeId = argMap.get("node_id");
        Node thisNode = config.nodesById.get(nodeId);
        // init connection
        server = new Server(config.nodeNum, thisNode, nodeId);
        // start server
        server.start();
    }
}