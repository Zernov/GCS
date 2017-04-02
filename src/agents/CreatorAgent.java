package agents;

import jade.core.Agent;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class CreatorAgent extends Agent {

    @Override
    protected void setup() {
        ContainerController cc = getContainerController();
        Object[] args_L1 = new Object[1];
        args_L1[0] = "1 100 2 100 3 100 4 100 5 100 6 100";
        try {
            AgentController ac = cc.createNewAgent("Listener 1", "ListenerAgent", args_L1);
        } catch (StaleProxyException spe) {
            spe.printStackTrace();
        }
    }
}
