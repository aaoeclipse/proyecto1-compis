/**
 * Class: Tree.java
 *  Creats a syntax tree with help of the reader that passes each char
 * @param (reader) with source code
 * @return Tree with nodes
 */


package lexicalanalyzer.tokens;

import java.util.ArrayList;
import java.util.List;
import lexicalanalyzer.reader.ReadSourceCode;

public class Tree{
    Node root = null;
    Node currNode = null;
    Node prevNode = null;
    ReadSourceCode reader = null;
    
    /**
     * Creats a tree by reading char by char
     * @param reader
     * @return null
     */
    public Tree(ReadSourceCode reader){
        this.reader = reader;

        // read first char
        int c = reader.readNextChar();
        // while has more (c != EOF)
        while ( c != -2) {
           // ignore space and enters
           if((char) c != '\n' || (char) c == ' '){
                if ((char) c == '*'){
                    // TODO
                }
                else if ((char) c == '|'){
                    // TODO
                }
                else if ((char) c == '.'){
                    // TODO
                }
                else if ((char) c == '('){
                    reader.readNextChar();
                    while ((char) c != ')'){
                        // TODO
                        reader.readNextChar();
                    }
                }
                else{
                    // aqui significa que es un character que no esta en la tabla de symbolos
                    
                }
            }
            // next char
            c = reader.readNextChar();
           
        }

    }

    public void addNode(){

    }

    // goDeep() goes all the way to the left and so forth
    public Node[] goDeep(){
        return null;
    }

    // breath() goes layer by layer
    public Node[] breath(){
        return null;
    }
    
}