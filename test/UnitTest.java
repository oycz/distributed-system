import sys.Config;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;

public class UnitTest {

    @Test
    public void testConfig() throws IOException {
        Config c = new Config("config/config");
        System.out.println(c);
    }

    @Test
    public void testSocket() throws IOException {
        Socket socket = new Socket("www.google.com", 80);
        System.out.println(socket.getInetAddress().getHostAddress());
        System.out.println(socket.getInetAddress().getHostName());
    }
}
