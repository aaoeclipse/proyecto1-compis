package lexicalanalyzer.automatas;

import lexicalanalyzer.DefaultValues;
import lexicalanalyzer.PostfixTree;
import lexicalanalyzer.VisitorTree;
import lexicalanalyzer.automatas.equations.State;
import lexicalanalyzer.automatas.equations.Trans;
import java.util.Arrays;

import lexicalanalyzer.automatas.equations.TransitionTnfaDfa;
import lexicalanalyzer.reader.ReadSourceCode;
import lexicalanalyzer.tokens.Node;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DFA extends Automata{
    private NFA nfa;
    private Set<Integer> symbols;
    public ArrayList<Set<State>> states;
    private ArrayList<Integer> usedSymbols;
    private TransitionTnfaDfa transitionTable;

    private PostfixTree tree;
    private VisitorTree visitor;

    public DFA(){

    }

    public DFA(PostfixTree tree, VisitorTree visitor){
        this.tree = tree;
        this.visitor = new VisitorTree(tree);
    }


    public boolean Simulate(ReadSourceCode reader){
        int c = reader.readNextCharInfile();

        int currState = -2;
        System.out.println("c: " + (char) c + " s: " + currState);

        while ( c != DefaultValues.EOF && currState != -1) {
            currState = transitionTable.mover(currState,c);
            c = reader.readNextCharInfile();
        }
        if (currState == -1){
            return false;
        }
        return (transitionTable.getFinalStates().contains(currState));
    }

    public void DFADirect(PostfixTree tree){

    }



    public void NFAtoDFA(NFA nfa){
        states = new ArrayList<>();
        Set<State> currCosure = new HashSet<>();

        this.nfa = nfa;
        symbols = nfa.getAllSymbols();

        this.transitionTable = new TransitionTnfaDfa(symbols, nfa.getFinalState(), nfa.getInitialId());

        State s = nfa.getStartingState();

        getAllClosures(s);

        System.out.println(transitionTable);

    }

    private Set<State> getAllClosures(State s){
        Set<State> closureState = new HashSet<>();

        State curr;

        closureState = nfa.epsilonClosure(s);


        if (closureState.isEmpty()){
            return closureState;

        } else {
            // Se agrega el state que se encuentra?

            if (addToArray(closureState)){

                for (State ns: closureState){
                    System.out.print("Movement on: ");
                    System.out.println(ns);

                    for (int i: symbols) {
                        System.out.println("with : " + (char) i);
                        curr = nfa.moverNoEpsilon(ns, i);

                        if(curr != null){
                            transitionTable.addTransition(closureState,  i, getAllClosures(curr) );
                        }

                    }
                }
            }
        }
        return closureState;
    }

        private boolean addToArray(Set<State> givenState){
            int[] idOfGiven = new int[givenState.size()];
            int i = 0;
            for (State s:givenState) {
                idOfGiven[i] = s.getId();
                i++;
            }

            int[] idOfCurr;
            for (Set<State> sn:states) {
                idOfCurr = new int[sn.size()];
                i=0;
                for (State s:sn) {
                    idOfCurr[i] = s.getId();
                    i++;
                }

                if (Arrays.equals(idOfGiven, idOfCurr)){
                    return false;
                }
            }

            transitionTable.addNfaDfa(givenState);

            this.states.add(givenState);
            return true;
        }



    private void setSymbols(){
        this.usedSymbols = new ArrayList<>();

        for (int i: symbols) {
            usedSymbols.add(i);
        }
    }

    private Integer nextSymbol(){
        return null;
    }
}