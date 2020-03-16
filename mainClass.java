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

import java.io.Reader;
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
            System.out.println(nfa);

            // Simulating
            System.out.println("SIMULATING");
            reader = new ReadSourceCode();
            String[] test = new String[1];
            test[0] = "runner.txt";
            if (reader.runFile(test)) {
                boolean isit = nfa.Simulate(reader);
                System.out.println("is it? " + isit);
            }

        }
    }

}