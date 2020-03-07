package lexicalanalyzer.automatas.equations;

import java.util.Arrays;
import java.util.Set;

public class Transition {
    int[][] transition;
    Symbol symbols;
    Set<State> states;

    public Transition(Symbol symbols, Set<State> states){
        this.symbols = symbols;
        this.states = states;
        transition = new int[states.size()][symbols.getSymbols().size()];    
        for (int i = 0; i < transition.length; i++) {
            Arrays.fill(transition[i],-1);
        }

        System.out.println(toString());
    }

    /**
     * makeTransition, modifies the table in the position symbol, state to the next state that should go
     * @param symbol
     * @param state
     * @param finalState
     */
    public void makeTransition(int symbol, int state, int finalState){
        transition[state][symbol] = finalState;
    }

    /**
     * tansit works by showing the next state, if it's -1 means it died there.
     * @param symbol
     * @param state
     * @return int of the state in the table
     */
    public int transit(int symbol, int state){
        return transition[state][symbol];
    }
    
    @Override
    public String toString(){
        String stringToReturn = "++   ";
        int counter = 0;
        for (int symbol: symbols.getSymbols()){
            stringToReturn += (char) symbol + "  ";
        }
        stringToReturn += "\n";
        for (int[] i : transition) {
            stringToReturn += counter+": {";
            for (int j : i) {
                stringToReturn += j + ",";
            }
            stringToReturn += "}\n";
            counter++;
        }
        return stringToReturn;
    }
}