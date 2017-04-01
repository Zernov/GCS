package agents;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

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
