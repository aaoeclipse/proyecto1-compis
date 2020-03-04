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

    public void makeTransition(int symbol, int state, int finalState){

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