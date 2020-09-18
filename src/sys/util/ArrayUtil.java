package sys.util;

public class ArrayUtil {

    public static String[] addAll(String[]... args) {
        for(int i = 0; i < args.length; i++) {
            if(args[i] == null) {
                args[i] = new String[0];
            }
        }
        int len = 0;
        for(String[] arr: args) {
            len += arr.length;
        }
        String[] result = new String[len];
        int p = 0;
        for(String[] arr: args) {
            for(String str: arr) {
                result[p++] = str;
            }
        }
        return result;
    }
}
