package agents;

import jade.core.Agent;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class CreatorAgent extends Agent {

    @Override
    protected void setup() {

        //Create
        ContainerController cc = getContainerController();
        try {
            AgentController ac_l1 = cc.createNewAgent("Listener 1", "agents.ListenerAgent", new Object[] {"1 100 2 100 3 100 4 100 5 0 6 0"});
            AgentController ac_l2 = cc.createNewAgent("Listener 2", "agents.ListenerAgent", new Object[] {"1 100 2 100 3 100 4 0 5 0 6 0"});
            AgentController ac_l3 = cc.createNewAgent("Listener 3", "agents.ListenerAgent", new Object[] {"1 100 2 100 3 0 4 0 5 0 6 0"});
            ac_l1.start();
            ac_l2.start();
            ac_l3.start();
            AgentController ac_s1 = cc.createNewAgent("Schedule 1", "agents.ScheduleAgent", new Object[] {"1 2 3 4 5 6"});
            AgentController ac_s2 = cc.createNewAgent("Schedule 2", "agents.ScheduleAgent", new Object[] {"1 4 3 2 5 6"});
            AgentController ac_s3 = cc.createNewAgent("Schedule 3", "agents.ScheduleAgent", new Object[] {"6 2 3 4 5 1"});
            ac_s1.start();
            ac_s2.start();
            ac_s3.start();
            AgentController ac_g1 = cc.createNewAgent("Generation 1", "agents.GenerationAgent", new Object[] {"0"});
            ac_g1.start();

        } catch (StaleProxyException spe) {
            spe.printStackTrace();
        }
    }
}
