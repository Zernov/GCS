package agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import java.util.ArrayList;
import java.util.Collections;

import static agents.Global.LISTENER_COUNT;
import static agents.Global.SCHEDULE_COUNT;
import static agents.Global.TOTAL;

public class CreatorAgent extends Agent {

    private Integer schedules = SCHEDULE_COUNT;

    //========================Create========================//
    //Random Listener
    private void createRandomListener(String name) {
        ContainerController cc = getContainerController();
        ArrayList<Integer> preferences = new ArrayList<>();
        for (int i = 0; i < TOTAL; i++) {
            preferences.add(i);
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
    //======================================================//

    @Override
    protected void setup() {

        //=========================Init=========================//
        //Create Listeners
        for (int i = 0; i < LISTENER_COUNT; i++) {
            createRandomListener(String.format("Listener %d", i));
        }

        //Create Schedules
        for (int i = 0; i < SCHEDULE_COUNT; i++) {
            createRandomSchedule(String.format("Schedule %d", i));
        }

        //Create Generation
        ContainerController cc = getContainerController();
        try {
            AgentController ac = cc.createNewAgent("Generation", "agents.GenerationAgent", new Object[] {"3"});
        } catch (StaleProxyException spe) {
            spe.printStackTrace();
        }
        //======================================================//

        //=======================Reactive=======================//
        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                ACLMessage msg = receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
                if (msg != null) {
                    String content = msg.getContent();
                    createCustomSchedule(String.format("Schedule %d", schedules++), new Object[]{content});
                } else {
                    block();
                }
            }
        });
        //======================================================//
    }
}
