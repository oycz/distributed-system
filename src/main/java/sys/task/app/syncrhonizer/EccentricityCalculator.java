package sys.task.app.syncrhonizer;

import org.apache.log4j.Logger;
import sys.Server;
import sys.factory.MessageFactory;
import sys.message.Message;

import java.util.*;

public class EccentricityCalculator extends Synchronizer {
    private static final Logger logger = Logger.getLogger(EccentricityCalculator.class);
    private List<Set<String>> nodesOfRounds = new ArrayList<>();
    private List<Set<String>> nodesOfHops = new ArrayList<>();
    private Integer eccentricity = 1;

    public EccentricityCalculator(Server server, String taskId, int maxRound) {
        super(server, taskId, maxRound);
        nodesOfRounds.add(new HashSet<>());
        nodesOfRounds.get(0).add(context.NODE_ID);
        for(int i = 0; i < maxRound; i++) {
            nodesOfHops.add(new HashSet<>());
        }
        nodesOfHops.get(0).add(context.NODE_ID);
        logger.info( "Starting calculate eccentricity");
    }
    @Override

    public void run() {
        super.run();
        calNodeOfHops();
        logger.info("Compute completed");
        for(int i = 0; i < maxRound; i++) {
            logger.info("Hop " + i + " nodes: " + nodesOfHops.get(i).toString());
        }
        logger.info("Eccentricity of this node is: " + this.eccentricity);
    }

    @Override
    protected Message pre(){
        int round = (Integer) clock.clock;
        if(nodesOfRounds.size() < round + 1) {
            nodesOfRounds.add(new HashSet<>(nodesOfRounds.get(nodesOfRounds.size() - 1)));
        }
        List<String> buffer = new ArrayList<>();
        for(String id: nodesOfRounds.get(round)) {
            buffer.add(id);
        }
        return MessageFactory.appMessage(String.join(" ", buffer), clock, taskId, context.LOCALHOST, context.PORT);
    }

    @Override
    protected void step(Message message) {
        int round = (Integer) clock.clock;
        String[] idsNeighborKnows = message.message.split("\\s+");
        Set<String> thisRoundNode = nodesOfRounds.get(round);
        thisRoundNode.addAll(Arrays.asList(idsNeighborKnows));
        if(round > 0 && thisRoundNode.size() > nodesOfRounds.get(round - 1).size()) {
            this.eccentricity = round + 1;
        }
    }

    private void calNodeOfHops() {
        nodesOfHops.get(0).add(context.NODE_ID);
        Set<String> seen = new HashSet<>();
        seen.add(context.NODE_ID);
        for(int i = 1; i < maxRound; i++) {
            for(String s: nodesOfRounds.get(i - 1)) {
                if(!seen.contains(s)) {
                    seen.add(s);
                    nodesOfHops.get(i).add(s);
                }
            }
        }
    }
}
