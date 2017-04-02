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

    @Override
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
                    Integer[][] profit = new Integer[TIMEZONE_COUNT][TIMEZONE_SIZE];
                    for (int i = 0; i < TIMEZONE_COUNT; i++) {
                        Integer max = 0;
                        for (int j = 0; j < TIMEZONE_SIZE; j++) {
                            max = Math.max(max, preferences.get(schedule[i][j]));
                        }
                        boolean flag = true;
                        for (int j = 0; j < TIMEZONE_SIZE; j++) {
                            if (max.equals(preferences.get(schedule[i][j])) && flag) {
                                profit[i][j] = max;
                                flag = false;
                            } else {
                                profit[i][j] = 0;
                            }
                        }
                    }
                    ACLMessage reply = msg.createReply();
                    reply.setPerformative(ACLMessage.INFORM);
                    reply.setContent(toStringMessage(profit));
                    myAgent.send(reply);
                } else {
                    block();
                }
            }
        });
        //======================================================//
    }

    @Override
    protected void takeDown() {
        try {
            DFService.deregister(this);
            System.out.println(String.format("[ListenerAgent \"%s\" was deregistered]\n", getLocalName()));
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }
}
