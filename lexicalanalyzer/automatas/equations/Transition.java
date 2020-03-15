package lexicalanalyzer.automatas.equations;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class Transition {

    private ArrayList<ArrayList<int[]>> table;
    private int[][] transition;
    private Symbol symbols;
    private Set<State> states;

    public Transition(Symbol symbols){
            this.symbols = symbols;
            this.table = new ArrayList<ArrayList<int[]>>();
    }

    public void addRow(ArrayList<int[]> row){
        this.table.add(row);
    }

    public String testTable(){
        int counter = 0;
        String printingTable = "\t";

        for (int symbol: symbols.getSymbols()){
            printingTable += (char) symbol + "  \t \t";
        }
        printingTable += "    Epsilon";

        for (ArrayList<int[]> ai: table) {
            printingTable += "\n";

            for (int[] i: ai){
                printingTable += " | ";

                for (int n: i) {
                    printingTable += n+", ";
                }
            }
        }

        return printingTable;
    }

    public Transition(Symbol symbols, Set<State> states){
        this.symbols = symbols;
        this.states = states;

        transition = new int[states.size()][symbols.getSymbols().size()];

        for (int i = 0; i < transition.length; i++) {
            Arrays.fill(transition[i],-1);
        }

        System.out.println(toString());
    }

    public int[] epsilons(State x){
        int[] epsilonsList = null;
        int id = x.getId();

        ArrayList<int[]> ai = table.get(id);
        epsilonsList = ai.get(ai.size()-1);

        return epsilonsList;
    }

    /**
     * @param id
     * @return epsilonsList
     * */
    public int[] epsilons(int id){
        int[] epsilonsList = null;

        ArrayList<int[]> ai = table.get(id);
        epsilonsList = ai.get(ai.size()-1);

        return epsilonsList;
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

    public ArrayList<Integer> showPossibleMoves(State x){
        int id = x.getId();

        ArrayList<Integer> arrayToReturn = new ArrayList<>();

        ArrayList<int[]> row = table.get(id);
        int[] death = new int[] {-1};
        System.out.println("test");
        for (int i = 0; i < row.size();i++){
            if (row.get(i)[0] != -1){
                arrayToReturn.add(97);
            }
        }
        return null;
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