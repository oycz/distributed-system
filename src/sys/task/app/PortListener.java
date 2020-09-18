package sys.task.app;

import sys.Server;
import sys.message.Message;
import sys.setting.Settings;
import sys.task.Task;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Logger;

public class PortListener extends Task {
    private final Socket socket;
    private static final Logger logger = Logger.getLogger(PortListener.class.getName());

    public PortListener(Socket socket, Server server) {
        super(server, Settings.PORT_LINSTENER_CLOCK_TYPE);
        this.socket = socket;
    }

    @Override
    public void run() {
        Message message;
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(socket.getInputStream());
        } catch (EOFException eofe) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        while(true) {
            try {
                message = (Message) in.readObject();
                context.messageQueue.add(message);
            } catch (EOFException e) {
                try {
                    socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void step(Message message) {

    }
}