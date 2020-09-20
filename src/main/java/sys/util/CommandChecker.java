package sys.util;

public class CommandChecker {

    private static final String SYNCHRONIZER_WARNING = "Synchronizer command invalid. Please follow the format: [synchronizer max_round]";

    public static String check(String[] command) {
        String warning = null;
        switch(command[0]) {
            case "synchronizer": {
                warning = synchronizerChecker(command);
                break;
            }
            case "eccentricity": {
                warning = eccentricityChecker(command);
                break;
            }
        }
        return warning;
    }

    private static String synchronizerChecker(String[] command) {
        if(command.length != 2 || !StringUtil.isNumeric(command[1])) {
            return SYNCHRONIZER_WARNING;
        }
        return null;
    }

    private static String eccentricityChecker(String[] command) {
        return null;
    }
}
