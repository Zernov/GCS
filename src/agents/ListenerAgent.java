package agents;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

import java.util.TreeMap;

import static agents.Global.*;

public class ListenerAgent extends Agent {

    private TreeMap<Integer, Integer> preferences;

    protected void setup() {

        //=========================Init=========================//
        //Arguments
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            preferences = toMap(args[0].toString());
            System.out.println(String.format("\n[ListenerAgent \"%s\" was created]\n", getLocalName()));
        } else {
            System.out.print(String.format("\n[ListenerAgent \"%s\" was not created (wrong arguments)]\n", getLocalName()));
            doDelete();
        }

        //Yellow pages
        try {
            DFAgentDescription dfd = new DFAgentDescription();
            dfd.setName(getAID());
            ServiceDescription sd = new ServiceDescription();
            sd.setName("ListenerAgent");
            sd.setType("Listener");
            dfd.addServices(sd);
            DFService.register(this, dfd);
            System.out.println(String.format("\n[ListenerAgent \"%s\" was registered ]\n", getLocalName()));
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
        //======================================================//
    }
}
