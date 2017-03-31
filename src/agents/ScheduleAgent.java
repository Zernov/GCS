package agents;

import jade.core.Agent;

import static agents.Global.*;

public class ScheduleAgent extends Agent {

    private Integer[][] schedule = new Integer[TIMEZONE_COUNT][TIMEZONE_SIZE];
    private Integer[][] profit = new Integer[TIMEZONE_COUNT][TIMEZONE_SIZE];

    protected void setup() {

        //Create
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            schedule = toArray(args[0].toString());
        }
        System.out.println(String.format("\n[ScheduleAgent \"%s\" created]\n%s\n", getLocalName(), toStringConsole(schedule)));
    }
}
