package sys;

import sys.message.Message;
import sys.task.Task;
import sys.task.app.PortListener;
import sys.task.meta.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    private final static Logger logger = Logger.getLogger(Server.class.getName());
    private final BlockingQueue<Message> messageQueue;
    private final Map<String, Task> idToTask = new HashMap<>();

    public final Map<String, Socket> sockets = new HashMap<>();
    public final String LOCALHOST;
    public final Integer PORT;
    public final List<Node> neighbors;
    public final Set<Thread> taskThreads;
    public final Integer NET_NODE_NUMBER;
    public final String NODE_ID;

    public Server(int netNodeNumber, Node node, String nodeId) throws IOException {
        this.NET_NODE_NUMBER = netNodeNumber;
        this.PORT = node.port;
        this.neighbors = node.neighbors;
        LOCALHOST = InetAddress.getLocalHost().toString();
        this.messageQueue = new LinkedBlockingQueue<>();
        this.taskThreads = new HashSet<>();
        this.NODE_ID = nodeId;
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

    public void offerMessageToTask(Message message, String taskId) {
        logger.log(Level.INFO, "Offered message to " + taskId + ", message is " + message.message + " clock is: " + message.clock);
        this.idToTask.get(taskId).offerMessage(message);
    }

    public boolean hasTask(String taskId) {
        return idToTask.containsKey(taskId);
    }

    public void offerMessage(Message message) {
        this.messageQueue.offer(message);
    }

    public Message takeMessage() {
        Message message = null;
        try {
            message = this.messageQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return message;
    }
}