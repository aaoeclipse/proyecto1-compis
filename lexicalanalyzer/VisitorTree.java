/**
 * VisitorTree, goes through the tree and returns the node with the next operation to do
 * Created: 10/03/2020
 */
package lexicalanalyzer;

import java.util.HashSet;
import java.util.Set;
import lexicalanalyzer.PostfixTree;
import lexicalanalyzer.tokens.*;

public class VisitorTree{
    // The postfix tree
    private PostfixTree tree;
    // All the nodes that it has visited
    private Set<Node<Integer>> visited;

    public VisitorTree(PostfixTree tree){
        System.out.println("New VisitorTree");
        this.tree = tree;
        visited = new HashSet<>();
    }

    /** 
     * Next(), goes through all the tree and returns the laft most 
     * node in which it hasn't send
     * @return returns the node with the next operand 
     */
    public Node<Integer> Next(){

        Node<Integer> currNode = tree.getRoot();

        while ( currNode.isLeftChild() ){
            // check if left child is visited
            while ( visited.contains( currNode.getLeftChild()) ) {
                // stop, go to right
                if ( currNode.hasRightChild() ){
                    currNode = currNode.getRightChild();
                } else {
                    currNode = currNode.getLeftChild();
                    break;
                }
            }
            currNode = currNode.getLeftChild();
        }
        
        // parent of the left most child
        currNode = currNode.getParent();

        // Here currNode is the wanted
        this.visited.add(currNode);
        System.out.println(this.visited);

        return currNode;
    }
}