/*
Class Node
Esta clase es utilizada por el lexical analyzer para la creacion del arbol.
Este tiene el data que representa el objeto ya sea statement o algun otro y se pueda formar el arbol
*/

package lexicalanalyzer.tokens;

import java.util.ArrayList;
import java.util.List;

public class Node<T>{
    
    private Node<T> parent = null;

    private T data = null; 

    private Node<T> left_child;
    private Node<T> right_child;

    private int layer = 0;

    public Node(){ }
    
    public Node(T data){
        left_child = null;
        right_child = null;
        this.data = data;
    }

    public Node(T data, int layer){
        this.data = data;
        this.layer = layer;
    }

    public boolean isOperand(){
        switch ((char) this.data) {
            case '|':
            case '*':
            case '+':
            case '?':
                return true;
            default:
                return false;
        }
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

    public int getLayer(){
        return this.layer;
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

    public void setLayer(int layer){
        this.layer = layer;
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


}