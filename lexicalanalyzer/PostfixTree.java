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
    private Node<Integer> currentLeft;
    private Node<Integer> currentBrother;
    private Node<Integer> currentFather;
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
        Node<Integer> tempNode;

        Stack<Integer> operand = new Stack<>();

        int currChar = reader.readNextChar();
        
        while (currChar != DefaultValues.EOF){

            if (DefaultValues.letter.contains(currChar)){
                allSymbols.add(currChar);
            }else if (DefaultValues.operators.contains(currChar)){
                allOperants.add(currChar);
            }

            if (DefaultValues.operators.contains(currChar)){
                // This means it's an operand
                // If there are no currNode, it means it has to pop 2 numbers
                if (currNode == null){
                    currNode = new Node<>(currChar);
                    currNode.addRightChild( newNodeId(operand.pop()) );
                    currNode.addLeftChild( newNodeId(operand.pop()) );
                }
                // else if there is a current node then it has to use that node as a left child
                // and only pop 1 number from the stack 
                else {
                    tempNode = currNode;
                    currNode = newNodeId(currChar);
                    currNode.addRightChild( newNodeId(operand.pop()));
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

    /**
     * 
     * @return root Node
     */
    public Node<Integer> getRoot(){
        return this.root;
    }

    /**
     * Method: complete, checks if there are still unvisited ids.
     * @return true if no more unvisited 
     */
    public boolean complete(){
        if (this.visitedIds.size() == this.nodeId)
            return true;
        return false;
    }

    public void resetVisited(){
        this.visitedIds = new ArrayList<>();
    }

    
    /** 
     * getFamilyMembers() gets the bottom left with it's brother
     *  (if borther not null) and it's parent. Also it checks the
     *  Visited box for each one
     */
    private void getFamilyMemebers(){
        Node<Integer> tranverse = root;
        
        while (tranverse.getLeftChild() != null){
            // Check if visited left child
            if (this.visitedIds.contains(tranverse.getLeftChild().getnodeId())){
                if (tranverse.getRightChild() != null)
                    tranverse = tranverse.getRightChild();
                else{
                    System.out.println("Should break");
                    break;
                }
            }
            if (tranverse.getLeftChild().getLeftChild() != null)
                tranverse = tranverse.getLeftChild();
            else{
                this.currentFather = tranverse;
                this.currentBrother = tranverse.getRightChild();
                this.currentLeft = tranverse.getLeftChild();
            }
            visitedIds.add(this.currentLeft.getnodeId());
            visitedIds.add(this.currentFather.getnodeId());
            if (currentBrother != null)
                visitedIds.add(this.currentBrother.getnodeId());
        }

        
    }

    // GETTERS for the current nodes
    public Node<Integer> getBottomLeft(){return this.currentLeft;}
    public Node<Integer> getBrotherRight(){return this.currentBrother;}
    public Node<Integer> getParent(){return this.currentFather;}

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
     * 
     * @return all symbols 
     */
    public Set<Integer> getAllSymbols(){
        return this.allSymbols;
    }
    public ArrayList<Integer> getAllOperants(){
        return this.allOperants;
    }
    
}