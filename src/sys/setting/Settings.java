package sys.setting;

import sys.clock.LamortClock;
import sys.clock.MatrixClock;
import sys.clock.TimeStampClock;
import sys.clock.VectorClock;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Settings {

    public static final String CONFIG_PATH = "config/config";

    public static final Map<String, Class> CLOCK_TYPES = new HashMap<>();
    public static final Set<String> COMMANDS = new HashSet<>(); // task classes
    public static final Map<String, String> DEFAULT_PARAMS = new HashMap<>();
    public static final Set<String> NECESSARY_PARAMS = new HashSet<>();


    static {
        CLOCK_TYPES.put("lamport", LamortClock.class);
        CLOCK_TYPES.put("matrix", MatrixClock.class);
        CLOCK_TYPES.put("vector", VectorClock.class);
        CLOCK_TYPES.put("timestamp", TimeStampClock.class);

        COMMANDS.add("synchronizer");
        COMMANDS.add("eccentricity");
        COMMANDS.add("help");

        NECESSARY_PARAMS.add("node_id");
        NECESSARY_PARAMS.add("clock");

        DEFAULT_PARAMS.put("clock", "lamport");
    }

    public static final Integer CHECK_AVAIL_TIMEOUT = 3000000;

    public static final String SYNCHRONIZER_CLOCK_TYPE = "lamport";
    public static final String ACTIVE_CONNECTOR_CLOCK_TYPE = "none";
    public static final String COMMAND_LINE_CLOCK_TYPE = "none";
    public static final String MESSAGE_HANDLER_CLOCK_TYPE = "none";
    public static final String PASSIVE_CONNECTOR_CLOCK_TYPE = "none";
    public static final String PORT_LINSTENER_CLOCK_TYPE = "none";
    public static final String TASK_STARTER_CLOCK_TYPE = "none";
    public static final String ECHOER_CLOCK_TYPE = "none";
    public static final String ORPHAN_MESSAGE_HANDLER_CLOCK_TYPE = "none";
    public static final String TASK_START_NOTIFIER_CLOCK_TYPE = "none";
    public static final String HEARTBEAT_CLOCK_TYPE = "none";

    // meta task IDs
    public static final String PASSIVE_CONNECTOR = "1";
    public static final String ACTIVE_CONNECTOR = "2";
    public static final String MESSAGE_HANDLER = "3";
    public static final String COMMAND_LINE = "4";
    public static final String TASK_STARTER = "5";
    public static final String ECHOER = "6";
    public static final String ORPHAN_MESSAGE_HANDLER = "7";
    public static final String TASK_START_NOTIFIER = "8";
    public static final String HEARTBEAT = "9";

    public static final Integer HEARTBEAT_PORT = 6378;
}
