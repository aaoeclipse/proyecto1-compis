/**
 *  Class Postfixtree
 *  Description: Crea un arbol en base de leer un arreglo de postfix
 *  @param reader el cual le va a mandar un char a la vez
 *  @return Tree
 */
package lexicalanalyzer;

import java.util.Stack;
import lexicalanalyzer.reader.ReadSourceCode;
import lexicalanalyzer.tokens.*;

public class PostfixTree{
    
    // Reader which will send the char by char but in this case it's an int
    ReadSourceCode reader;
    // root node (main node)
    Node<Integer> root;
    
    public PostfixTree(ReadSourceCode reader){
        this.reader = reader;
        createTree();
        System.out.println("tree created");
    }

    /**
     * createTree is the main method in which the reader is going to 
     * send char by char and the tree is going to build the tree
     * while it's being created
     * @param postfix
     * @return root Node (Tree)
     */
    private void createTree(){
        Node<Integer> currNode = null;
        Node<Integer> tempNode;

        Stack<Integer> operand = new Stack<>();

        int currChar = reader.readNextChar();
        
        while (currChar != DefaultValues.EOF){

            if (DefaultValues.operators.contains(currChar)){
                // This means it's an operand
                // If there are no currNode, it means it has to pop 2 numbers
                if (currNode == null){
                    currNode = new Node<>(currChar);
                    currNode.addRightChild( new Node<>(operand.pop()) );
                    currNode.addLeftChild( new Node<>(operand.pop()) );
                }
                // else if there is a current node then it has to use that node as a left child
                // and only pop 1 number from the stack 
                else {
                    tempNode = currNode;
                    currNode = new Node<>(currChar);
                    currNode.addRightChild( new Node<>(operand.pop()));
                    currNode.addLeftChild(tempNode);
                }

            } else if (DefaultValues.letter.contains(currChar)){
                operand.push(currChar);
            } else {
                // Not a valid character
                System.err.println("[-] ERROR: (Class PostfixTree) Not recognizable letter: " + (char) currChar + " ... " + currChar);
            }
            currChar = reader.readNextChar();
        }

        this.root = currNode;
    }

    public Node<Integer> getRoot(){
        return this.root;
    }
}