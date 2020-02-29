/** Class main
 *  Execute this in order to start the program, calls first the lexicalanalyzer.reader.ReadSourceCode.java
 *   In order to read the source code named in the arguments
 *  File last modified: 11 feb, 2020
 */

import lexicalanalyzer.reader.*;
import lexicalanalyzer.automatas.*;
import lexicalanalyzer.automatas.tokens.*;


public class mainClass{
    public static void main(String... args){
        ReadSourceCode reader = new ReadSourceCode();
        // Open File
        if (reader.runFile(args)){
            // Create Tree
            // Tree sintaxTree = new Tree(reader);

            // Pass Tree to Automata
            System.out.println((int)'\n');
            
        }
    }
}