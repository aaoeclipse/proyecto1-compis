/**
 * Class: Tree.java
 *  Creats a syntax tree with help of the reader that passes each char
 * @param (reader) with source code
 * @return Tree with nodes
 */


package lexicalanalyzer.tokens;

import java.util.ArrayList;
import java.util.List;

public class Tree{
    Node root = null;
    Node currNode = null;
    Node prevNode = null;
    
    /**
     * Creats a tree by reading char by char
     * @param reader
     * @return null
     */
    public Tree(){
        
    }

    public void addNode(Node node, boolean left){
        if (root == null){
            root = node;
            return;
        }
        if (left){
            
        }
        
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