package sys;

import sys.setting.Setting;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerMain {

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
                if(!Setting.NECESSARY_PARAMS.containsKey(k)) {
                    logger.log(Level.SEVERE, "Argument " + k + " does not exist");
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
                logger.log(Level.SEVERE, "Argument missing. All necessary parameters: " + Setting.NECESSARY_PARAMS.toString());
                return;
            } else if(!Setting.NECESSARY_PARAMS.get(param).contains(argMap.get(param))) {
                logger.log(Level.SEVERE, "Invalid argument \"" + param + "=" + argMap.get(param)
                        +"\", all possible values: " + Setting.NECESSARY_PARAMS.get(param).toString());
                return;
            }
        }

//        // log level
//        String mode = argMap.get("mode");
//        Logger root = Logger.getLogger("");
//        if(mode.equals("debug")) {
//            root.setLevel(Level.FINE);
//        } else if(mode.equals("finer_debug")) {
//            root.setLevel(Level.FINER);
//        }

        String configPath;
        if(argMap.get("env").equals("test")) {
            configPath = Setting.TEST_CONFIG_PATH;
        } else {
            configPath = Setting.CONFIG_PATH;
        }
        // read config
        config = new Config(configPath);
        Node thisNode = config.nodesById.get(argMap.get("node_id"));
        // init connection
        server = new Server(config.nodeNum, thisNode);
        // start server
        server.start();
    }
}