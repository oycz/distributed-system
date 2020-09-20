import org.junit.Test;
import sys.util.ArrayUtil;
import sys.util.CommandChecker;

import java.io.IOException;
import java.net.Socket;

public class UnitTest {

    @Test
    public void testSocket() throws IOException {
        Socket socket = new Socket("www.google.com", 80);
        System.out.println(socket.getInetAddress().getHostAddress());
        System.out.println(socket.getInetAddress().getHostName());
    }

    @Test
    public void testValueOf() {
        String[] empty = new String[0];
        String[] concat = new String[]{"a", "b", String.valueOf(empty)};
        for(String str: concat) {
            System.out.print(str + " ");
        }
    }

    @Test
    public void testArrayUtil() {
        String[] arr1 = {"a", "b", "c"};
        String[] arr2 = null;
        System.out.println(ArrayUtil.addAll(arr1, arr2).length);
    }

    @Test
    public void commandCheckTest() {
        String warning = CommandChecker.check(new String[] {"synchronizer", "5"});
        System.out.println(warning);
    }

    @Test
    public void testJoin() {
        System.out.println(String.join(" ", new String[0]));
    }
}
