
# Distributed System <a href="https://github.com/oycz/distributed-system"><img style="display: inline;" src="https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png" width="35"/></a>[![HitCount](http://hits.dwyl.com/oycz/distributed-system.svg)](http://hits.dwyl.com/oycz/distributed-system)

A distributed system with complete infrastructure, code is flexible and easy extension.

## Compile: 
This project using maven for project management. <br/> 
Go to the root directory which contains <code>pom.xml</code>. Run command <code>mvn package</code>.<br/>
Now the compiled jar file should be already in <code>target</code> directory.

## Run:
Go to the classpath, execute following command: <br/> 
<code>java -jar distributed-system-0.1-jar-with-dependencies.jar node_id=[node_id] mode=[mode], env=[env] </code>

#### Arguments:
There are three arguments needed by main class so far. <br/> 

##### node_id <small>(required)</small>
Id of this this machine, as configured in the <code>config.conf</code><br/> 

##### mode
Possible values: <code>normal(default), debug, finer_debug</code><br/> 
This argument is to specify the level of log. The granularity increases from left to right

##### env
Possible values: <code>production(default), test</code><br/> 
By default, the config path file is <code>:classpath/resources/config.conf</code>. <br/>
If <code>env</code> is specified as <code>test</code>, then the main class will read the config file <code>:classpath/resources/config.conf</code> <br/>

**Before execute any application, please be sure to startup all nodes specified in the <code>config.conf</code>**

## Code structure:
#### Basic operation rule:
The system is *message* and *task* based. <br/>
In one node, there runs several threads named *task* (whose code is in the *task* package). One task is essentially a thread which is responsible for certain kind of work in the system.<br/>
Tasks communicate through *message* (whose code is in the message package). Every *message* and *task* has a *clock* (type of clock like lamport clock, vector clock or matrix clock depends on type of tasks), and every task has a *message queue*, which is a priority queue of message ordered by *clock* of that *message* to guarantee FIFO (or other arbitrary order) of messages.<br/>
#### Tasks:
There are two types of tasks, one is *meta task*, the other is *application task*. All *meta task*s were activated along with the node to ensure the basic operation of the system. <br/>
##### Meta task:
Currently, there are <code>9</code> meta tasks:
<ol>
<li><code>ActiveConnector</code>, to actively connector the neighbors of this node after startup.</li>
<li><code>PassiveConnector</code>, to passively listen and handle connection requests from other nodes.</li>
<li><code>CommandLine</code>, to process user input.</li>
<li><code>Echoer</code>, when user input some nonsense that is not command, <code>Echoer</code> is reponsible for echoing it back to command line. </li>
<li><code>Heartbeat</code>, listen to a specified port, for other nodes to ensure this node is alive or not.</li>
<li><code>MessageHandler</code>, to handle messages in the system.</li>
<li><code>OrphanMessageHandler</code>, if a message that not belong to any alive task was sent, it is temporarily keep by <code>OrphanMessageHandler</code> until that task occur. </li>
<li><code>TaskStarter</code>, a meta task to start other tasks.</li>
<li><code>TaskStartNotifier</code>, a meta task to notify neighbors of this node starting required tasks to ensure the synchronization of tasks in distributed systems. </li>
</ol>

##### Applilcation task:
Now there are <code>2</code> application tasks:
<ol>
<li><code>synchronizer</code>, a global synchronizer to ensure all nodes in the distributed system stay in the same number of round at same time.</li>
<li><code>eccentricity</code>, expand logic of <code>synchronizer</code> to allow it to calculate the eccentricity of nodes in the system.</li>
</ol>
An application task can be started by user through command line.

## Play with it:
There are two applications in the system for you to play with after start, one is <code>synchronizer</code>, another is <code>eccentricity calculator</code>. <br/>
You may use following commands to start an application:
<ol>
<li><code>synchronizer [max_round]</code></li>
<li><code>eccentricity</code></li>
</ol>

## Contribute:
You can add applications by add you own *application task* following existing logic. <br/>
Details to be added...