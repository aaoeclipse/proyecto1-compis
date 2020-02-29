/*
Class Node
Esta clase es utilizada por el lexical analyzer para la creacion del arbol.
Este tiene el data que representa el objeto ya sea statement o algun otro y se pueda formar el arbol
*/

package lexicalanalyzer.tokens;

import java.util.ArrayList;
import java.util.List;

public class Node<T>{
    
    private Node parent = null;
    private T data = null; 
    private List<Node<T>> children = new ArrayList<>();
    private int layer = 0;
    
    public Node(T data){
        this.data = data;
    }

    public Node(T data, int layer){
        this.data = data;
        this.layer = layer;
    }

    /* GETTERS */
    public T getData(){
        return this.data;
    }

    public Node getParent(){
        return this.parent;  
    }

    public List<Node<T>> getChildren(){
        return this.children;
    }

    public int getLayer(){
        return this.layer;
    }

    /* SETTERS */
    public void setParent(Node parent){
        this.parent = parent;
    }

    public void setData(T data){
        this.data = data;
    }

    public void addChildren(Node<T> child){
        this.children.add(child);
    }

    public void setLayer(int layer){
        this.layer = layer;
    }


}