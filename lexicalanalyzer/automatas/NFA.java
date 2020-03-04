/** Autonoma no definido 
 *  Creado por: Santiago Paiz
 *  @param PostfixTree given by the class PostfixTree
 *  @return NFA 
 * **/

package lexicalanalyzer.automatas;

import java.util.ArrayList;
import java.util.Set;

import lexicalanalyzer.PostfixTree;
import lexicalanalyzer.reader.ReadSourceCode;
import lexicalanalyzer.tokens.Node;


public class NFA extends Automata{
    private PostfixTree postfixTree;
    // NFA -> States, Simbols, Transitinos, initial state, final state

    public NFA(PostfixTree postfixTree){
        this.postfixTree = postfixTree;
    }



    public boolean simulating(){
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
        // while(postfixTree.unvisited())
            // postfixTree.getBottomLeft()
            // postfixTree.getBrotherRight()
            // postfixTree.getParent()
        int parent = 0;
        switch ((char) parent){
            case '|':
                
                break;
            case '*':
                break;
            case '?':
                break;
            case '+':
                break;

        }
    }

    private void createStates(){

    }

    private void createSymbols(){

    }

    private void createTransitions(){

    }
}