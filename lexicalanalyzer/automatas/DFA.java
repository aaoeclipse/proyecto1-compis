package lexicalanalyzer.automatas;

import lexicalanalyzer.DefaultValues;
import lexicalanalyzer.PostfixTree;
import lexicalanalyzer.VisitorTree;
import lexicalanalyzer.automatas.equations.State;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import lexicalanalyzer.automatas.equations.TransitionTnfaDfa;
import lexicalanalyzer.reader.ReadSourceCode;
import lexicalanalyzer.tokens.Node;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.lang.reflect.Array;

public class DFA extends Automata{
    private NFA nfa;
    private Set<Integer> symbols;
    public ArrayList<Set<State>> states;
    private ArrayList<Integer> usedSymbols;
    private TransitionTnfaDfa transitionTable;

    private PostfixTree tree;
    private VisitorTree visitor;

    private Queue<Set<State>> queue;

    public DFA(){

    }

    public DFA(PostfixTree tree, VisitorTree visitor){
        this.tree = tree;
        this.visitor = new VisitorTree(tree);
    }

    public boolean Simulate(ReadSourceCode reader){
        int c = reader.readNextCharInfile();

        int currState = -2;

        while ( c != DefaultValues.EOF && currState != -1) {
            currState = transitionTable.mover(currState, c);

            c = reader.readNextCharInfile();
        }
        if (currState == -1){
            return false;
        }
        return (transitionTable.getFinalStates().contains(currState));
    }

    public void DFADirect(PostfixTree tree){

    }

    /**
     * it's given a nfa and it transform it to a dfa
     * @param nfa
     */
    public void NFAtoDFA(NFA nfa){
        queue = new LinkedList<>();
        // Set variables
        states = new ArrayList<>();
        Set<State> currCosure = new HashSet<>();
        this.nfa = nfa;
        symbols = nfa.getAllSymbols();
        // initiate the transition table
        this.transitionTable = new TransitionTnfaDfa(symbols, nfa.getFinalState(), nfa.getInitialId());
        // get the initial state
        State s = nfa.firstStartingState();
        // fill the table of transition (DFA)
        getAllClosures2(s);
        // debug
         System.out.println(transitionTable);
    }

    /**
     * It fills the transition table. It goes through the closures-epsilon, then it goes with all the values in which
     * it can move and then it calls itself for the same process.
     * @param s
     * @return
     */
    private Set<State> getAllClosures(State s){
        Set<State> closureState = new HashSet<>();
        State curr;
//        System.out.println("State: " + s);
        closureState = nfa.epsilonClosure(s);
//        System.out.println("Closure State { " + closureState + "}");


        if (closureState.isEmpty()){
            return closureState;

        } else {
            if (addToArray(closureState)){

                for (int i: symbols){
                    for (State ns: closureState) {
//                        System.out.println("Trying to move: " + (char) i + " of state: " + ns.getId());
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

    private void getAllClosures2(State s){
        Set<State> closureState;
        Set<State> newSet;
        Set<State> currentSet;

        Set<State> adding = new HashSet<>();

        closureState = nfa.epsilonClosure(s);

        if (closureState == null){
            return;
        }
        addToArray(closureState);


        while (!queue.isEmpty())
        {
            closureState = queue.remove();

            for (int c:
                    symbols) {

                // mov(estado, char) ej: {2,10}
                currentSet = nfa.mov(closureState,c);

                for (State mov:
                        currentSet) {

                    // epsilon - (mov(A,a)) -> ({2,10}
                    newSet = nfa.epsilonClosure(mov);

                    if (!newSet.isEmpty()){
                        for (State s1:
                             newSet) {
                            adding.add(s1);
                        }
                    }
                }
                if (!adding.isEmpty()){
                    addToArray(adding);
                    transitionTable.addTransition(closureState,c,adding);
                    adding = new HashSet<>();
                }

            }

        }
    }

    /**
     * It tries to add the givenState into the states arraylist
     * If it's successful, it means that there was no copies of it on the arraylist
     * And it also adds a new state in the transition table
     * @param givenState
     * @return
     */
    private boolean addToArray(Set<State> givenState){
            // get id of all the states in the given state
            int[] idOfGiven = new int[givenState.size()];
            int i = 0;
            for (State s:givenState) {
                idOfGiven[i] = s.getId();
                i++;
            }
            // get id of each of the states in the states array
            int[] idOfCurr;
            for (Set<State> sn:states) {
                idOfCurr = new int[sn.size()];
                i=0;
                for (State s:sn) {
                    idOfCurr[i] = s.getId();
                    i++;
                }
                // Compare if they equal means that there is already a state with that set
                if (Arrays.equals(idOfGiven, idOfCurr)){
//                    System.out.println("Don't add");

                    return false;
                }
            }
//        System.out.println("added");
            transitionTable.addNfaDfa(givenState);
            this.states.add(givenState);
            queue.add(givenState);

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

    public void export_json(String filename){
        // TODO export the contents of this class so it can be imported easily
        JSONObject dfaJson = new JSONObject();

        try (FileWriter file = new FileWriter("Dfa.json")) {

            file.write(dfaJson.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void import_json(JSONObject json){
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("employees.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray employeeList = (JSONArray) obj;
            System.out.println(employeeList);

            //Iterate over employee array
//            employeeList.forEach( emp -> parseEmployeeObject( (JSONObject) emp ) );

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}