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

import static agents.Global.*;

public class ScheduleAgent extends Agent {

    private Integer[][] schedule;
    private Integer[][] profit = new Integer[TIMEZONE_COUNT][TIMEZONE_SIZE];
    private Integer requested = 0;
    private AID requester;

    //========================Search========================//
    //Search Listeners
    private DFAgentDescription[] getListeners() {
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Listener");
        dfd.addServices(sd);
        DFAgentDescription[] result = null;
        try {
             result = DFService.search(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
        return result;
    }
    //======================================================//

    @Override
    protected void setup() {

        //=========================Init=========================//
        //Default
        for (int i = 0; i < TIMEZONE_COUNT; i++) {
            for (int j = 0; j < TIMEZONE_SIZE; j++) {
                profit[i][j] = 0;
            }
        }

        //Arguments
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            schedule = toArray(args[0].toString());
            System.out.println(String.format("[ScheduleAgent \"%s\" was created]", getLocalName()));
            //System.out.println(String.format("%s", toStringMessage(schedule)));
        } else {
            System.out.print(String.format("[ScheduleAgent \"%s\" was not created (wrong arguments)]", getLocalName()));
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
            System.out.println(String.format("[ScheduleAgent \"%s\" was registered ]", getLocalName()));
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
                    requester = msg.getSender();
                    DFAgentDescription[] listeners = getListeners();
                    ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
                    request.setContent(toStringMessage(schedule));
                    for (DFAgentDescription listener: listeners) {
                        request.addReceiver(listener.getName());
                    }
                    send(request);
                } else {
                    block();
                }
            }
        });

        //Inform profit
        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                ACLMessage msg = receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
                if (msg != null) {
                    Integer[][] content = toArray(msg.getContent());
                    profit = sumArray(profit, content);
                    requested = requested + 1;
                    if (requested.equals(LISTENER_COUNT)) {
                        ACLMessage inform = new ACLMessage(ACLMessage.INFORM);
                        inform.setContent(toStringMessage(profit));
                        inform.addReceiver(requester);
                        send(inform);
                    }
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
            System.out.println(String.format("[ScheduleAgent \"%s\" was deregistered]", getLocalName()));
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }
}
