/** Autonoma: Padre, tiene los atributos de ambos NFA y DFA
 *  Creado por: Santiago Paiz
 *  Descripcion: THOMPSON algoritmos para cambiar de un deterministico a un no deterministico
 *  @param Tree of the regular expression
 *  @return Automata
 **/

package lexicalanalyzer.automatas;
import lexicalanalyzer.automatas.equations.*;

import java.util.Set;

public class Automata {
    protected Set<State> states;
    protected Transition transitions;
    protected Symbol symbols;
    protected State initialState;
    protected Set<State> finalStates;

    public Automata(){
        
    }

    public boolean Simulate(){
        return false;
    }

}