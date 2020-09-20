package sys;

import org.apache.log4j.*;
import sys.setting.Setting;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class ServerMain {

    private static Logger logger = Logger.getLogger(ServerMain.class);
    private static Server server;

    public static void main(String args[]) throws IOException {
//        // init log4j
        BasicConfigurator.configure();
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
            } else if(!(Setting.NECESSARY_PARAMS.get(param).size() == 1 && Setting.NECESSARY_PARAMS.get(param).get(0).equals("")) && !Setting.NECESSARY_PARAMS.get(param).contains(argMap.get(param))) {
                logger.error("Invalid argument \"" + param + "=" + argMap.get(param)
                        +"\", all possible values: " + Setting.NECESSARY_PARAMS.get(param).toString());
                return;
            }
        }

        // log level
        String mode = argMap.get("mode");
        Enumeration root = Logger.getRootLogger().getAllAppenders();
        if(mode.equals("debug")) {
            Enumeration<Appender> e = Logger.getRootLogger().getAllAppenders();
            while(e.hasMoreElements()) {
                ((AppenderSkeleton) e.nextElement()).setThreshold(Level.DEBUG);
            }
        } else if(mode.equals("finer_debug")) {
            Enumeration<Appender> e = Logger.getRootLogger().getAllAppenders();
            while(e.hasMoreElements()) {
                ((AppenderSkeleton) e.nextElement()).setThreshold(Level.TRACE);
            }
        } else {
            Enumeration<Appender> e = Logger.getRootLogger().getAllAppenders();
            while(e.hasMoreElements()) {
                ((AppenderSkeleton) e.nextElement()).setThreshold(Level.INFO);
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