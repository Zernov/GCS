package agents;

import jade.core.Agent;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class CreatorAgent extends Agent {

    @Override
    protected void setup() {
        ContainerController cc = getContainerController();
        try {
            AgentController ac = cc.createNewAgent("Listener 1", "agents.ListenerAgent", new Object[] {"1 100 2 100 3 100 4 100 5 100 6 100"});
            ac.start();
        } catch (StaleProxyException spe) {
            spe.printStackTrace();
        }
    }
}
