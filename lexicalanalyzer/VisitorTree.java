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
    private Set<Integer> visited;

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
        
        if (this.visited.size() == this.tree.size()){
            return null;
        }

        currNode = getLeftmostNode(currNode);
        this.visited.add(currNode.getnodeId());

        return currNode;
    }

    public Node<Integer> getLeftmostNode(Node<Integer> currNode){
        if (!currNode.isLeftChild()){
            return currNode.getParent();
        } 

        if (this.visited.contains(currNode.getLeftChild().getnodeId()) || DefaultValues.letter.contains(currNode.getLeftChild().getData())){

            // Aqui hay dos posibilidades, o el hijo izquierdo es una letra o ya fue visitado

            if (currNode.hasRightChild()){
                if (this.visited.contains(currNode.getRightChild().getnodeId()) || DefaultValues.letter.contains(currNode.getRightChild().getData())){
                    return currNode;
                } else{
                    currNode = getLeftmostNode(currNode.getRightChild());
                }
            }
        }

        else {
            return getLeftmostNode(currNode.getLeftChild());
        }
        
        return currNode;
    }

}