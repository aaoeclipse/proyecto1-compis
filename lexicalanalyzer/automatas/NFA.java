/** Autonoma no definido 
 *  Creado por: Santiago Paiz
 *  @param PostfixTree given by the class PostfixTree
 *  @return NFA 
 * **/

package lexicalanalyzer.automatas;

import lexicalanalyzer.reader.ReadSourceCode;

public class NFA extends Automata{
    private ReadSourceCode reader;

    public NFA(ReadSourceCode reader){
        this.reader = reader;
        // inicial = new Estado(0);
    }

    public boolean simulating(Estado s0){
        char c = (char) reader.readNextChar();
        // while ( c != eof) {
        //      s = mover(s,c);
        //      c = (char) reader.readNextChar();
        // }
        // if (s esta en F) return true;
        return false;
    }    


    private void Thompson(){
        
    }
}