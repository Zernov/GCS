package agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * Created by winnie on 3/29/2017.
 */
public class ListenerAgent extends Agent {

    protected void setup() {

        //Create
        System.out.println("LISTENER " + getLocalName() + " CREATED");

        //Behavior
        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                ACLMessage msg = receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
                System.out.println(myAgent.getLocalName() + " RECEIVED MESSAGE FROM " + msg.getSender());
                ACLMessage reply = msg.createReply();
                reply.setContent("DAROVA))))");
                myAgent.send(reply);
                System.out.println(myAgent.getLocalName() + " SENT ANSWER MESSAGE TO " + msg.getSender());
            }
        });

    }
}
