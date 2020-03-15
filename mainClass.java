/** Class main
 *  Execute this in order to start the program, calls first the lexicalanalyzer.reader.ReadSourceCode.java
 *   In order to read the source code named in the arguments
 *  File last modified: 11 feb, 2020
 */

import lexicalanalyzer.automatas.equations.State;
import lexicalanalyzer.automatas.equations.Symbol;
import lexicalanalyzer.automatas.equations.Transition;
import lexicalanalyzer.reader.*;
import lexicalanalyzer.PostfixTree;
import lexicalanalyzer.automatas.*;

import java.util.ArrayList;


public class mainClass{
    public static void main(String... args){
        ReadSourceCode reader = new ReadSourceCode();
        // Open File
        if (reader.runFile(args)){
            reader.changeToPostfix();
            reader.printPostfix();
            System.out.println("END READER");

            PostfixTree tree = new PostfixTree(reader);

            if (tree.getRoot() == null){
                System.out.println("It's null");
            }

            System.out.println(tree.getRoot());
            System.out.print("All Symbols!: ");
            for (int i : tree.getAllSymbols()) {
                System.out.print((char) i+", ");
            }

            System.out.println();
            System.out.print("All Operations!: ");
            for (int i : tree.getAllOperants()) {
                System.out.print((char) i+", ");
            }
            System.out.println();
            System.out.println("Go through tree");

            NFA nfa = new NFA(tree);

            Symbol test = new Symbol();
            test.symbolAdd((int) 'a');
            test.symbolAdd((int) 'b');
            test.symbolAdd((int) 'c');
            Transition transitionTest = new Transition(test);
            ArrayList<int[]> rowTest = new ArrayList<>();

            rowTest.add(new int[]{1});
            rowTest.add(new int[]{-1});
            rowTest.add(new int[]{-1});
            rowTest.add(new int[]{2,3,4});

            transitionTest.addRow(rowTest);

            rowTest = new ArrayList<>();

            rowTest.add(new int[]{-1});
            rowTest.add(new int[]{2});
            rowTest.add(new int[]{-1});
            rowTest.add(new int[]{3,4});

            transitionTest.addRow(rowTest);

            State stateTest = new State(0);
            transitionTest.showPossibleMoves(stateTest);
            System.out.println(nfa);
//            System.out.println(transitionTest.testTable());
        }
    }

}