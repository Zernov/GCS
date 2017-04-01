package agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

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
            System.out.println(String.format("[ListenerAgent \"%s\" was created]\n", getLocalName()));
        } else {
            System.out.print(String.format("[ListenerAgent \"%s\" was not created (wrong arguments)]\n", getLocalName()));
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
            System.out.println(String.format("[ListenerAgent \"%s\" was registered ]\n", getLocalName()));
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
        //======================================================//

        //======================Behaviours======================//
        //Request profit
        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                ACLMessage msg = receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
                if (msg != null) {
                    String content = msg.getContent();
                    Integer[][] schedule = toArray(content);
                    Integer profit = 0;
                    for (Integer[] timezone: schedule) {
                        Integer max = 0;
                        for(int i = 0; i < TIMEZONE_SIZE; i++) {
                            max = Math.max(max, preferences.get(timezone[i]));
                        }
                        profit = profit + max;
                    }
                    ACLMessage reply = msg.createReply();
                    reply.setPerformative(ACLMessage.INFORM);
                    reply.setContent(profit.toString());
                    myAgent.send(reply);
                } else {
                    block();
                }
            }
        });
        //======================================================//
    }

    protected void takeDown() {
        try {
            DFService.deregister(this);
            System.out.println(String.format("[ListenerAgent \"%s\" was deregistered]\n", getLocalName()));
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }
}
