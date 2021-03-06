/** Autonoma no definido 
 *  Creado por: Santiago Paiz
 *  @param PostfixTree given by the class PostfixTree
 *  @return NFA 
 * **/

package lexicalanalyzer.automatas;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import jdk.swing.interop.SwingInterOpUtils;
import lexicalanalyzer.DefaultValues;
import lexicalanalyzer.PostfixTree;
import lexicalanalyzer.VisitorTree;
import lexicalanalyzer.automatas.equations.State;
import lexicalanalyzer.automatas.equations.Symbol;
import lexicalanalyzer.automatas.equations.Trans;
import lexicalanalyzer.reader.ReadSourceCode;
import lexicalanalyzer.tokens.Node;


public class NFA extends Automata{
    
    private VisitorTree visitor;
    private PostfixTree postfixTree;
    private int counterId;
    private int numArrayState;

    public Set<Trans> transitionTable;
    private ArrayList<State[]> allStates;
    public ArrayList<State> trulyStates;

    public NFA(PostfixTree postfixTree){
        this.transitionTable = new HashSet<>();
        trulyStates = new ArrayList<>();

        this.postfixTree = postfixTree;
        this.visitor = new VisitorTree(postfixTree);
        this.counterId = 0;
        this.allStates = new ArrayList<>();
        Thompson();
    }

    public NFA(ArrayList<Integer> toCreate, int option){
        trulyStates = new ArrayList<>();
        if (toCreate.size() == 1 && (toCreate.get(0) < 0)){
            createCHR(toCreate.get(0)*(-1));
            return;
        }
        this.transitionTable = new HashSet<>();
        this.counterId = 0;
        this.allStates = new ArrayList<>();
        State[] initialFinal = createStates(2);
        initialFinal[0].setInitialState(true);
        initialFinal[1].setFinalState(true);
        State[] s;
        // option 0 means it's an 'or' for each case
        if (option == 0){
            for (int i:
                 toCreate) {
                s = createStates(1);
                this.transitionTable.add(new Trans(initialFinal[0], DefaultValues.EPSILON, s[0]));
                this.transitionTable.add(new Trans(s[0], i, initialFinal[1]));
            }
        }
        // option 1 means it's and for each case
        if (option == 1){
            s = createStates(toCreate.size()+1);
            this.transitionTable.add(new Trans(initialFinal[0], DefaultValues.EPSILON, s[0]));
            for (int i=0; i < toCreate.size();i++){
                this.transitionTable.add(new Trans(s[i], toCreate.get(i), s[i+1]));
            }
            this.transitionTable.add(new Trans(s[s.length-1], DefaultValues.EPSILON, initialFinal[1]));
        }
    }

    private void createCHR(int i) {
        this.transitionTable = new HashSet<>();
        this.counterId = 0;
        this.allStates = new ArrayList<>();
        State[] s;
        s = createStates(i);
        s[0].setInitialState(true);
        for (int j = 1; j < s.length; j++) {
            this.transitionTable.add(new Trans(s[j-1], (int) ' ', s[j]));
        }
        s[s.length-1].setFinalState(true);

    }

    public boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    public NFA(NFA given, int option, NFA next, boolean repeat){
        trulyStates = given.getTrullyStates();
        transitionTable = given.transitionTable;

        int numberOfStates1 = given.getNumOfStates();
        System.out.println("numberOfStates1 = " + numberOfStates1);

        // CONCAT
        if (option == 0){
            // changing id
            for (State st:next.trulyStates) {
                st.setId(st.getId()+numberOfStates1);
            }
//            next.removeStartingPointsTrans();
            System.out.println(transitionTable);
            for (Trans t: next.transitionTable) {
                transitionTable.add(t);
            }
        }
        // OR
        if (option == 1){
            numberOfStates1 = given.getNumOfStates()+1;
            // changing id
            for (Trans t: next.transitionTable) {
                t.getState().setId(numberOfStates1+t.getState().getId());
                t.getNextState().setId(numberOfStates1+t.getNextState().getId());
            }
        }
        // { }
        if (repeat){
            this.transitionTable.add( new Trans( firstStartingState(), DefaultValues.EPSILON, firstFinalState() ) );
            this.transitionTable.add( new Trans( firstFinalState(), DefaultValues.EPSILON, firstStartingState() ) );
        }

    }

    public ArrayList<State> getTrullyStates(){
        return this.trulyStates;
    }

