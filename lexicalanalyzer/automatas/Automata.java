/** Autonoma: Padre, tiene los atributos de ambos NFA y DFA
 *  Creado por: Santiago Paiz
 *  Descripcion: THOMPSON algoritmos para cambiar de un deterministico a un no deterministico
 *  @param Tree of the regular expression
 *  @return Automata
 **/

package lexicalanalyzer.automatas;

import java.util.Set;

public class Automata {
    private Set<Integer> setOfSymbols;
    private Set<Estado> States;
    private Set<Transition> transitions;
    private Set<Estado> initialState;
    private Set<Estado> finalState;

    public Automata(){
        
    }

    public boolean Simulate(){

    }


}