package lexicalanalyzer.automatas;

import lexicalanalyzer.automatas.equations.State;
import lexicalanalyzer.automatas.equations.Trans;
import lexicalanalyzer.tokens.Node;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Set;

public class DFA extends Automata{
    private NFA nfa;
    private Set<Trans> transitionTable;


    public DFA(){

    }

    public void NFAtoDFA(NFA nfa){
        ArrayList<ArrayList<State>> states;
        this.nfa = nfa;

        boolean finish = false;
        State s = nfa.getStartingState();

        // while there are still option
        while (!finish){
            // TODO
            finish = true;
        }

    }
}