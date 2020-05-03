package lexicalanalyzer.automatas;

import lexicalanalyzer.automatas.equations.State;
import lexicalanalyzer.tokens.Token;

public class TransitionNFA {
    public State currState;
    public State nextState;
    public Token token;
    public NFA transitinoNFA;

    public TransitionNFA(State currState, Token transitinoNFA, State nextState) {
        this.currState = currState;
        this.nextState = nextState;
        this.transitinoNFA = transitinoNFA.getNFA();
        this.token = transitinoNFA;
    }

    public boolean transition(String s){
        return transitinoNFA.SimulateString(s);
    }

    public State getCurrState() {
        return currState;
    }

    public void setCurrState(State currState) {
        this.currState = currState;
    }

    public State getNextState() {
        return nextState;
    }

    public void setNextState(State nextState) {
        this.nextState = nextState;
    }

    public NFA getTransitinoNFA() {
        return transitinoNFA;
    }

    public void setTransitinoNFA(NFA transitinoNFA) {
        this.transitinoNFA = transitinoNFA;
    }

    @Override
    public String toString() {
        return "[" + currState + ", " + token.getName() + ", " + nextState + "]";
    }
}
