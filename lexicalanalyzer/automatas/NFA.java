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
import lexicalanalyzer.automatas.equations.Trans;
import lexicalanalyzer.reader.ReadSourceCode;
import lexicalanalyzer.tokens.Node;


public class NFA extends Automata{
    
    private Stack<Node<Integer>> nodeStack;
    private VisitorTree visitor;
    private PostfixTree postfixTree;
    private int counterId;
    private int numArrayState;

    private Set<Trans> transitionTable;

    private ArrayList<State[]> allStates;

    public NFA(PostfixTree postfixTree){
        this.transitionTable = new HashSet<>();

        this.postfixTree = postfixTree;
        this.visitor = new VisitorTree(postfixTree);
        this.counterId = 0;
        this.allStates = new ArrayList<>();
        Thompson();
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

    private State getStartingState() {
        for (State[] ss:allStates) {
            for (State s: ss) {
                if (s.getInitialState()){
                    return s;
                }
            }
        }
        return null;
    }

    private State mover(State s, int c) {
        State temp;
        for (Trans t: this.transitionTable){
            if (t.getState().getId() == s.getId()){
                if (t.getCharacter() == 0){
                    // if epsilon, then it can keep going
                    temp = mover(t.getNextState(),c);

                    if (temp != null)
                        return temp;

                } else if (t.getCharacter() == c){
                    System.out.println("Moving "+ s.getId() +"-> " + t.getNextState());
                    return t.getNextState();

                }
            }
        }
        System.out.println("NULL -> " + s + " " + (char) c );

        return null;
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
            states = createStates(1);
            states[0].setSymbol(currNode.getnodeId());
            this.transitionTable.add(new Trans(getStates(currNode,"left")[0], DefaultValues.EPSILON, states[0]));
            this.transitionTable.add(new Trans(getStates(currNode,"left")[1], DefaultValues.EPSILON, states[0]));

        }else{
            states = createStates(3);
            states[0].setSymbol(currNode.getnodeId());
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

            this.transitionTable.add(new Trans(getStates(currNode,"left")[1], DefaultValues.EPSILON, states[0]));
            this.transitionTable.add(new Trans(states[0], DefaultValues.EPSILON, getStates(currNode,"left")[0]));
            this.transitionTable.add(new Trans(states[0], DefaultValues.EPSILON, states[1]));
        } else {
            states = createStates(3);
            states[0].setSymbol(currNode.getnodeId());

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
        System.out.println(states[0]);
        System.out.println(states[1]);
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
            this.transitionTable.add(new Trans(operandRight[0], DefaultValues.EPSILON, states[1]));


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

    /**
     *
     * @param currNode
     */
    private void caseConcat(Node<Integer> currNode) {
        State[] states = null;

        if(currNode.getLeftChild().isOperand()){
            if (currNode.getRightChild().isOperand()){
                // LEFT AND RIGHT is operation
                State[] operandLeft = getStates(currNode, "left");
                State[] operandRight = getStates(currNode, "right");

                states = createStates(1);
                states[0].setSymbol(currNode.getnodeId());

                this.transitionTable.add(new Trans(operandLeft[1], DefaultValues.EPSILON, operandRight[0]));
                this.transitionTable.add(new Trans(operandRight[0], DefaultValues.EPSILON, states[0]));

                states[0].setSymbol(currNode.getnodeId());

                removeFinalPoints();
                states[0].setFinalState(true);

            }else{
                // LEFT is operation
                states = createStates(2);
                State[] operandLeft = getStates(currNode, "left");

                states[0].setSymbol(currNode.getnodeId());

                removeFinalPoints();
                states[1].setFinalState(true);

                this.transitionTable.add(new Trans(operandLeft[0], DefaultValues.EPSILON, states[0]));
                this.transitionTable.add(new Trans(states[0], (Integer) currNode.getRightChild().getData(), states[1]));

            }
        } else if (currNode.getRightChild().isOperand()){
            // RIGHT is operation
            states = createStates(2);
            states[0].setSymbol(currNode.getnodeId());

            State[]  operandRight= getStates(currNode, "right");
            this.transitionTable.add(new Trans(states[0], (Integer) currNode.getRightChild().getData(), states[1]));
            this.transitionTable.add(new Trans(states[1], DefaultValues.EPSILON, operandRight[0]));
        } else{
            // NO OPERATIONS
            states = createStates(3);
            states[0].setSymbol(currNode.getnodeId());
            this.transitionTable.add(new Trans(states[0], (Integer) currNode.getLeftChild().getData(), states[1]));
            this.transitionTable.add(new Trans(states[1], (Integer) currNode.getRightChild().getData(), states[2]));
        }
        this.allStates.add(states);
    }


    private State[] createStates(int numberOfStates){
        State[] toReturn = new State[numberOfStates];
        for (int i=0;i<numberOfStates;i++){
            toReturn[i] = new State(this.counterId);
            counterId++;
        }
        return toReturn;
    }

    private void createTransitions(){

    }

    @Override
    public String toString() {
        String toReturn = "List of States:\n";
        // All States
        for (State[] n:
             this.allStates) {
            toReturn += "" +(char) n[0].getSymbol() + " = [ ";
            for (State s:
                 n) {
                toReturn += s.getId()+" ,";
            }
            toReturn += "]\n";
        }

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

}