/** Autonoma no definido 
 *  Creado por: Santiago Paiz
 *  @param PostfixTree given by the class PostfixTree
 *  @return NFA 
 * **/

package lexicalanalyzer.automatas;

import java.util.ArrayList;
import java.util.Set;
import java.util.Stack;

import lexicalanalyzer.PostfixTree;
import lexicalanalyzer.VisitorTree;
import lexicalanalyzer.reader.ReadSourceCode;
import lexicalanalyzer.tokens.Node;


public class NFA extends Automata{
    
    private Stack<Node<Integer>> nodeStack;
    private VisitorTree visitor;
    private PostfixTree postfixTree;

    public NFA(PostfixTree postfixTree){
        this.postfixTree = postfixTree;
        this.visitor = new VisitorTree(postfixTree);
        Thompson();
    }


    @Override
    public boolean Simulate(){
        // char c = (char) reader.readNextChar();
        // while ( c != eof) {
        //      s = mover(s,c);
        //      c = (char) reader.readNextChar();
        // }
        // if (s esta en F) return true;
        return false;
    }    


    /** Method: Thompson creats a directional graph that represents the NFa
     *  @param void
     *  @return NFA (directional graph)
      */
    private void Thompson(){
        System.out.println();
        Node<Integer> currNode = null;  

        System.out.println("1");
        currNode = this.visitor.Next();
        System.out.println(currNode);

        System.out.println("2");
        currNode = this.visitor.Next();
        System.out.println(currNode);

        System.out.println("3");
        currNode = this.visitor.Next();
        System.out.println(currNode);

        /* switch (currNode.getData()){
            case (int) '|':
                break;
            case (int) '&':
                break;
            case (int) '*':
                break;
            default:
                break;
        } */
        
    }

    private void createStates(){

    }

    private void createSymbols(){

    }

    private void createTransitions(){

    }
}