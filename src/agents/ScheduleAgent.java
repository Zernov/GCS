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
import sun.plugin2.message.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static agents.Global.*;

public class ScheduleAgent extends Agent {

    private Integer[][] schedule;
    private Integer[][] profit = new Integer[TIMEZONE_COUNT][TIMEZONE_SIZE];
    private Integer[] top;
    private Integer requested = 0;
    private Integer proposed = 0;
    private AID requester;
    private AID killer;
    private HashMap<AID, ArrayList<Integer>> tops = new HashMap<>();
    private AID pair;

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

    @Override
    protected void setup() {

        for (int i = 0; i < TIMEZONE_COUNT; i++) {
            for (int j = 0; j < TIMEZONE_SIZE; j++) {
                profit[i][j] = 0;
            }
        }

        //Arguments
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            schedule = toArray(args[0].toString());
            //System.out.println(String.format("[ScheduleAgent \"%s\" was created]", getLocalName()));
        } else {
            //System.out.print(String.format("[ScheduleAgent \"%s\" was not created (wrong arguments)]", getLocalName()));
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
            //System.out.println(String.format("[ScheduleAgent \"%s\" was registered ]", getLocalName()));
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }


        //Request Profit
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

        //Inform Profit
        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                ACLMessage msg = receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
                if (msg != null) {
                    Integer[][] content = toArray(msg.getContent());
                    profit = sumArray(profit, content);
                    requested = requested + 1;
                    if (requested.equals(LISTENER_COUNT)) {
                        top = topReports(schedule, profit);
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

        //Request Top
        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                ACLMessage msg = receive(MessageTemplate.MatchPerformative(ACLMessage.PROPAGATE));
                if (msg != null) {
                    DFAgentDescription[] schedules = getSchedules();
                    ACLMessage propose = new ACLMessage(ACLMessage.PROPOSE);
                    for (DFAgentDescription schedule: schedules) {
                        if (!schedule.getName().equals(getAID())) {
                            propose.addReceiver(schedule.getName());
                        }
                    }
                    send(propose);
                } else {
                    block();
                }
            }
        });

        //Send Top
        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                ACLMessage msg = receive(MessageTemplate.MatchPerformative(ACLMessage.PROPOSE));
                if (msg != null) {
                    ACLMessage reply = msg.createReply();
                    reply.setContent(toStringMessage(top));
                    reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                    send(reply);
                } else {
                    block();
                }
            }
        });

        //Choose Pair
        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                ACLMessage msg = receive(MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL));
                if (msg != null) {
                    tops.put(msg.getSender(), closeArrays(toArrayTop(msg.getContent()), top));
                    proposed = proposed + 1;
                    if (proposed.equals(SCHEDULE_COUNT - 1)) {
                        pair = topTops(tops);
                        System.out.println(String.format("\"%s\" chose \"%s\"", getLocalName(), pair.getLocalName()));
                        ACLMessage reply = new ACLMessage(ACLMessage.PROPAGATE);
                        reply.setContent(toStringMessage(tops.get(pair)));
                        reply.addReceiver(requester);
                        send(reply);
                    }
                } else {
                    block();
                }
            }
        });

        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                ACLMessage msg = receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST_WHEN));
                if (msg != null) {
                    ACLMessage reply = msg.createReply();
                    reply.setContent(toStringMessage(schedule));
                    reply.setPerformative(ACLMessage.INFORM_IF);
                    send(reply);
                } else {
                    block();
                }
            }
        });

        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                ACLMessage msg = receive(MessageTemplate.MatchPerformative(ACLMessage.CANCEL));
                if (msg != null) {
                    killer = msg.getSender();
                    doDelete();
                } else {
                    block();
                }
            }
        });
    }

    @Override
    protected void takeDown() {

        //Yellow Pages
        try {
            DFService.deregister(this);
            //System.out.println(String.format("[ScheduleAgent \"%s\" was deregistered]", getLocalName()));
        } catch (FIPAException e) {
            e.printStackTrace();
        }
        ACLMessage msg = new ACLMessage(ACLMessage.CONFIRM);
        msg.addReceiver(killer);
        send(msg);
    }
}
