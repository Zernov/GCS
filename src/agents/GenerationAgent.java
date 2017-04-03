package agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import static agents.Global.SCHEDULE_COUNT;

public class GenerationAgent extends Agent {

    private Integer generations;
    private Integer generation = 0;
    private Integer requested = 0;

    //========================Search========================//
    //Search Schedules
    private DFAgentDescription[] getSchedules() {
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Schedule");
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
        //Arguments
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            generations = Integer.parseInt(args[0].toString());
            System.out.println(String.format("[GenerationAgent \"%s\" was created]", getLocalName()));
        } else {
            System.out.print(String.format("[GenerationAgent \"%s\" was not created (wrong arguments)]", getLocalName()));
            doDelete();
        }

        //Yellow pages
        try {
            DFAgentDescription dfd = new DFAgentDescription();
            dfd.setName(getAID());
            ServiceDescription sd = new ServiceDescription();
            sd.setName("GenerationAgent");
            sd.setType("Generation");
            dfd.addServices(sd);
            DFService.register(this, dfd);
            System.out.println(String.format("[GenerationAgent \"%s\" was registered ]", getLocalName()));
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        //Listeners and Schedules

        //======================================================//

        //======================Behaviours======================//
        //Request Profit
        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                ACLMessage msg = receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
                if (msg != null) {
                    DFAgentDescription[] schedules = getSchedules();
                    for (DFAgentDescription schedule: schedules) {
                        ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
                        request.addReceiver(schedule.getName());
                        send(request);
                    }
                } else {
                    block();
                }
            }
        });

        //Receive Profit
        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                ACLMessage msg = receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
                if (msg != null) {
                    requested = requested + 1;
                    if (requested.equals(SCHEDULE_COUNT)) {
                        System.out.println("NEW GENERATION NOT IMPLEMENTED");
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
            System.out.println(String.format("[GenerationAgent \"%s\" was deregistered]", getLocalName()));
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }
}
