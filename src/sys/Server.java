package sys;

import sys.message.Message;
import sys.setting.Settings;
import sys.task.Task;
import sys.task.meta.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.logging.Logger;

public class Server {


    public final PriorityBlockingQueue<Message> messageQueue;
    public final Map<String, Socket> sockets = new HashMap<>();
    public final String LOCALHOST;
    public final int PORT;
    public Set<Node> nonConnectedNeighbors;
    public final Map<String, Task> idToTask = new HashMap<>();
    public final Map<String, String> metaTaskId = new HashMap<>();
    public final List<Node> neighbors;
    public final Set<Thread> taskThreads;

    private final int netNodeNumber;
    private final static Logger logger = Logger.getLogger(Server.class.getName());


    public Server(int netNodeNumber, Node node) throws IOException {
        this.netNodeNumber = netNodeNumber;
        this.PORT = node.port;
        this.neighbors = node.neighbors;
        LOCALHOST = InetAddress.getLocalHost().toString();
        this.nonConnectedNeighbors = ConcurrentHashMap.newKeySet();
        this.nonConnectedNeighbors.addAll(neighbors);
        this.messageQueue = new PriorityBlockingQueue<>();
        this.taskThreads = new HashSet<>();
    }

    public void start() {
        // init meta tasks
        // passive connections thread
        PassiveConnector passiveConnector = new PassiveConnector(this);
        ActiveConnector activeConnector = new ActiveConnector(this);
        MessageHandler messageHandler = new MessageHandler(this);
        CommandLine commandLine = new CommandLine(this);
        TaskStarter taskStarter = new TaskStarter(this);
        Echoer echoer = new Echoer(this);
        OrphanMessageHandler orphanMessageHandler = new OrphanMessageHandler(this);
        TaskNotifier taskNotifier = new TaskNotifier(this);

        metaTaskId.put(Settings.PASSIVE_CONNECTOR, passiveConnector.taskId);
        metaTaskId.put(Settings.ACTIVE_CONNECTOR, activeConnector.taskId);
        metaTaskId.put(Settings.MESSAGE_HANDLER, messageHandler.taskId);
        metaTaskId.put(Settings.COMMAND_LINE, commandLine.taskId);
        metaTaskId.put(Settings.TASK_STARTER, taskStarter.taskId);
        metaTaskId.put(Settings.ECHOER, echoer.taskId);
        metaTaskId.put(Settings.ORPHAN_MESSAGE_HANDLER, orphanMessageHandler.taskId);
        metaTaskId.put(Settings.TASK_NOTIFIER, taskNotifier.taskId);

        idToTask.put(passiveConnector.taskId, passiveConnector);
        idToTask.put(activeConnector.taskId, activeConnector);
        idToTask.put(messageHandler.taskId, messageHandler);
        idToTask.put(commandLine.taskId, commandLine);
        idToTask.put(taskStarter.taskId, taskStarter);
        idToTask.put(echoer.taskId, echoer);
        idToTask.put(orphanMessageHandler.taskId, orphanMessageHandler);
        idToTask.put(taskNotifier.taskId, taskNotifier);

        newTask(passiveConnector);
        newTask(activeConnector);
        newTask(messageHandler);
        newTask(commandLine);
        newTask(taskStarter);
        newTask(echoer);
        newTask(orphanMessageHandler);
        newTask(taskNotifier);
    }

    public void newTask(Task task) {
        Thread thread = new Thread(task);
        taskThreads.add(thread);
        thread.start();
    }
}