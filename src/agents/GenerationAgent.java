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
import jade.util.leap.*;
import jade.util.leap.Collection;
import jade.util.leap.Iterator;

import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static agents.Global.*;

public class GenerationAgent extends Agent {

    private Integer generations;
    private Integer generation = 0;
    private Integer requested = 0;
    private HashMap<AID, Integer[][]> profits = new HashMap<>();
    private HashMap<AID, Integer> sums = new HashMap<>();

    private ArrayList<Integer[][]> projects = new ArrayList<>();
    private AID[] clones;
    private AID[] mutantes = new AID[MUTANTE_COUNT];


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

        //Request Profit
        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                ACLMessage msg = receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
                if (msg != null) {
                    DFAgentDescription[] schedules = getSchedules();
                    ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
                    for (DFAgentDescription schedule: schedules) {
                        request.addReceiver(schedule.getName());
                    }
                    send(request);
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
                    AID name = msg.getSender();
                    Integer[][] profit = toArray(msg.getContent());
                    profits.put(name, profit);
                    sums.put(name, sumTotal(profit));
                    if (requested.equals(SCHEDULE_COUNT)) {
                        if (generation < generations) {
                            System.out.println(String.format("\n===Generation %s===", generation.toString()));
                            for (Map.Entry<AID, Integer> sum: sums.entrySet()) {
                                System.out.println(String.format("\"%s\" has profit %s", sum.getKey().getLocalName(), sum.getValue()));
                            }
                            ACLMessage propagate = new ACLMessage(ACLMessage.PROPAGATE);
                            ///////////////////MESSAGES FOR CLONES AND MUTANTS
                            DFAgentDescription[] schedules = getSchedules();
                            ArrayList<AID> list = new ArrayList<>();
                            for (DFAgentDescription schedule: schedules) {
                                list.add(schedule.getName());
                            }
                            Collections.shuffle(list);

                            System.out.println(String.format("=================="));

                            System.out.print(String.format("Males: "));
                            for (int i = 0; i < MALE_COUNT - 1; i++) {
                                propagate.addReceiver(list.get(i));
                                System.out.print(String.format("\"%s\", ", list.get(i).getLocalName()));
                            }
                            propagate.addReceiver(list.get(MALE_COUNT - 1));
                            System.out.println(String.format("\"%s\"", list.get(MALE_COUNT - 1).getLocalName()));

                            clones = topSums(sums);

                            System.out.print(String.format("Clones: "));
                            for (int i = 0; i < CLONE_COUNT - 1; i++) {
                                System.out.print(String.format("\"%s\", ", clones[i].getLocalName()));
                            }
                            System.out.println(String.format("\"%s\"", clones[CLONE_COUNT - 1].getLocalName()));

                            Collections.shuffle(list);

                            System.out.print(String.format("Mutantes: "));
                            for (int i = 0; i < MUTANTE_COUNT - 1; i++) {
                                mutantes[i] = list.get(i);
                                System.out.print(String.format("\"%s\", ", mutantes[i].getLocalName()));
                            }
                            mutantes[MUTANTE_COUNT - 1] = list.get(MUTANTE_COUNT - 1);
                            System.out.println(String.format("\"%s\"", mutantes[MUTANTE_COUNT - 1].getLocalName()));

                            System.out.println(String.format("==================\n"));
                            send(propagate);
                        }
                    }
                } else {
                    block();
                }
            }
        });

        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                ACLMessage msg = receive(MessageTemplate.MatchPerformative(ACLMessage.PROPAGATE));
                if (msg != null) {
                    ArrayList<Integer> top = toArrayPair(msg.getContent());
                    Integer[][] child = createArray(top);
                    projects.add(child);
                    if (SCHEDULE_COUNT.equals(projects.size())) {
                        //TODO
                    }
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
            System.out.println(String.format("[GenerationAgent \"%s\" was deregistered]", getLocalName()));
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }
}
