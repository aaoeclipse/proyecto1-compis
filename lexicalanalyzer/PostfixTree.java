/**
 *  Class Postfixtree
 *  Description: Crea un arbol en base de leer un arreglo de postfix
 *  @param reader el cual le va a mandar un char a la vez
 *  @return Tree
 */
package lexicalanalyzer;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Set;
import java.util.HashSet;

import lexicalanalyzer.reader.ReadSourceCode;
import lexicalanalyzer.tokens.*;

public class PostfixTree{
    
    // Reader which will send the char by char but in this case it's an int
    private ReadSourceCode reader;
    // root node (main node)
    private Node<Integer> root;
     // Tree Transversal
    private ArrayList<Integer> visitedIds = new ArrayList<>();
    private int nodeId;

    // For the automata
    private Set<Integer> allSymbols;
    private ArrayList<Integer> allOperants;

    public PostfixTree(ReadSourceCode reader){
        this.nodeId = 0;
        this.reader = reader;
        this.allSymbols = new HashSet<>();
        this.allOperants = new ArrayList<>();
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

        Stack<Integer> operand = new Stack<>();
        Stack<Node<Integer>> nodeStack = new Stack<>();

        int currChar = reader.readNextChar();
        
        while (currChar != DefaultValues.EOF){

            // it adds the symbol or operand to the stack
            if (DefaultValues.letter.contains(currChar)){
                allSymbols.add(currChar);
            }
            else if (DefaultValues.operators.contains(currChar)){
                allOperants.add(currChar);
            }

            // Check if it's an operator
            if (DefaultValues.operators.contains(currChar)){

                if (currChar == (int) '*'){
                    // * only needs one
                    // fist check if operands are empty
                    if (operand.empty()){
                        if (nodeStack.empty()){
                            System.err.println("[-] ERROR PostixTree: createTree. Both stacks are empty");
                        }else {
                            // this means that there is a node
                            currNode = new Node<Integer>(currChar);
                            currNode.addLeftChild(nodeStack.pop());
                            currNode.getLeftChild().setParent(currNode);

                            nodeStack.push(currNode);
                        }
                    } else {
                        // This means that the stack of operands is not empty
                        currNode = new Node<Integer>(currChar);
                        currNode.addLeftChild( newNodeId( operand.pop() ) );
                        currNode.getLeftChild().setParent(currNode);

                        nodeStack.push(currNode);
                    }

                } else {
                    switch (operand.size()){
                        case 0:
                            if (nodeStack.size() < 2){
                                System.err.println("[-] ERROR PostfixTree: createTree(), operand size == 0 and node stack is less than two");
                            }
                            currNode = new Node<Integer> (currChar);
                            currNode.addRightChild(nodeStack.pop() );
                            currNode.addLeftChild( nodeStack.pop() );
                            break;

                        case 1:
                            if (nodeStack.size() < 1){
                                System.err.println("[-] ERROR PostfixTree: createTree(), operand size == 0 and node stack is less than 1");
                            }
                            currNode = new Node<Integer>(currChar);

                            currNode.addLeftChild( nodeStack.pop() );
                            currNode.addRightChild(newNodeId( operand.pop()) );
                            break;

                        default:
                            currNode = new Node<Integer>(currChar);

                            currNode.addLeftChild( newNodeId( operand.pop() ) );
                            currNode.addRightChild( newNodeId( operand.pop() ) );
                            break;
                    }

                    currNode.getRightChild().setParent(currNode);
                    currNode.getLeftChild().setParent(currNode);

                    nodeStack.push(currNode);
                }
            } // check if letter 
            else if (DefaultValues.letter.contains(currChar)){
                operand.push(currChar);
            } else {
                // Not a valid character
                System.err.println("[-] ERROR: (Class PostfixTree) Not recognizable letter: " + (char) currChar + " ... " + currChar);
            }
            currChar = reader.readNextChar();
        }
        this.root = currNode;
    }

    /**
     * 
     * @return root Node
     */
    public Node<Integer> getRoot(){
        return this.root;
    }


    /**
     * newNodeId: creates a new node with an especific id 
     * @param value
     * @return newNode
     */
    private Node<Integer> newNodeId(int value){
        Node<Integer> newNode = new Node<Integer>(value, this.nodeId);
        this.nodeId++;
        return newNode;
    }
    /**
     * getAllSymbols returns all the symbols
     * @return all symbols 
     */
    public Set<Integer> getAllSymbols(){
        return this.allSymbols;
    }

    /**
     * getAllSymbols returns all the symbols
     * @return all Operands 
     */
    public ArrayList<Integer> getAllOperants(){
        return this.allOperants;
    }
    
}