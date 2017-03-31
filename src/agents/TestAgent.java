package agents;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

public class TestAgent extends Agent {

    protected void setup() {

        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Schedule");
        template.addServices(sd);
        try {
            DFAgentDescription[] result = DFService.search(this, template);
            for (int i = 0; i < result.length; i++) {
                System.out.println(result[i].getName().toString());
            }
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }
    }
}
