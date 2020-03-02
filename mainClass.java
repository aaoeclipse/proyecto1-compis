/** Class main
 *  Execute this in order to start the program, calls first the lexicalanalyzer.reader.ReadSourceCode.java
 *   In order to read the source code named in the arguments
 *  File last modified: 11 feb, 2020
 */

import lexicalanalyzer.reader.*;
import lexicalanalyzer.PostfixTree;
import lexicalanalyzer.automatas.*;


public class mainClass{
    public static void main(String... args){
        ReadSourceCode reader = new ReadSourceCode();
        // Open File
        if (reader.runFile(args)){
            reader.changeToPostfix();
            PostfixTree tree = new PostfixTree(reader);
            if (tree.getRoot() == null){
                System.out.println("It's null");
            }
            System.out.println(tree.getRoot());
            
        }
    }
}