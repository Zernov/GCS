package agents;

import jade.core.Agent;

import java.util.TreeMap;

import static agents.Global.*;

public class ListenerAgent extends Agent {

    private TreeMap<Integer, Integer> preferences;

    protected void setup() {

        //Create
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            preferences = toMap(args[0].toString());
        }
        System.out.println(String.format("\n[ListenerAgent \"%s\" created]\n%s\n", getLocalName(), toStringConsole(preferences)));
    }
}
