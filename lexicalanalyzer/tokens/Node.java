/*
Class Node
Esta clase es utilizada por el lexical analyzer para la creacion del arbol.
Este tiene el data que representa el objeto ya sea statement o algun otro y se pueda formar el arbol
*/

package lexicalanalyzer.tokens;

import lexicalanalyzer.DefaultValues;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node<T>{
    
    private Node<T> parent = null;

    private T data = null; 

    private Node<T> left_child;
    private Node<T> right_child;

    private int nodeId = 0;

    public Node(){
        left_child = null;
        right_child = null;
        this.data = null;
    }
    
    public Node(T data){
        left_child = null;
        right_child = null;
        this.data = data;
    }

    public Node(T data, int nodeId){
        left_child = null;
        right_child = null;
        this.data = data;
        this.nodeId = nodeId;
    }

    public boolean isOperand(){
        if (DefaultValues.operators.contains(this.data))
            return true;
        return false;
    }

    /* GETTERS */
    public T getData(){
        return this.data;
    }

    public Node getParent(){
        return this.parent;  
    }

    public Node getLeftChild(){
        return this.left_child;
    }

    public Node getRightChild(){
        return this.right_child;
    }

    public int getnodeId(){
        return this.nodeId;
    }

    /* SETTERS */
    public void setParent(Node<T> parent){
        this.parent = parent;
    }

    public void setData(T data){
        this.data = data;
    }

    public void addLeftChild(Node<T> child){
        this.left_child = child;
    }
    public void addRightChild(Node<T> child){
        this.right_child = child;
    }

    public void setnodeId(int nodeId){
        this.nodeId = nodeId;
    }

    public boolean isLeftChild(){
        if (this.left_child != null)
            return true;
        return false;
    }

    public boolean hasRightChild(){
        if (this.right_child != null)
            return true;
        return false;
    }

    @Override
    public String toString(){
        String stringToReturn = "";
        stringToReturn += "N("+ this.data + "){ ";
        if (this.left_child != null)
            stringToReturn += "Left: { " + this.left_child.toString() + " } ";
        if (this.right_child != null)
            stringToReturn += "Right: {" + this.right_child.toString() + "}";
        stringToReturn += "} EN("+ this.data + ")";
        return stringToReturn;
    }


    public int[] children() {
        int[] toReturn = new int[2];
        if (isLeftChild()){
            if (DefaultValues.operators.contains(getLeftChild().data)){

            }
        } else {
            toReturn[0] = 0;
        }
        if (hasRightChild()){

        } else {
            toReturn[1] = 0;
        }

        return toReturn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node<?> node = (Node<?>) o;
        return nodeId == node.nodeId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodeId);
    }
}