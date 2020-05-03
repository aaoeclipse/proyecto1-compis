package lexicalanalyzer.automatas;

import lexicalanalyzer.DefaultValues;
import lexicalanalyzer.automatas.equations.State;
import lexicalanalyzer.automatas.equations.Trans;
import lexicalanalyzer.tokens.Token;

import java.util.ArrayList;

/**
 * NFA of NFAS
 */
public class NFA2 {
    ArrayList<State> states;
    ArrayList<TransitionNFA> transitionTable;
    int counter;
    public boolean keyword;

    public NFA2(Token currentNFA, boolean keyword) {
        this.keyword = keyword;
        this.states = new ArrayList<>();
        this.transitionTable = new ArrayList<>();
        counter = 0;
        firstCaseNFA(currentNFA);
    }

    public boolean Simulate(String s){
        if (s == null){
            return false;
        }
        State currState = getState(0);
        String c;

        for (int i = 0; i < s.length(); i++){
            c = "" + s.charAt(i);
            currState = transition(currState, c);

            if (currState == null)
                return false;

        }
        return currState.getFinalState();
    }

    private State transition(State s, String c) {
        for (TransitionNFA t: transitionTable) {
            if (t.getCurrState().getId() == s.getId())

            if (t.transition(c)) {
                    return t.getNextState();
                }
        }
        return null;
    };

    private void firstCaseNFA(Token currentNFA) {
        states.add(new State(counter, true, false));
        counter++;
        states.add(new State(counter, false, true));
        counter++;
        transitionTable.add(new TransitionNFA(states.get(0), currentNFA, states.get(1)));
    }

    public State getState(int initOrFinal){
        if (initOrFinal==0){
            // initial
            for (TransitionNFA t: transitionTable) {
                if (t.getCurrState().getInitialState())
                    return t.getCurrState();
            }
        }else{
            // final
            for (TransitionNFA t: transitionTable) {
                if (t.getNextState().getFinalState())
                    return t.getNextState();
                if (t.getCurrState().getFinalState())
                    return t.getCurrState();
            }
        }
        return null;
    }

    public void Thompson(Token tnfa1, int option){
        switch (option){
            case 0:
                // Concat
                State finalstate = getState(1);
                finalstate.setFinalState(false);
                State newFinalState = createState(false, true);
                transitionTable.add(new TransitionNFA( finalstate, tnfa1, newFinalState));
                break;

            case 1:
                // Or
                State initialStae = getState(0);
                State finalState = getState(1);
                transitionTable.add(new TransitionNFA(initialStae, tnfa1, finalState));
                break;

            case 2:
                // *
                State initial = getState(0);
                State finals = getState(1);
                initial.setFinalState(true);
                finals.setFinalState(false);
                for (TransitionNFA t: transitionTable) {
                    if (t.getNextState().getId() == finals.getId())
                        t.setNextState(initial);
                }
                break;

            default:
                break;
        }
    }

    private State createState(boolean initial, boolean finalS) {
        State toReturn = new State(counter, initial, finalS);
        states.add(toReturn);
        counter++;
        return toReturn;
    }

    public void Thompson(NFA2 nfa1){
        State firstNFA1 = nfa1.getState(0);
        State localFinal = getState(1);
        firstNFA1.setInitialState(false);

        for (State s:nfa1.states) {
            s.setId(counter-1);
            counter++;
            states.add(s);
        }
        transitionTable.addAll(nfa1.transitionTable);

        firstNFA1.setId(getState(1).getId());
        if (!firstNFA1.getFinalState())
            localFinal.setFinalState(false);

    }

    public void concat(NFA nfa){

    }

    @Override
    public String toString() {
        String toReturn = "";
        for (TransitionNFA t: transitionTable) {
            toReturn += t.toString() + "\n";
        }
        return toReturn;
    }
}
