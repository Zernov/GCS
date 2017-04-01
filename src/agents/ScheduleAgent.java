package agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.rmi.server.ServerCloneException;

import static agents.Global.*;

public class ScheduleAgent extends Agent {

    private Integer[][] schedule;
    private Integer[][] profit;

    protected void setup() {

        //=========================Init=========================//
        //Arguments
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            schedule = toArray(args[0].toString());
            System.out.println(String.format("[ScheduleAgent \"%s\" was created]\n", getLocalName()));
        } else {
            System.out.print(String.format("[ScheduleAgent \"%s\" was not created (wrong arguments)]\n", getLocalName()));
            doDelete();
        }

        //Yellow pages
        try {
            DFAgentDescription dfd = new DFAgentDescription();
            dfd.setName(getAID());
            ServiceDescription sd = new ServiceDescription();
            sd.setName("ScheduleAgent");
            sd.setType("Schedule");
            dfd.addServices(sd);
            DFService.register(this, dfd);
            System.out.println(String.format("[ScheduleAgent \"%s\" was registered ]\n", getLocalName()));
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
                    DFAgentDescription dfd = new DFAgentDescription();
                    ServiceDescription sd = new ServiceDescription();
                    sd.setType("Listener");
                    dfd.addServices(sd);
                    DFAgentDescription[] listeners =  DFService.search(, dfd);


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
            System.out.println(String.format("[ScheduleAgent \"%s\" was deregistered]\n", getLocalName()));
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }
}