    private int getNumOfStates() {
        int valuetoreturn = 0;
        for (Trans t:
             transitionTable) {
               if (t.getState().getId() > valuetoreturn)
                   valuetoreturn = t.getState().getId();
               if (t.getNextState().getId() > valuetoreturn)
                    valuetoreturn = t.getNextState().getId();
        }
        return valuetoreturn;
    }

    private void removeFinalPointsTrans() {
        for(State st: trulyStates)
            st.setFinalState(false);
    }

    public void addToIds(int newVaue){
        for (State st:trulyStates) {
            st.setId(st.getId()+newVaue);
        }
    }

    private void removeStartingPointsTrans() {
        for (Trans t:
                transitionTable) {
            if (t.getState().getInitialState()){
                t.getState().setInitialState(false);
            }
        }
    }

    public boolean Simulate(ReadSourceCode reader){
         int c = reader.readNextCharInfile();

         State s = getStartingState();
         System.out.println("c: " + (char) c + " s: " + s);
         while ( c != DefaultValues.EOF && s != null) {
             s = mover(s,c);
             c = reader.readNextCharInfile();
         }
         if (s == null){
             return false;
         }
         return (s.getFinalState() || epsilonToEnd(s));
    }

    public boolean SimulateString(String reader){
        int counter = 0;

        char[] test = reader.toCharArray();
        int c = test[counter];

        State s = firstStartingState();

        while ( c != DefaultValues.EOF && s != null) {
            s = mover(s,c);
            if (counter+1 < test.length) {
                counter++;
                c = test[counter];
            }
            else
                break;
        }
        if (s == null){
            return false;
        }
        return (s.getFinalState() || epsilonToEnd(s));
    }

