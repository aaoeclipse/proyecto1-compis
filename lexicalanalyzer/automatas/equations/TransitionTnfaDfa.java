package lexicalanalyzer.automatas.equations;

import lexicalanalyzer.reader.ReadSourceCode;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TransitionTnfaDfa {
    private ArrayList<Set<Integer>> NFAids;
    private ArrayList<Integer> DFAids;
    private ArrayList<int[]> symbolsTable;
    private ArrayList<Integer> symHeader;

    private ArrayList<Integer> dfaInitialStates;
    private ArrayList<Integer> dfaFinalStates;

    private int numOfSym;
    private int currName;
    private int finalState;
    private boolean first;
    private int initalState;


    public TransitionTnfaDfa(Set<Integer> allSymbols, int finalState, int initalState){
        first = true;
        this.initalState = initalState;
        this.finalState = finalState;
        dfaInitialStates = new ArrayList<>();
        dfaFinalStates = new ArrayList<>();
        NFAids = new ArrayList<>();
        DFAids = new ArrayList<>();
        symbolsTable = new ArrayList<>();
        symHeader = new ArrayList<>();
        currName = 65;

        for (int i: allSymbols) {
            symHeader.add(i);
        }

        numOfSym = symHeader.size();
    }

    public ArrayList<Integer> getFinalStates(){
        finalStates();

        return dfaFinalStates;
    }

    public void finalStates(){
        for (int i = 0; i < NFAids.size(); i++) {
            if (NFAids.get(i).contains(this.finalState)){
                dfaFinalStates.add(DFAids.get(i));
            }
        }
    }

    public int mover(int dfa, int c) {
        int nextState = -1;
        if (dfa < 0)
            dfa = this.DFAids.get(0);

        // 65
        int rowDFA = dfa-65;

        int column = -1;
        for (int i = 0; i < symHeader.size(); i++) {
            if (symHeader.get(i) == c){
                column = i;
            }
        }

        if (column < 0)
            return -1;
//        System.out.println(symbolsTable.get(rowDFA)[column]);
        nextState = getDfaName(symbolsTable.get(rowDFA)[column]);

        if (nextState == 63)
            nextState = -1;
        return nextState;
    }



    private int charToPosition(int character){
        for (int i = 0; i < symHeader.size(); i++) {
            if (symHeader.get(i) == character)
                return i;
        }
        return -1;
    }


    public void addNfaDfa(Set<State> nfaS){
        Set<Integer> nfa = setToInt(nfaS);

        NFAids.add(nfa);
        DFAids.add(currName++);

        // row of the table++
        int[] toAdd = new int[numOfSym];

        for (int i = 0; i < toAdd.length; i++) {
            toAdd[i] = -1;
        }
        symbolsTable.add(toAdd);

        if (first){
            first = false;
            dfaInitialStates.add(65);
        }
    }

    public void addTransition(Set<State> nfas, int character, Set<State> dfas){
        Set<Integer> nfa = setToInt(nfas);
        Set<Integer> dfa = setToInt(dfas);

        for (int row = 0; row < NFAids.size(); row++) {
            if (NFAids.get(row).equals(nfa)){
                addTable(row,character,dfa);
            }
        }
    }

    private void addTable(int row, int character, Set<Integer> dfa) {
        if (dfa.size() == 0){
            symbolsTable.get(row)[charToPosition(character)] = -1;
            return;
        }
        for (int finalRow = 0; finalRow < NFAids.size(); finalRow++) {
            if (NFAids.get(finalRow).equals(dfa)){
                symbolsTable.get(row)[charToPosition(character)] = finalRow;
            }
        }
    }

    @Override
    public String toString() {
        finalStates();
        String test = "\nSYMBOL TABLE\n      ";
        // header del table
        for (int header:symHeader) {
            test += (char) header+"  ";
        }
        test += "\n";
        int counter = 0;
        for (int[] i :symbolsTable) {

            if (dfaInitialStates.contains(DFAids.get(counter)))
                test += getDfaName(counter++) + "  I [";
            else
                test += getDfaName(counter++) + "    [";

            for (int j = 0; j < i.length; j++) {
                test += getDfaName(symbolsTable.get(counter-1)[j])+ ", " ;
            }
            if (this.dfaFinalStates.contains(DFAids.get(counter-1)))
                test += "] F\n";
            else
                test += "]\n";

        }
        return "TransitionTnfaDfa{" +
                "\nNFAids=" + NFAids +
                "\n, DFAids=" + DFAids +
                "" + test +
                ", numOfSym=" + numOfSym +
                ", currName=" + currName +
                '}';
    }

    private char getDfaName(int i) {
        if (i==-1){
            return '?';
        }
        for (int id:DFAids
             ) {
            System.out.print(id+", ");
        }
        int test= DFAids.get(i);
        return (char) test;
    }

    private Set<Integer> setToInt(Set<State> states){
        Set<Integer> toReturn = new HashSet<>();
        for (State s:states) {
            toReturn.add(s.getId());
        }
        return toReturn;
    }

    /* Setters and Getters */

    public ArrayList<Integer> getSymHeader() {
        return symHeader;
    }

    public void setSymHeader(ArrayList<Integer> symHeader) {
        this.symHeader = symHeader;
    }


    public ArrayList<Set<Integer>> getNFAids() {
        return NFAids;
    }

    public void setNFAids(ArrayList<Set<Integer>> NFAids) {
        this.NFAids = NFAids;
    }

    public ArrayList<Integer> getDFAids() {
        return DFAids;
    }

    public void setDFAids(ArrayList<Integer> DFAids) {
        this.DFAids = DFAids;
    }

    public ArrayList<int[]> getSymbolsTable() {
        return symbolsTable;
    }

    public void setSymbolsTable(ArrayList<int[]> symbolsTable) {
        this.symbolsTable = symbolsTable;
    }


}
