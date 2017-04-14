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
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import java.util.ArrayList;
import java.util.Collections;

import static agents.Global.*;

public class CreatorAgent extends Agent {

    private Integer population = SCHEDULE_COUNT;
    private Integer requested = 0;
    private ArrayList<Integer[][]> projects;
    private AID generation_agent;

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

    //Random Listener
    private void createRandomListener(String name) {
        ContainerController cc = getContainerController();
        ArrayList<Integer> preferences = new ArrayList<>();
        Double a = getRandomDouble();
        Integer b = getRandomInteger();
        for (int i = 0; i < TOTAL; i++) {
            preferences.add(getDistributionValue(i, a, b));
        }
        Collections.shuffle(preferences);
        String args = "";
        for (int i = 0; i < TOTAL; i++) {
            args = args + String.format("%d %d ", i, preferences.get(i));
        }
        args = args.substring(0, args.length() - 1);
        try {
            AgentController ac = cc.createNewAgent(name, "agents.ListenerAgent", new Object[] {args});
            ac.start();
        } catch (StaleProxyException spe) {
            spe.printStackTrace();
        }
    }

    //Custom Listener
    private void createCustomListener(String name, Object[] args) {
        ContainerController cc = getContainerController();
        try {
            AgentController ac = cc.createNewAgent(name, "agents.ListenerAgent", args);
            ac.start();
        } catch (StaleProxyException spe) {
            spe.printStackTrace();
        }
    }

    //Random Schedule
    private void createRandomSchedule(String name) {
        ContainerController cc = getContainerController();
        ArrayList<Integer> schedule = new ArrayList<>();
        for (int i = 0; i < TOTAL; i++) {
            schedule.add(i);
        }
        Collections.shuffle(schedule);
        String args = "";
        for (int i = 0; i < TOTAL; i++) {
            args = args + String.format("%d ", schedule.get(i));
        }
        args = args.substring(0, args.length() - 1);
        try {
            AgentController ac = cc.createNewAgent(name, "agents.ScheduleAgent", new Object[] {args});
            ac.start();
        } catch (StaleProxyException spe) {
            spe.printStackTrace();
        }
    }

    //Custom Listener
    private void createCustomSchedule(String name, Object[] args) {
        ContainerController cc = getContainerController();
        try {
            AgentController ac = cc.createNewAgent(name, "agents.ScheduleAgent", args);
            ac.start();
        } catch (StaleProxyException spe) {
            spe.printStackTrace();
        }
    }

    //Generation
    private void createGeneration(String name, Object[] args) {
        ContainerController cc = getContainerController();
        try {
            AgentController ac = cc.createNewAgent("Generation", "agents.GenerationAgent", args);
            ac.start();
        } catch (StaleProxyException spe) {
            spe.printStackTrace();
        }
    }

    private void createDataTestListeners() {
        createCustomListener("Listener 0", new Object[] {"0 98 1 96 2 93 3 26 4 16 5 13 6 3 7 3 8 1 9 1 10 2 11 2 12 1 13 6 14 0 15 1 16 9 17 10 18 0 19 8 20 6 21 5 22 7 23 0 24 0 25 4 26 11 27 4 28 2 29 5"});
        createCustomListener("Listener 1", new Object[] {"0 95 1 89 2 57 3 44 4 38 5 22 6 2 7 28 8 26 9 31 10 14 11 1 12 0 13 3 14 12 15 8 16 9 17 13 18 19 19 17 20 7 21 6 22 20 23 4 24 24 25 34 26 0 27 10 28 15 29 5"});
        createCustomListener("Listener 2", new Object[] {"0 96 1 94 2 85 3 51 4 41 5 2 6 0 7 14 8 13 9 10 10 17 11 32 12 24 13 6 14 5 15 3 16 20 17 19 18 29 19 8 20 0 21 36 22 7 23 12 24 4 25 27 26 9 27 16 28 1 29 22"});
        createCustomListener("Listener 3", new Object[] {"0 97 1 92 2 20 3 15 4 13 5 2 6 5 7 5 8 3 9 9 10 3 11 1 12 2 13 11 14 6 15 1 16 8 17 0 18 4 19 1 20 0 21 10 22 3 23 0 24 2 25 6 26 4 27 0 28 0 29 7"});
        createCustomListener("Listener 4", new Object[] {"0 94 1 78 2 47 3 41 4 37 5 12 6 4 7 1 8 2 9 24 10 3 11 8 12 17 13 18 14 7 15 10 16 15 17 14 18 13 19 6 20 0 21 28 22 26 23 9 24 30 25 5 26 22 27 20 28 33 29 0"});
        createCustomListener("Listener 5", new Object[] {"0 96 1 92 2 87 3 68 4 43 5 4 6 23 7 1 8 19 9 3 10 37 11 0 12 6 13 9 14 13 15 14 16 10 17 16 18 30 19 27 20 0 21 7 22 12 23 2 24 25 25 8 26 17 27 5 28 21 29 33"});
        createCustomListener("Listener 6", new Object[] {"0 97 1 94 2 90 3 84 4 57 5 19 6 23 7 5 8 31 9 28 10 15 11 2 12 13 13 16 14 4 15 0 16 12 17 21 18 8 19 1 20 18 21 35 22 26 23 7 24 41 25 9 26 11 27 3 28 0 29 6"});
        createCustomListener("Listener 7", new Object[] {"0 96 1 91 2 83 3 48 4 40 5 2 6 5 7 6 8 14 9 29 10 10 11 3 12 16 13 8 14 13 15 9 16 24 17 17 18 26 19 4 20 1 21 0 22 0 23 12 24 22 25 7 26 32 27 35 28 20 29 19"});
        createCustomListener("Listener 8", new Object[] {"0 98 1 96 2 94 3 85 4 17 5 2 6 2 7 6 8 8 9 7 10 0 11 9 12 11 13 5 14 13 15 1 16 5 17 2 18 0 19 1 20 1 21 4 22 6 23 10 24 3 25 4 26 3 27 0 28 0 29 1"});
        createCustomListener("Listener 9", new Object[] {"0 98 1 94 2 78 3 17 4 14 5 7 6 3 7 4 8 0 9 8 10 3 11 0 12 0 13 3 14 0 15 1 16 7 17 2 18 5 19 2 20 6 21 4 22 9 23 1 24 10 25 0 26 12 27 1 28 5 29 2"});
    }

