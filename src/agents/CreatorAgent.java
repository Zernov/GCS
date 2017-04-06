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

import static agents.Global.*;

public class CreatorAgent extends Agent {

    private Integer schedules = SCHEDULE_COUNT;

    //========================Create========================//
    //Random Listener
    private void createRandomListener(String name) {
        ContainerController cc = getContainerController();
        ArrayList<Integer> preferences = new ArrayList<>();
        for (int i = 0; i < TOTAL; i++) {
            preferences.add(getDistributionValue(i));
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

    private void createGeneration(String name, Object[] args) {
        ContainerController cc = getContainerController();
        try {
            AgentController ac = cc.createNewAgent("Generation", "agents.GenerationAgent", args);
            ac.start();
        } catch (StaleProxyException spe) {
            spe.printStackTrace();
        }
    }
    //======================================================//

    private void createDataTestListeners() {
        createCustomListener("Listener 0", new Object[] {"0 20 1 2 2 1 3 27 4 0 5 28 6 0 7 32 8 181 9 32 10 0 11 0 12 0 13 97 14 3 15 0 16 0 17 0 18 428 19 119 20 6 21 78 22 56 23 93"});
        createCustomListener("Listener 1", new Object[] {"0 1 1 280 2 46 3 85 4 10 5 2 6 21 7 161 8 194 9 4 10 169 11 0 12 9 13 1 14 0 15 2 16 0 17 0 18 63 19 7 20 375 21 50 22 36 23 0"});
        createCustomListener("Listener 2", new Object[] {"0 0 1 61 2 0 3 0 4 16 5 0 6 1 7 19 8 0 9 4 10 4 11 17 12 105 13 4 14 4 15 4 16 358 17 39 18 1 19 340 20 34 21 0 22 13 23 0"});
        createCustomListener("Listener 3", new Object[] {"0 345 1 0 2 5 3 0 4 0 5 0 6 0 7 144 8 0 9 1 10 1 11 38 12 1 13 300 14 4 15 0 16 116 17 3 18 65 19 8 20 7 21 21 22 4 23 0"});
        createCustomListener("Listener 4", new Object[] {"0 298 1 16 2 0 3 13 4 0 5 2 6 36 7 117 8 9 9 3 10 71 11 429 12 0 13 1 14 0 15 0 16 2 17 356 18 4 19 3 20 0 21 191 22 1 23 1"});
        createCustomListener("Listener 5", new Object[] {"0 1 1 78 2 0 3 60 4 0 5 95 6 363 7 0 8 264 9 9 10 320 11 69 12 28 13 31 14 0 15 7 16 49 17 0 18 11 19 13 20 0 21 0 22 0 23 30"});
        createCustomListener("Listener 6", new Object[] {"0 0 1 0 2 240 3 0 4 2 5 0 6 159 7 0 8 368 9 366 10 40 11 64 12 0 13 0 14 1 15 75 16 1 17 133 18 24 19 6 20 10 21 10 22 1 23 14"});
        createCustomListener("Listener 7", new Object[] {"0 31 1 103 2 51 3 2 4 0 5 190 6 17 7 0 8 0 9 27 10 7 11 8 12 170 13 177 14 14 15 9 16 48 17 0 18 4 19 449 20 0 21 109 22 132 23 168"});
        createCustomListener("Listener 8", new Object[] {"0 145 1 89 2 38 3 4 4 31 5 48 6 0 7 450 8 1 9 194 10 0 11 3 12 0 13 0 14 79 15 1 16 1 17 2 18 28 19 277 20 1 21 32 22 0 23 0"});
        createCustomListener("Listener 9", new Object[] {"0 11 1 25 2 40 3 0 4 231 5 32 6 257 7 0 8 5 9 24 10 0 11 0 12 374 13 0 14 0 15 0 16 0 17 6 18 55 19 113 20 0 21 27 22 91 23 0"});

        createCustomListener("Listener 10", new Object[] {"0 461 1 0 2 0 3 72 4 25 5 158 6 1 7 1 8 0 9 0 10 0 11 2 12 67 13 0 14 100 15 3 16 36 17 242 18 1 19 364 20 0 21 76 22 43 23 69"});
        createCustomListener("Listener 11", new Object[] {"0 0 1 0 2 2 3 21 4 0 5 0 6 1 7 0 8 0 9 109 10 29 11 1 12 0 13 6 14 1 15 0 16 28 17 70 18 368 19 4 20 421 21 281 22 0 23 74"});
        createCustomListener("Listener 12", new Object[] {"0 174 1 0 2 4 3 0 4 21 5 0 6 0 7 256 8 1 9 438 10 51 11 0 12 76 13 88 14 0 15 2 16 23 17 32 18 2 19 26 20 361 21 11 22 2 23 231"});
        createCustomListener("Listener 13", new Object[] {"0 0 1 21 2 456 3 0 4 0 5 10 6 28 7 35 8 1 9 54 10 0 11 76 12 1 13 1 14 126 15 47 16 50 17 186 18 16 19 0 20 0 21 0 22 2 23 354"});
        createCustomListener("Listener 14", new Object[] {"0 51 1 0 2 11 3 59 4 39 5 0 6 0 7 4 8 130 9 0 10 1 11 0 12 0 13 50 14 9 15 287 16 409 17 11 18 137 19 161 20 199 21 0 22 0 23 0"});
        createCustomListener("Listener 15", new Object[] {"0 9 1 2 2 7 3 1 4 214 5 4 6 110 7 22 8 372 9 6 10 71 11 48 12 2 13 0 14 0 15 0 16 30 17 0 18 200 19 447 20 0 21 8 22 33 23 58"});
        createCustomListener("Listener 16", new Object[] {"0 0 1 272 2 83 3 7 4 9 5 3 6 0 7 0 8 112 9 0 10 16 11 0 12 42 13 0 14 17 15 3 16 0 17 49 18 83 19 0 20 266 21 110 22 0 23 41"});
        createCustomListener("Listener 17", new Object[] {"0 7 1 1 2 5 3 0 4 93 5 3 6 1 7 0 8 2 9 0 10 4 11 63 12 0 13 6 14 249 15 463 16 9 17 4 18 266 19 1 20 0 21 44 22 0 23 9"});
        createCustomListener("Listener 18", new Object[] {"0 0 1 465 2 60 3 0 4 0 5 0 6 0 7 0 8 55 9 25 10 8 11 9 12 0 13 4 14 16 15 1 16 50 17 81 18 41 19 2 20 0 21 382 22 86 23 25"});
        createCustomListener("Listener 19", new Object[] {"0 0 1 0 2 59 3 6 4 59 5 169 6 0 7 0 8 1 9 0 10 47 11 4 12 290 13 33 14 147 15 167 16 8 17 13 18 0 19 302 20 160 21 7 22 5 23 35"});

        createCustomListener("Listener 20", new Object[] {"0 2 1 0 2 294 3 2 4 0 5 129 6 32 7 111 8 30 9 0 10 0 11 61 12 0 13 70 14 0 15 41 16 0 17 1 18 0 19 24 20 0 21 466 22 2 23 2"});
        createCustomListener("Listener 21", new Object[] {"0 25 1 236 2 4 3 0 4 0 5 0 6 0 7 18 8 14 9 7 10 0 11 15 12 0 13 0 14 336 15 212 16 9 17 145 18 3 19 5 20 467 21 6 22 0 23 12"});
        createCustomListener("Listener 22", new Object[] {"0 0 1 0 2 0 3 3 4 0 5 1 6 0 7 152 8 0 9 319 10 2 11 1 12 104 13 88 14 2 15 1 16 300 17 0 18 10 19 211 20 9 21 22 22 6 23 10"});
        createCustomListener("Listener 23", new Object[] {"0 0 1 0 2 0 3 0 4 0 5 1 6 433 7 30 8 40 9 0 10 22 11 0 12 0 13 69 14 384 15 12 16 4 17 10 18 15 19 253 20 97 21 24 22 2 23 0"});
        createCustomListener("Listener 24", new Object[] {"0 0 1 2 2 5 3 3 4 0 5 190 6 5 7 3 8 0 9 56 10 0 11 43 12 98 13 0 14 25 15 303 16 0 17 8 18 14 19 228 20 14 21 0 22 0 23 373"});
        createCustomListener("Listener 25", new Object[] {"0 0 1 0 2 2 3 0 4 237 5 156 6 3 7 13 8 4 9 74 10 0 11 95 12 0 13 1 14 59 15 423 16 22 17 0 18 18 19 0 20 30 21 3 22 319 23 302"});
        createCustomListener("Listener 26", new Object[] {"0 231 1 0 2 0 3 177 4 1 5 7 6 5 7 306 8 12 9 1 10 3 11 8 12 0 13 66 14 13 15 51 16 11 17 0 18 41 19 7 20 26 21 96 22 287 23 8"});
        createCustomListener("Listener 27", new Object[] {"0 139 1 1 2 84 3 0 4 50 5 42 6 26 7 0 8 4 9 0 10 278 11 339 12 1 13 189 14 0 15 0 16 351 17 1 18 0 19 4 20 29 21 6 22 7 23 42"});
        createCustomListener("Listener 28", new Object[] {"0 0 1 59 2 28 3 3 4 21 5 6 6 27 7 1 8 0 9 1 10 0 11 120 12 22 13 49 14 50 15 1 16 0 17 0 18 460 19 285 20 1 21 380 22 169 23 0"});
        createCustomListener("Listener 29", new Object[] {"0 20 1 357 2 466 3 13 4 0 5 10 6 185 7 121 8 0 9 10 10 0 11 10 12 5 13 5 14 1 15 12 16 48 17 68 18 1 19 0 20 0 21 39 22 28 23 2"});
    }

    private void createDataTestSchedules() {
        createCustomSchedule("Schedule 0", new Object[] {"11 14 23 18 2 19 9 21 15 3 8 16 13 17 7 20 12 1 6 4 0 10 5 22"});
        createCustomSchedule("Schedule 1", new Object[] {"16 22 19 0 2 10 17 11 21 1 9 3 13 6 14 18 7 20 8 12 5 15 4 23"});
        createCustomSchedule("Schedule 2", new Object[] {"1 17 11 13 14 23 6 15 7 5 19 0 21 2 22 3 20 16 9 10 8 12 4 18"});
        createCustomSchedule("Schedule 3", new Object[] {"23 22 17 19 13 21 14 1 2 20 4 11 7 9 12 8 18 10 3 5 0 15 16 6"});
        createCustomSchedule("Schedule 4", new Object[] {"22 0 6 14 9 8 19 11 1 21 15 7 18 10 3 17 5 4 23 12 20 2 16 13"});
        createCustomSchedule("Schedule 5", new Object[] {"13 22 5 1 3 17 20 16 0 12 21 4 18 15 19 23 11 2 6 7 10 8 14 9"});
        createCustomSchedule("Schedule 6", new Object[] {"10 7 14 1 4 17 9 23 6 19 12 16 8 2 11 15 5 0 3 22 18 13 21 20"});
        createCustomSchedule("Schedule 7", new Object[] {"18 10 0 13 1 14 15 19 7 8 4 3 23 6 12 11 2 20 16 5 17 9 21 22"});
        createCustomSchedule("Schedule 8", new Object[] {"2 20 21 4 6 14 7 23 8 3 9 22 11 15 1 16 0 10 18 5 12 13 17 19"});
        createCustomSchedule("Schedule 9", new Object[] {"9 16 20 15 13 23 14 8 3 17 11 21 5 19 12 18 6 1 7 22 10 2 4 0"});
    }

    private void createDataRandom() {
        //Create Listeners
        for (int i = 0; i < LISTENER_COUNT; i++) {
            createRandomListener(String.format("Listener %d", i));
        }

        //Create Schedules
        for (int i = 0; i < SCHEDULE_COUNT; i++) {
            createRandomSchedule(String.format("Schedule %d", i));
        }
    }

    @Override
    protected void setup() {

        //=========================Init=========================//
        createDataTestListeners();
        createDataTestSchedules();
        createGeneration("Generation", new Object[] {"3"} );
        //======================================================//

        //=======================Reactive=======================//
        //Reactive Creation
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