    /**
     * Checks if the end state is after epsilon
     * @param s
     * @return
     */
    private boolean epsilonToEnd(State s) {
        for (Trans t: this.transitionTable) {
            if (t.getState().getId() == s.getId()){
                if (t.getCharacter() == DefaultValues.EPSILON){
                    if (t.getNextState().getFinalState())
                        return true;
                    else{
                        if (epsilonToEnd(t.getNextState()))
                            return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * Works by getting all the reachable staets with an epsilon
     * @param s
     * @return
     */
    public Set<State> epsilonClosure(State s) {
        Set<State> states = new HashSet<>();

        for (Trans t: this.transitionTable) {
            if (t.getState().getId() == s.getId()){
                if (t.getCharacter() == DefaultValues.EPSILON){

                    states.add(t.getNextState());
                    Set<State> newStates = epsilonClosure(t.getNextState());

                    for (State ns: newStates ) {

                        states.add(ns);
                    }

                }
            }
        }
        return states;
    }

    public State firstStartingState(){
        for (Trans t:
             transitionTable) {
            if (t.getState().getInitialState())
                return t.getState();
        }
        return null;
    }

    public Set<Trans> getTransitionTable(){
        return this.transitionTable;
    }
    public State firstFinalState(){
        for (Trans t:
                transitionTable) {
            if (t.getState().getFinalState())
                return t.getState();
        }
        return null;
    }

    public State getStartingState() {
        for (State[] ss:allStates) {
            for (State s: ss) {
                if (s.getInitialState()){
                    return s;
                }
            }
        }
        return null;
    }

    public int getInitialId() {
        for (State[] ss:allStates) {
            for (State s: ss) {
                if (s.getInitialState()){
                    return s.getId();
                }
            }
        }
        return -1;
    }
    public int getFinalState() {
        for (State[] ss:allStates) {
            for (State s: ss) {
                if (s.getFinalState()){
                    return s.getId();
                }
            }
        }
        return -1;
    }

    public State mover(State s, int c) {
        State temp;
        for (Trans t: this.transitionTable){
            if (t.getState().getId() == s.getId()){
                if (t.getCharacter() == 0){
                    // if epsilon, then it can keep going
                    temp = mover(t.getNextState(),c);

                    if (temp != null)
                        return temp;

                } else if (t.getCharacter() == c){
//                    System.out.println("Moving "+ s.getId() +"-> " + t.getNextState());
                    return t.getNextState();

                }
            }
        }
//        System.out.println("NULL -> " + s + " " + (char) c );

        return null;
    }

    public State moverNoEpsilon(State s, int c) {

        for (Trans t: this.transitionTable){
            if (t.getState().getId() == s.getId()){

                if (t.getCharacter() == c){
                    return t.getNextState();

                }
            }
        }

        return null;
    }

    public Set<State> moverNoEpsilonSet(State s, int c) {
        Set<State> toReturn = new HashSet<>();
        for (Trans t: this.transitionTable){
            if (t.getState().getId() == s.getId())
                if (t.getCharacter() == c)
                    toReturn.add(t.getNextState());
        }

        return toReturn;
    }


    /** Method: Thompson creats a directional graph that represents the NFa
     *  @return NFA (directional graph)
      */
    private void Thompson(){
        Node<Integer> currNode = null;
        currNode = visitor.Next();
        while(currNode != null){
            switch (currNode.getData()){
                case (int) '|':
                    caseOr(currNode);
                    break;
                case (int) '&':
                    caseConcat(currNode);
                    break;
                case (int) '*':
                    caseNullOrMany(currNode);
                    break;
                case (int) '+':
                    caseAtLeastOne(currNode);
                    break;
                case (int) '?':
                    caseZeroOrOne(currNode);
                    break;
                default:
                    System.err.println("Symbol not found");
                    break;
            }
            currNode = visitor.Next();
       }


        
    }


    private void caseZeroOrOne(Node<Integer> currNode) {
        State[] states = null;
        if (currNode.getLeftChild().isOperand()){
            states = createStates(2);
            states[0].setSymbol(currNode.getnodeId());

            removeFinalPoints();
            removeStartingPoint();

            states[1].setFinalState(true);
            states[0].setInitialState(true);

            this.transitionTable.add(new Trans(states[0], DefaultValues.EPSILON, getStates(currNode,"left")[0]));
            this.transitionTable.add(new Trans(getStates(currNode,"left")[0], DefaultValues.EPSILON, states[1]));
            this.transitionTable.add(new Trans(getStates(currNode,"left")[1], DefaultValues.EPSILON, states[1]));

        }else{
            states = createStates(3);
            states[0].setSymbol(currNode.getnodeId());

            removeFinalPoints();
            removeStartingPoint();

            states[2].setFinalState(true);
            states[0].setInitialState(true);

            this.transitionTable.add(new Trans(states[0], (Integer) currNode.getLeftChild().getData(), states[1]));
            this.transitionTable.add(new Trans(states[1], DefaultValues.EPSILON, states[2]));
            this.transitionTable.add(new Trans(states[0], DefaultValues.EPSILON, states[2]));
        }

        this.allStates.add(states);
    }

    private void caseAtLeastOne(Node<Integer> currNode) {
        State[] states = null;

        if (currNode.getLeftChild().isOperand()){
            states = createStates(2);
            states[0].setSymbol(currNode.getnodeId());

            removeFinalPoints();
            removeStartingPoint();

            states[1].setFinalState(true);
            states[0].setInitialState(true);

            this.transitionTable.add(new Trans(getStates(currNode,"left")[1], DefaultValues.EPSILON, states[0]));
            this.transitionTable.add(new Trans(states[0], DefaultValues.EPSILON, getStates(currNode,"left")[0]));
            this.transitionTable.add(new Trans(states[0], DefaultValues.EPSILON, states[1]));
        } else {
            states = createStates(3);
            states[0].setSymbol(currNode.getnodeId());

            removeFinalPoints();
            removeStartingPoint();

            states[2].setFinalState(true);
            states[0].setInitialState(true);

            this.transitionTable.add(new Trans(states[0], (Integer) currNode.getLeftChild().getData(), states[1]));
            this.transitionTable.add(new Trans(states[1], DefaultValues.EPSILON, states[2]));
            this.transitionTable.add(new Trans(states[1], DefaultValues.EPSILON, states[0]));
        }
    }

    /**
     * CaseNullOrMany is in case of the star '*'
     * @param currNode
     */
    private void caseNullOrMany(Node<Integer> currNode) {
        State[] states = createStates(2);
        states[0].setSymbol(currNode.getnodeId());

        removeFinalPoints();
        removeStartingPoint();

        states[1].setFinalState(true);
        states[0].setInitialState(true);

        if (currNode.getLeftChild().isOperand()){
            getStates(currNode,"left");

            this.transitionTable.add(new Trans(states[0], DefaultValues.EPSILON, this.allStates.get(this.numArrayState)[0]));

            this.transitionTable.add(new Trans(states[0], DefaultValues.EPSILON, states[1]));

            this.transitionTable.add(new Trans(this.allStates.get(this.numArrayState)[this.allStates.get(this.numArrayState).length-1], DefaultValues.EPSILON, this.allStates.get(this.numArrayState)[0]));
            this.transitionTable.add(new Trans(this.allStates.get(this.numArrayState)[this.allStates.get(this.numArrayState).length-1], DefaultValues.EPSILON, states[1]));

        }else {

            this.transitionTable.add(new Trans(states[0],DefaultValues.EPSILON,states[1]));
            this.transitionTable.add(new Trans(states[0],(Integer) currNode.getLeftChild().getData(),states[0]));

        }
        this.allStates.add(states);
    }

    /**
     * this method is when the current case is a OR
     * Generates 7 new nodes in a state[] and then adds it
     * @param currNode
     */
    private void caseOr(Node<Integer> currNode) {
        State[] states = null;

        // check if there are operands on child
        if (currNode.getLeftChild().isOperand() || currNode.getRightChild().isOperand()){
            if (currNode.getLeftChild().isOperand() && currNode.getRightChild().isOperand()){
                states = createStates(2);
            } else {
                states = createStates(4);
            }

        } else {
            states = createStates(6);
        }

        states[0].setInitialState(true);
        states[0].setSymbol(currNode.getnodeId());

        if (states.length == 2){
            states[1].setFinalState(true);
            State[] operandLeft = getStates(currNode, "left");
            State[] operandRight = getStates(currNode, "right");
            this.transitionTable.add(new Trans(states[0], DefaultValues.EPSILON, operandLeft[0]));
            this.transitionTable.add(new Trans(states[0], DefaultValues.EPSILON, operandRight[0]));
            this.transitionTable.add(new Trans(operandLeft[1], DefaultValues.EPSILON, states[1]));
            this.transitionTable.add(new Trans(operandRight[1], DefaultValues.EPSILON, states[1]));


        } else if (states.length == 4){
            states[3].setFinalState(true);

            if (currNode.getLeftChild().isOperand()){
                State[] operandLeft = getStates(currNode, "left");

                this.transitionTable.add(new Trans(states[0], DefaultValues.EPSILON, operandLeft[0]));
                this.transitionTable.add(new Trans(states[0], DefaultValues.EPSILON, states[1]));

                this.transitionTable.add(new Trans(states[1], (Integer) currNode.getRightChild().getData(), states[2]));

                this.transitionTable.add(new Trans(operandLeft[1], DefaultValues.EPSILON, states[3]));
                this.transitionTable.add(new Trans(states[2], DefaultValues.EPSILON, states[3]));
            } else{
                State[] operandRight = getStates(currNode, "right");

                this.transitionTable.add(new Trans(states[0], DefaultValues.EPSILON, operandRight[0]));
                this.transitionTable.add(new Trans(states[0], DefaultValues.EPSILON, states[1]));

                this.transitionTable.add(new Trans(states[1], (Integer) currNode.getLeftChild().getData(), states[2]));
                this.transitionTable.add(new Trans(operandRight[1], DefaultValues.EPSILON, states[3]));
                this.transitionTable.add(new Trans(states[2], DefaultValues.EPSILON, states[3]));
            }

        } else {
            // Here means there is 7 states which means all are symbols
            this.transitionTable.add(new Trans(states[0], DefaultValues.EPSILON, states[1]));
            this.transitionTable.add(new Trans(states[0], DefaultValues.EPSILON, states[3]));
            // Symbols
            this.transitionTable.add(new Trans(states[1], (Integer) currNode.getLeftChild().getData(), states[2]));
            this.transitionTable.add(new Trans(states[3],  (Integer) currNode.getRightChild().getData(), states[4]));
            // end
            states[5].setFinalState(true);
            this.transitionTable.add(new Trans(states[2], DefaultValues.EPSILON, states[5]));
            this.transitionTable.add(new Trans(states[4], DefaultValues.EPSILON, states[5]));
        }

        removeFinalPoints();
        removeStartingPoint();

        states[0].setInitialState(true);
        states[states.length-1].setFinalState(true);

        allStates.add(states);
        }

    /**
     *  get only last or first and last states of the given
     * @param currNode
     * @param leftOrRight
     * @return
     */
    private State[] getStates(Node<Integer> currNode, String leftOrRight) {
        State[] toReturn = new State[2];
        if (leftOrRight.equalsIgnoreCase("left")){
            return searchState(currNode.getLeftChild().getnodeId());
        }else {
            return searchState(currNode.getRightChild().getnodeId());
        }
    }

    private State[] searchState(int id){
        State[] s = new State[2];
        for (int i=0; i < this.allStates.size();i++) {
            this.numArrayState = i;
            if (this.allStates.get(i)[0].getSymbol() == id) {
                // Este es el state
                s[0] =this.allStates.get(i)[0];
                s[1] = this.allStates.get(i)[this.allStates.get(i).length-1];
            }
        }
        return s;
    }

    public ArrayList<State[]> getAllStates(){
        return this.allStates;
    }


    private void caseConcat(Node<Integer> currNode) {
        State[] states = null;

        if(currNode.getLeftChild().isOperand()){
            if (currNode.getRightChild().isOperand()){

                // LEFT AND RIGHT is operation
                State[] operandLeft = getStates(currNode, "left");
                State[] operandRight = getStates(currNode, "right");

                states = createStates(2);
                states[0].setSymbol(currNode.getnodeId());


                this.transitionTable.add(new Trans(states[0], DefaultValues.EPSILON, operandLeft[0]));

                this.transitionTable.add(new Trans(operandLeft[1], DefaultValues.EPSILON, operandRight[0]));

                this.transitionTable.add(new Trans(operandRight[1], DefaultValues.EPSILON, states[1]));

                states[0].setSymbol(currNode.getnodeId());

                removeFinalPoints();
                removeStartingPoint();

                states[1].setFinalState(true);
                states[0].setInitialState(true);

            }else{
                // LEFT is operation

                states = createStates(4);
                State[] operandLeft = getStates(currNode, "left");

                states[0].setSymbol(currNode.getnodeId());

                removeStartingPoint();
                removeFinalPoints();
                states[3].setFinalState(true);
                states[0].setInitialState(true);

                this.transitionTable.add(new Trans(states[0], DefaultValues.EPSILON, operandLeft[0]));
                this.transitionTable.add(new Trans(operandLeft[1], DefaultValues.EPSILON, states[1]));
                this.transitionTable.add(new Trans(states[1], (Integer) currNode.getRightChild().getData(), states[2]));
                this.transitionTable.add(new Trans(states[2], DefaultValues.EPSILON, states[3]));

            }
        } else if (currNode.getRightChild().isOperand()){
            // RIGHT is operation
            states = createStates(4);
            states[0].setSymbol(currNode.getnodeId());

            State[]  operandRight= getStates(currNode, "right");

            this.transitionTable.add(new Trans(states[0], DefaultValues.EPSILON, states[1]));

            this.transitionTable.add(new Trans(states[1], (Integer) currNode.getLeftChild().getData(), states[2]));
            this.transitionTable.add(new Trans(states[2], DefaultValues.EPSILON, operandRight[0]));
            this.transitionTable.add(new Trans(operandRight[1], DefaultValues.EPSILON, states[3]));


            removeStartingPoint();
            removeFinalPoints();
            states[0].setInitialState(true);
            states[3].setFinalState(true);

        } else{
            // NO OPERATIONS
            states = createStates(6);
            states[0].setSymbol(currNode.getnodeId());

            removeStartingPoint();
            removeFinalPoints();

            states[0].setInitialState(true);
            states[states.length-1].setFinalState(true);

            this.transitionTable.add(new Trans(states[0], DefaultValues.EPSILON, states[1]));
            this.transitionTable.add(new Trans(states[1], (Integer) currNode.getLeftChild().getData(), states[2]));

            this.transitionTable.add(new Trans(states[2], DefaultValues.EPSILON, states[3]));

            this.transitionTable.add(new Trans(states[3], (Integer) currNode.getRightChild().getData(), states[4]));
            this.transitionTable.add(new Trans(states[4], DefaultValues.EPSILON, states[5]));
        }

        this.allStates.add(states);
    }


    private State[] createStates(int numberOfStates){
        State[] toReturn = new State[numberOfStates];
        State temp;
        for (int i=0;i<numberOfStates;i++){
            temp = new State(this.counterId);
            trulyStates.add(temp);
            toReturn[i] = temp;
            counterId++;
        }
        return toReturn;
    }

    @Override
    public String toString() {
        String toReturn = "List of States:\n";

        // Transition Table
        toReturn += "List of States:\n";
        for (Trans t: this.transitionTable) {
            toReturn += t+"\n";
        }

        return toReturn;
    }

    private void removeStartingPoint(){
        for (State[] ss: allStates) {
            for (State s: ss) {
                s.setInitialState(false);
            }
        }
    }

    private void removeFinalPoints(){
        for (State[] ss: allStates) {
            for (State s: ss) {
                s.setFinalState(false);
            }
        }
    }

    public Set<Integer> getAllSymbols(){
        return this.postfixTree.getAllSymbols();
    }

    public Set<State> mov(Set<State> closureState, int c) {
        State curr;
        Set<State> toReturn = new HashSet<>();

        for (State s: closureState) {
            curr = moverNoEpsilon(s,c);

            if (curr != null)
                toReturn.add(curr);
        }
        return toReturn;
    }

    public void removeStartingANDEnding(){
        for (Trans t:
             this.transitionTable) {

        }
    }
}