    private void createDataTestSchedules() {
        createCustomSchedule("Schedule 0", new Object[] {"20 25 9 6 7 1 29 14 8 17 4 26 2 10 21 12 15 3 5 0 23 11 24 27 13 22 16 28 18 19"});
        createCustomSchedule("Schedule 1", new Object[] {"20 26 0 4 7 1 14 25 8 15 17 10 11 22 12 3 18 2 9 19 6 29 21 27 23 28 13 16 5 24"});
        createCustomSchedule("Schedule 2", new Object[] {"12 19 24 26 15 18 5 8 28 14 9 6 2 23 3 25 17 21 27 16 0 11 22 7 20 4 13 29 1 10"});
        createCustomSchedule("Schedule 3", new Object[] {"27 9 28 20 14 16 17 26 23 24 0 7 8 10 19 22 1 4 5 3 11 2 18 13 15 12 21 6 25 29"});
        createCustomSchedule("Schedule 4", new Object[] {"10 0 15 19 25 14 9 3 22 20 24 27 26 6 18 17 12 23 2 28 7 5 11 29 21 8 4 13 1 16"});
    }

    //Random Data
    private void createDataRandom() {
        for (int i = 0; i < LISTENER_COUNT; i++) {
            createRandomListener(String.format("Listener %d", i));
        }

        for (int i = 0; i < SCHEDULE_COUNT; i++) {
            createRandomSchedule(String.format("Schedule %d", i));
        }
    }

    private DFAgentDescription[] getGenerations() {
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Generation");
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

        createDataTestListeners();
        createDataTestSchedules();
        createGeneration("Generation", new Object[] {"10"} );

        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        DFAgentDescription[] generations = new DFAgentDescription[0];
        while (generations.length == 0) {
            generations = getGenerations();
        }
        for (DFAgentDescription generation: generations) {
            generation_agent = generation.getName();
            msg.addReceiver(generation_agent);
        }
        send(msg);

        //Reactive Creation
        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                ACLMessage msg = receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
                if (msg != null) {
                    projects = toArrayList(msg.getContent());
                    ACLMessage killer = new ACLMessage(ACLMessage.CANCEL);
                    DFAgentDescription[] schedules = getSchedules();
                    for (DFAgentDescription schedule: schedules) {
                        killer.addReceiver(schedule.getName());
                    }
                    requested = 0;
                    send(killer);
                } else {
                    block();
                }
            }
        });

        //New generation
        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                ACLMessage msg = receive(MessageTemplate.MatchPerformative(ACLMessage.CONFIRM));
                if (msg != null) {
                    requested = requested + 1;
                    if (SCHEDULE_COUNT.equals(requested)) {
                        for (Integer[][] project: projects) {
                            createCustomSchedule(String.format("Schedule %s", population.toString()), new Object[] {toStringMessage(project)});
                            population = population + 1;
                        }
                        ACLMessage next = new ACLMessage(ACLMessage.REQUEST);
                        next.addReceiver(generation_agent);
                        send(next);
                    }
                } else {
                    block();
                }
            }
        });
    }
}
