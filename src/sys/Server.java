package sys;

import sys.message.Message;
import sys.task.Task;
import sys.task.app.PortListener;
import sys.task.meta.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.logging.Logger;

public class Server {

    private final int netNodeNumber;
    private final static Logger logger = Logger.getLogger(Server.class.getName());

    public final PriorityBlockingQueue<Message> messageQueue;
    public final Map<String, Socket> sockets = new HashMap<>();
    public final String LOCALHOST;
    public final int PORT;
    public final Map<String, Task> idToTask = new HashMap<>();
    public final List<Node> neighbors;
    public final Set<Thread> taskThreads;

    public Server(int netNodeNumber, Node node) throws IOException {
        this.netNodeNumber = netNodeNumber;
        this.PORT = node.port;
        this.neighbors = node.neighbors;
        LOCALHOST = InetAddress.getLocalHost().toString();
        this.messageQueue = new PriorityBlockingQueue<>();
        this.taskThreads = new HashSet<>();
    }

    public void start() {
        // init meta tasks
        Heartbeat heartbeat = new Heartbeat(this);
        PassiveConnector passiveConnector = new PassiveConnector(this);
        ActiveConnector activeConnector = new ActiveConnector(this);
        MessageHandler messageHandler = new MessageHandler(this);
        CommandLine commandLine = new CommandLine(this);
        TaskStarter taskStarter = new TaskStarter(this);
        Echoer echoer = new Echoer(this);
        OrphanMessageHandler orphanMessageHandler = new OrphanMessageHandler(this);
        TaskStartNotifier taskStartNotifier = new TaskStartNotifier(this);

        newTask(heartbeat);
        newTask(passiveConnector);
        newTask(activeConnector);
        newTask(messageHandler);
        newTask(commandLine);
        newTask(taskStarter);
        newTask(echoer);
        newTask(orphanMessageHandler);
        newTask(taskStartNotifier);
    }

    public synchronized static void initSocket(Socket socket, Server context) {
        String socketAddress = socket.getInetAddress().getHostName();
        context.sockets.put(socketAddress, socket);
        context.newTask(new PortListener(socket, context));
    }

    public void newTask(Task task) {
        idToTask.put(task.taskId, task);
        Thread thread = new Thread(task);
        taskThreads.add(thread);
        thread.start();
    }
}